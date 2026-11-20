group = "app.morphe.thirdparty.yavot"

patches {
    about {
        name = "Morphe Yandex VoT"
        description = "Yandex voice-over translation add-on for Morphe Patches. " +
                "Requires patching with Morphe official patches"
        source = "git@github.com:MarcaDian/morphe-patches-yavot.git"
        author = "MarcaDian"
        contact = "na"
        website = "https://github.com/MarcaDian/morphe-patches-yavot"
        license = "GNU General Public License v3.0, with additional GPL section 7 requirements"
    }
}

// Resolve composite-build patcher/smali as jars. Otherwise both the classes
// directory and the jar are passed to D8 by buildAndroid, and it fails with
// "Classpath type already present" — producing an .mpp without classes.dex
// that Morphe Manager loads as 0 patches.
configurations.compileClasspath {
    attributes {
        attribute(
            LibraryElements.LIBRARY_ELEMENTS_ATTRIBUTE,
            objects.named(LibraryElements.JAR),
        )
    }
}

// Separate configuration so gson is available at runtime for the
// generatePatchesList task but never bundled into the APK.
val patchListGeneratorClasspath = configurations.register("patchListGeneratorClasspath")

dependencies {
    implementation(libs.morphe.patches.library)

    patchListGeneratorClasspath(libs.gson)

    // Android API stubs defined here.
    compileOnly(project(":patches:stub"))
}

tasks {
    named("sourcesJar") {
        enabled = false
    }

    register<JavaExec>("generatePatchesList") {
        description = "Build patch with patch list"

        // buildAndroid emits classes.dex into the .mpp; plain `build` does not.
        dependsOn("buildAndroid")

        classpath = sourceSets["main"].runtimeClasspath + patchListGeneratorClasspath.get()
        mainClass.set("app.morphe.util.PatchListGeneratorKt")
    }
    // Used by gradle-semantic-release-plugin.
    publish {
        dependsOn("generatePatchesList")
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs = listOf("-Xcontext-parameters")
    }
}
