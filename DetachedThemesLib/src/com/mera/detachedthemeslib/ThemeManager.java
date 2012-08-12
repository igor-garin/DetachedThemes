package com.mera.detachedthemeslib;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.util.Log;

public class ThemeManager {
	
	private static ThemesConfiguration mConfig = null;
	
	static int getMainThemeId(Context context) {
	    int id_st = -1;
        try {
            id_st = context.getResources()
                .getIdentifier("MainTheme", "style",context.getPackageName());
        } catch(NullPointerException ex) {
            Log.e("ThemeManager", ex.getMessage());
            throw new IllegalArgumentException("There is no default theme with name \"MainTheme\"");
        }
	    return id_st;
	}
	
	static void init(Context ctx) {
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
	            setInnerTheme(ctx, getMainThemeId(ctx));
	        }
		}
	}
	
	public static List<Theme> getThemes(Context ctx, ThemesConfiguration cfg) {
	    mConfig = cfg;
        List<Theme> packagesList = new ArrayList<Theme>();
        final PackageManager pm = ctx.getPackageManager(); 
        
        packagesList.addAll(mConfig.getInternalThemes(ctx));
        
        int id = 8003;
        
        List<PackageInfo> packages = pm.getInstalledPackages(0);
        
        for( PackageInfo pi : packages) {
            String pnm  = pi.applicationInfo.packageName;
            if(pnm.startsWith(mConfig.getPackagePrefixName(ctx)) && (!ctx.getPackageName().equals(pnm))) {
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
	
	private static void setInnerTheme(Context ctx, int id) {
        PreferenceManager.setThemeType(ctx, false);
        PreferenceManager.setThemeId(ctx, id);
	}
	
	public static void setTheme(Context ctx, Theme theme) {
		if(theme.mDetached) {
			PreferenceManager.setThemeType(ctx, true);
			PreferenceManager.setThemePackage(ctx, theme.mPackageName);
		} else {
		    setInnerTheme(ctx, theme.mId);
		}
	}
	
	static boolean isThemeDetached(Context context) {
		return PreferenceManager.getThemeType(context);
	}
	
	static int getThemeId(Context context) {
		return PreferenceManager.getThemeId(context, getMainThemeId(context));
	}
	
	static Resources.Theme getTheme(Context ctx) {
		final PackageManager pm = ctx.getPackageManager();
		Resources res = null;
        try {
            res = pm.getResourcesForApplication (PreferenceManager.getThemePackage(ctx));
            Resources.Theme rs = res.newTheme();
            
            int id_st = 0;
            try {
                //TODO: mConfig.getNameForDetachedTheme()
            	id_st = res
                    .getIdentifier(/*mConfig.getNameForDetachedTheme()*/"DetachedTheme", "style",PreferenceManager.getThemePackage(ctx));
            } catch(NullPointerException ex) {
            	Log.e("ThemeManager", ex.getMessage());
            }
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
