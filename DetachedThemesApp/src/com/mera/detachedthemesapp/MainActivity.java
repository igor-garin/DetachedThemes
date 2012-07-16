package com.mera.detachedthemesapp;

import java.util.List;

import com.mera.detachedthemeslib.BaseActivity;
import com.mera.detachedthemeslib.Configuration;
import com.mera.detachedthemeslib.ThemeManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class MainActivity extends BaseActivity {
	
	@Override
	public Configuration getAppConfiguration() {
		return AppConfiguration.themeConfiguration;
	}
	

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        String[] items = new String[] {"One", "Two", "Three"};
        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        
        
        ListView listview = (ListView)this.findViewById(R.id.listView1);
        ArrayAdapter<String> listadapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items);
        listview.setAdapter(listadapter);

	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		
		List<ThemeManager.Theme> themes = ThemeManager.getThemes(this);
		for (ThemeManager.Theme t : themes) {
			menu.removeItem(t.mId);
			menu.add(0, t.mId, 0, t.mTitle);
		}
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		ThemeManager.setTheme(this, item.getItemId());
		this.finish();
		this.startActivity(new Intent(this, this.getClass()));
	    return true;
	}
}
