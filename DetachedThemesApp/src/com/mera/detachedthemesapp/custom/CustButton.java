package com.mera.detachedthemesapp.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.mera.detachedthemesapp.R;
import com.mera.detachedthemeslib.ThemeManager;

public class CustButton extends Button {

	public CustButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CustButton(Context context, AttributeSet attrs) {
		this(context, attrs, ThemeManager.getStyleIdValue(context,
				"custButtonStyle", R.attr.custButtonStyle));
	}

	public CustButton(Context context) {
		this(context, null);
	}

}
