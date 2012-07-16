package com.mera.detachedthemeslib;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

class ThemedResources extends Resources {
	
	private Resources mDetachedResources = null;
	
	public ThemedResources(Context ctx, Resources nativeResources, Resources downloadedResources) {
		super(nativeResources.getAssets(), nativeResources.getDisplayMetrics(), nativeResources.getConfiguration());
		
		if (ThemeManager.isThemeDetached(ctx)) {
			Resources res = ThemeManager.getDetachedResources(ctx);
			if (res != null) {
				mDetachedResources = res;
			}
		}
		
		if( mDetachedResources == null) {
			mDetachedResources = nativeResources;
		}
	}
	
	@Override
	public Drawable getDrawable(int id) {
		try {
			return mDetachedResources.getDrawable(id);
		} catch (Resources.NotFoundException ex ) {
			return this.getDrawable(id);
		}
	}
}
