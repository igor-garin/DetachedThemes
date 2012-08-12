package com.mera.detachedthemesapp;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mera.detachedthemeslib.BaseListActivity;
import com.mera.detachedthemeslib.ThemesConfiguration;
import com.mera.detachedthemeslib.ThemeManager;

public class SwitchThemeActivity extends BaseListActivity {

    private LayoutInflater mInflater;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        List<ThemeManager.Theme> themes = ThemeManager.getThemes(this,
                new ThemesConfiguration()
                        .addInnerTheme(R.style.CustomTheme2, "Green"));

        ArrayAdapter<ThemeManager.Theme> adapter = new ArrayAdapter<ThemeManager.Theme>(
                this, android.R.layout.simple_list_item_1, themes) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View row;

                if (null == convertView) {
                    row = mInflater.inflate(
                            android.R.layout.simple_list_item_1, null);
                } else {
                    row = convertView;
                }

                TextView tv = (TextView) row.findViewById(android.R.id.text1);
                tv.setText(((ThemeManager.Theme) getItem(position)).mTitle);
                return row;
            }

        };

        getListView().setAdapter(adapter);
        getListView().setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                ThemeManager.Theme theme = (ThemeManager.Theme) getListView()
                        .getAdapter().getItem(position);

                ThemeManager.setTheme(SwitchThemeActivity.this, theme);
                Intent intent = new Intent();
                intent.setClass(SwitchThemeActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                SwitchThemeActivity.this.startActivity(intent);
            }
        });

    }
}
