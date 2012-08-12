package com.mera.detachedthemeslib;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

class ThemedResources extends Resources {

    private Resources mDetachedResources = null;
    private Resources mNativeResources = null;

    public ThemedResources(Context ctx, Resources nativeResources,
            Resources downloadedResources) {
        super(nativeResources.getAssets(), nativeResources.getDisplayMetrics(),
                nativeResources.getConfiguration());

        if (ThemeManager.isThemeDetached(ctx)) {
            Resources res = ThemeManager.getDetachedResources(ctx);
            if (res != null) {
                mDetachedResources = res;
            }
        }

        if (mDetachedResources == null) {
            mDetachedResources = nativeResources;
        }
        mNativeResources = nativeResources;
    }

    @Override
    public Drawable getDrawable(int id) {
        try {
            return mDetachedResources.getDrawable(id);
        } catch (Resources.NotFoundException ex) {
            return this.getDrawable(id);
        }
    }

    @Override
    public XmlResourceParser getLayout(int id) throws NotFoundException {
        return mNativeResources.getLayout(id);
    }
    
    @Override
    public String getString(int id) throws NotFoundException {
    	return mNativeResources.getString(id);
    }
    
    @Override
    public String getString(int id, Object... formatArgs) throws NotFoundException {
    	return mNativeResources.getString(id, formatArgs);
    }
    
    @Override
    public TypedArray obtainAttributes(AttributeSet set, int[] attrs) {
    	return mNativeResources.obtainAttributes(set, attrs);
    }
}
