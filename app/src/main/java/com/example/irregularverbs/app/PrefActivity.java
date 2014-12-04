package com.example.irregularverbs.app;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;

public class PrefActivity extends PreferenceActivity implements OnPreferenceClickListener{
	
	SharedPreferences sPref;
	Editor ed;
	int sw;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		sPref=getSharedPreferences("myPref", MODE_PRIVATE);
    	ed = sPref.edit();
    	ed.putInt("clearH", 0);
    	ed.commit();
		addPreferencesFromResource(R.xml.pref);
		Preference btnClH = findPreference("btnClH");
		btnClH.setOnPreferenceClickListener(this);
	}

	@Override
	public boolean onPreferenceClick(Preference preference) {
		if (preference.getKey().equals("btnClH")) {
			ed.putInt("clearH", 1);
			ed.commit();
		}
		return false;
	}

}
