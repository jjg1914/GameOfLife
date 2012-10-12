package com.quesucede.gameoflife;

import android.content.Context;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class PreferencesActivity extends PreferenceActivity {

	private static final String OPTION_MINIMUM = "UNDERPOPULATION_VARIABLE";
	private static final String OPTION_MINIMUM_DEFAULT = "2";
	private static final String OPTION_MAXIMUM = "OVERPOPULATION_VARIABLE";
	private static final String OPTION_MAXIMUM_DEFAULT = "3";
	private static final String OPTION_SPAWN = "SPAWN_VARIABLE";
	private static final String OPTION_SPAWN_DEFAULT = "3";
	private static final String OPTION_ANIMATION_SPEED = "ANIMATION_SPEED_VARIABLE";
	private static final String OPTION_ANIMATION_SPEED_DEFAULT = "3";

    private CheckBoxPreference colorCoding;

    private ListPreference animationSpeedVariable;

    public static boolean ENABLE_COLOR_CODING = false;

    public static long MOVE_DELAY = 250;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);

        colorCoding = (CheckBoxPreference)
                       getPreferenceManager().findPreference( "COLOR_CODING" );
        colorCoding.setOnPreferenceChangeListener(
            new Preference.OnPreferenceChangeListener() {
                public boolean onPreferenceChange( Preference preference,
                                                            Object newValue ) {
                    if ( newValue.toString().equals( "true" ) ) {
                        PreferencesActivity.ENABLE_COLOR_CODING = true;
                    } else {
                        PreferencesActivity.ENABLE_COLOR_CODING = false;
                    }
                    return true;
                }
            }
        );

        animationSpeedVariable = (ListPreference)
           getPreferenceManager().findPreference( "ANIMATION_SPEED_VARIABLE" );
        animationSpeedVariable.setOnPreferenceChangeListener(
            new Preference.OnPreferenceChangeListener() {
                public boolean onPreferenceChange( Preference preference,
                                                            Object newValue ) {
                    String s = newValue.toString();

                    if ( s.equals( "5" ) ) {
                        MOVE_DELAY = 1000;
                    } else if ( s.equals( "4" ) ) {
                        MOVE_DELAY = 500;
                    } else if ( s.equals( "2" ) ) {
                        MOVE_DELAY = 100;
                    } else if ( s.equals( "1" ) ) {
                        MOVE_DELAY = 50;
                    } else {
                        MOVE_DELAY = 250;
                    }
                    return true;
                }
            }
        );
	}
	
	public static String getMinimumVariable(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).
			getString(OPTION_MINIMUM, OPTION_MINIMUM_DEFAULT);
	}
	
	public static String getMaximumVariable(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).
			getString(OPTION_MAXIMUM, OPTION_MAXIMUM_DEFAULT);
	}
	
	public static String getSpawnVariable(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).
			getString(OPTION_SPAWN, OPTION_SPAWN_DEFAULT);
	}
	
	public static String getAnimationSpeed(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).
			getString(OPTION_ANIMATION_SPEED, OPTION_ANIMATION_SPEED_DEFAULT);
	}
}
