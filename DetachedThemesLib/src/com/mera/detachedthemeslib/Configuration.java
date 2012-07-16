package com.mera.detachedthemeslib;

import java.util.ArrayList;
import java.util.List;

import com.mera.detachedthemeslib.ThemeManager.Theme;

public abstract class Configuration {

	private List<Theme> packagesList = new ArrayList<Theme>();
	private int mDefaultThemeId;
	private String mDetachedThemeName;
	private String mPackagePrefixName;
	
	List<Theme> getInternalThemes() {
		return packagesList;
	}
	
	public void addNameForDetachedTheme(String detachedThemeName) {
		mDetachedThemeName = detachedThemeName;
	}
	
	public void addPackagePrefixName(String packagePrefixName) {
		mPackagePrefixName = packagePrefixName;
	}
	
	
	String getNameForDetachedTheme() {
		return mDetachedThemeName;
	}
	
	String getPackagePrefixName() {
		return mPackagePrefixName;
	}
	
	public void addInnerTheme(int thmemeId, String themeTitle, boolean isDefault) {
		Theme theme1 =  new Theme(false);
        theme1.mPackageName = "";
        theme1.mTitle = themeTitle;
        theme1.mId = thmemeId;
        packagesList.add(theme1);
        if(isDefault) {
        	mDefaultThemeId = thmemeId;
        }
	}
	
	int getDefaultThemeId() {
		return mDefaultThemeId;
	}

}
