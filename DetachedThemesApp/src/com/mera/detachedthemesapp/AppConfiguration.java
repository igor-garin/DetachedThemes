package com.mera.detachedthemesapp;

import com.mera.detachedthemeslib.Configuration;

public class AppConfiguration extends Configuration {

	public static final Configuration themeConfiguration;
	
	static {
		themeConfiguration = new ThisAppConfiguration();
	}
	
	private static class ThisAppConfiguration extends Configuration {
		
		public ThisAppConfiguration() {
			this.addInnerTheme(R.style.CustomTheme, "Default", true);
			this.addInnerTheme(R.style.CustomTheme2, "Green", false);
			this.addNameForDetachedTheme("NewTheme");
			this.addPackagePrefixName("com.mera.detachedthemesapp.theme.");
		}
	}
	
	
}
