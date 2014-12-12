package com.example.irregularverbs.app;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.view.KeyEvent;

public class PrefActivity extends PreferenceActivity {


	@Override
	protected void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			setResult(1);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	public static class MyPreferenceFragment extends PreferenceFragment
	{
		@Override
		public void onCreate(final Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.pref);
			Preference pref = (Preference) findPreference("btnClH");
			pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(Preference preference) {
					MainActivity.clearHistory=1;
					return false;
				}
			});
		}
	}
}




	/*@Override
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
	}*/
