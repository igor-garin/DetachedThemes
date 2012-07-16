package com.mera.detachedthemeslib;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;

public class ThemeManager {
	
	private static Configuration mConfig;
	
	static void init(Context ctx, Configuration cfg) {
		mConfig = cfg;
		if(isThemeDetached(ctx)) {

			final PackageManager pm = ctx.getPackageManager(); 
	        List<PackageInfo> packages = pm.getInstalledPackages(0);
	        boolean packageDeleted = true;
	        String themePackage = PreferenceManager.getThemePackage(ctx);
	        for( PackageInfo pi : packages) {
	            String pnm  = pi.applicationInfo.packageName;
	            if(pnm.equals(themePackage)) {
	            	packageDeleted = false;
	            }
	        }
	        if(packageDeleted) {
	        	setTheme(ctx, getThemes(ctx).get(0));
	        }
		}
	}
	
	public static List<Theme> getThemes(Context ctx) {
        List<Theme> packagesList = new ArrayList<Theme>();
        final PackageManager pm = ctx.getPackageManager(); 
        
        packagesList.addAll(mConfig.getInternalThemes());
        
        int id = 8003;
        
        List<PackageInfo> packages = pm.getInstalledPackages(0);
        
        for( PackageInfo pi : packages) {
            String pnm  = pi.applicationInfo.packageName;
            if(pnm.startsWith(mConfig.getPackagePrefixName())) {
                try {
                    Resources res = pm.getResourcesForApplication (pnm);
                    Theme theme =  new Theme(true);
                    theme.mPackageName = pnm;
                    theme.mTitle = res.getString(pi.applicationInfo.labelRes);
                    theme.mId = id;
                    id++;
                    packagesList.add(theme);
                    
                } catch (NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        return packagesList;
	}
	
	
	public static void setTheme(Context ctx, int id) {
		for(Theme theme : getThemes(ctx)) {
			if(theme.mId == id) {
				setTheme(ctx, theme);
			}
		}
	}
	
	private static void setTheme(Context ctx, Theme theme) {
		if(theme.mDetached) {
			PreferenceManager.setThemeType(ctx, true);
			PreferenceManager.setThemePackage(ctx, theme.mPackageName);
		} else {
			PreferenceManager.setThemeType(ctx, false);
			PreferenceManager.setThemeId(ctx, theme.mId);
		}
	}
	
	static boolean isThemeDetached(Context context) {
		return PreferenceManager.getThemeType(context);
	}
	
	static int getThemeId(Context context) {
		return PreferenceManager.getThemeId(context, mConfig.getDefaultThemeId());
	}

	static Resources.Theme getTheme(Context ctx) {
		final PackageManager pm = ctx.getPackageManager();
		Resources res = null;
        try {
            res = pm.getResourcesForApplication (PreferenceManager.getThemePackage(ctx));
            Resources.Theme rs = res.newTheme();
            
            int id_st = res
                    .getIdentifier(mConfig.getNameForDetachedTheme(), "style",PreferenceManager.getThemePackage(ctx));
            rs.applyStyle(id_st, true);
            return rs;
            
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
	}


	public static int getStyleIdValue(Context ctx, String attrStyleValue, int dafaultAttrStyleValue) {
		if (isThemeDetached(ctx)) {
			final PackageManager pm = ctx.getPackageManager();
			Resources res = null;
	        try {
	            res = pm.getResourcesForApplication (PreferenceManager.getThemePackage(ctx));
	            int id_st = res
	                    .getIdentifier(attrStyleValue, "attr",PreferenceManager.getThemePackage(ctx));
	            return id_st;
	            
	        } catch (NameNotFoundException e) {
	            e.printStackTrace();
	        }
		}
		
		return dafaultAttrStyleValue;
	}
	
	static Resources getDetachedResources(Context ctx) {
		final PackageManager pm = ctx.getPackageManager();
		Resources res = null;
        try {
            res = pm.getResourcesForApplication (PreferenceManager.getThemePackage(ctx));
            return res;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
	}
	
	public static class Theme {
		public final boolean mDetached;
		public String mTitle;
		public String mPackageName;
		public int mId;
		
		Theme(boolean detached) {
			mDetached = detached;
		}
	}
}
