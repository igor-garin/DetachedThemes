package com.mera.detachedthemeslib;

import android.content.Context;
import android.content.SharedPreferences;

class PreferenceManager {

	final static String PREF_THEME_TYPE = "PREF_THEME_TYPE";
	final static String PREF_THEME_ID = "PREF_THEME_ID";
	final static String PREF_THEME_PACKAGE = "PREF_THEME_PACKAGE";

	// theme type
	static void setThemeType(Context context, boolean value) {
		SharedPreferences pref = context.getSharedPreferences("PREF_NAME",
				Context.MODE_PRIVATE);
		pref.edit().putBoolean(PREF_THEME_TYPE, value).commit();
	}

	static boolean getThemeType(Context context) {
		SharedPreferences pref = context.getSharedPreferences("PREF_NAME",
				Context.MODE_PRIVATE);
		return pref.getBoolean(PREF_THEME_TYPE, false);
	}

	// theme id
	static void setThemeId(Context context, int value) {
		SharedPreferences pref = context.getSharedPreferences("PREF_NAME",
				Context.MODE_PRIVATE);
		pref.edit().putInt(PREF_THEME_ID, value).commit();
	}

	static int getThemeId(Context context, int defaultThemeId) {
		SharedPreferences pref = context.getSharedPreferences("PREF_NAME",
				Context.MODE_PRIVATE);
		return pref.getInt(PREF_THEME_ID, defaultThemeId);
	}

	// theme package
	static void setThemePackage(Context context, String value) {
		SharedPreferences pref = context.getSharedPreferences("PREF_NAME",
				Context.MODE_PRIVATE);
		pref.edit().putString(PREF_THEME_PACKAGE, value).commit();
	}

	static String getThemePackage(Context context) {
		SharedPreferences pref = context.getSharedPreferences("PREF_NAME",
				Context.MODE_PRIVATE);
		return pref.getString(PREF_THEME_PACKAGE, "");
	}
}
