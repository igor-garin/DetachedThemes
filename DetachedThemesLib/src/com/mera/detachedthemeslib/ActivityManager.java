package com.mera.detachedthemeslib;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;

class ActivityManager {

	static void setThemeForActivity(Activity act) {
		ThemeManager.init(act);
		if(!ThemeManager.isThemeDetached(act)) {
			act.setTheme(ThemeManager.getThemeId(act));
		}
	}
	
	static Resources.Theme getThemeForActivity(Context ctx, Resources.Theme superTheme) {
		
		if (ThemeManager.isThemeDetached(ctx)) {
			Resources.Theme theme = ThemeManager.getTheme(ctx);
			if (theme != null) {
				return theme;
			}
		}
		
		return superTheme;
	}
	
	static Resources getResourcesForActivity(Context ctx, Resources superResources) {
		return new ThemedResources(ctx, superResources, ThemeManager.getDetachedResources(ctx));
	}
	
}
