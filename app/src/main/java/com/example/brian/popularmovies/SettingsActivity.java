package com.example.brian.popularmovies;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

/**
 * Created by brian on 5/17/16.
 *
 */
public class SettingsActivity extends PreferenceActivity
        implements Preference.OnPreferenceChangeListener    {

    public void SettingsActivity(){   }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.pref_general);

        Preference sort = findPreference(getString(R.string.pref_sort_key));

        //put listener logic here instead of a function since there is only one
        //will refactor into a function if more preferences are added later
        sort.setOnPreferenceChangeListener(this);

        onPreferenceChange(sort, PreferenceManager
                .getDefaultSharedPreferences(sort.getContext())
                .getString(sort.getKey(), ""));
    }



    public boolean onPreferenceChange(Preference preference, Object value){

        String prefValue = value.toString(); //all values in this preference are strings

        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int index = listPreference.findIndexOfValue(prefValue);

            if (index != -1 ) { //-1 is returned if index not found
                preference.setSummary(listPreference.getEntries()[index]);
            } else {
                preference.setSummary(prefValue);
            }
        }

        return true;
    }
}
