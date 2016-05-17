package com.example.brian.popularmovies;

import android.preference.Preference;
import android.preference.PreferenceActivity;

/**
 * Created by brian on 5/17/16.
 *
 */
public class SettingsActivity extends PreferenceActivity
        implements Preference.OnPreferenceChangeListener    {
    public void SettingsActivity(){

    }

    public boolean onPreferenceChange(Preference preference, Object value){
        return true;
    }
}
