package com.mera.detachedthemeslib;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;

public abstract class BaseActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		ActivityManager.setThemeForActivity(this);
		super.onCreate(savedInstanceState);
	}

	@Override
	public Resources getResources() {
		return ActivityManager.getResourcesForActivity(this, super.getResources());
	}

	@Override
	public Resources.Theme getTheme() {
		return ActivityManager.getThemeForActivity(this, super.getTheme());
	}
}
