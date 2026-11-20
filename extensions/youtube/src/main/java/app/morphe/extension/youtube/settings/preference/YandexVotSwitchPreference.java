/*
 * Copyright (C) 2026 Morphe
 *
 * This file is part of the morphe-patches-yavot project:
 * https://github.com/MarcaDian/morphe-patches-yavot
 *
 * Licensed under the GNU General Public License v3.0.
 */

package app.morphe.extension.youtube.settings.preference;

import android.content.Context;
import android.util.AttributeSet;

import app.morphe.extension.shared.settings.preference.BooleanSettingPreference;
import app.morphe.extension.youtube.settings.YandexVotSettings;

/**
 * A switch of a Yandex VoT {@link app.morphe.extension.shared.settings.BooleanSetting}.
 * <p>
 * Resolves its setting by key in {@code onAttachedToHierarchy}, which only works when
 * {@link YandexVotSettings} has already been loaded. The settings search and the player
 * load that class lazily, so loading it from the constructor guarantees every switch of
 * this add-on is registered before the first preference is attached.
 */
@SuppressWarnings("deprecation")
public class YandexVotSwitchPreference extends BooleanSettingPreference {

    public YandexVotSwitchPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        loadSettingsClass();
    }

    public YandexVotSwitchPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loadSettingsClass();
    }

    public YandexVotSwitchPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        loadSettingsClass();
    }

    public YandexVotSwitchPreference(Context context) {
        super(context);
        loadSettingsClass();
    }

    private static void loadSettingsClass() {
        // The static initializer registers every Yandex VoT setting by its key.
        YandexVotSettings.YANDEX_VOT_ENABLED.get();
    }
}