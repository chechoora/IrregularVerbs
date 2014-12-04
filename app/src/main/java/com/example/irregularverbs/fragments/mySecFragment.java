package com.example.irregularverbs.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.example.irregularverbs.adapters.IrrVerbsAdapterSecond;
import com.example.irregularverbs.app.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressLint("ValidFragment")
public class mySecFragment extends android.support.v4.app.Fragment implements Support {

	// stuff for ListView
	ListView lvIrregularVerbs;
	IrrVerbsAdapterSecond irregularVerbsAdapter;
	Intent intent;

	// all stuff for DB
	Cursor cursor;
	ArrayList<IrrVerb> lst = new ArrayList<IrrVerb>();
	List<IrrVerb> searchLst = new ArrayList<IrrVerb>();

	// all stuff for dialogs
	AlertDialog.Builder adb;
	IrrVerb sendVerb = new IrrVerb("Some", "Shit", "maybe", "but", 8);
	LinearLayout view;

	static int isSearched =0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater
				.inflate(R.layout.fragment_biatch, container, false);
		lvIrregularVerbs = (ListView) root.findViewById(R.id.lvSimple);
		fulllist(lst);
		Collections.reverse(lst);; 
		irregularVerbsAdapter = new IrrVerbsAdapterSecond(getActivity(), lst,
				MainActivity.db);
		lvIrregularVerbs.setAdapter(irregularVerbsAdapter);
		lvIrregularVerbs.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				sendVerb = irregularVerbsAdapter.getVerb(position);
				showDialog(sendVerb);
			}
		});
		lvIrregularVerbs.setSelection(MainActivity.fragpos1);
		return root;

	}

	public void fulllist(List<IrrVerb> list) {
		list.clear();
		MainActivity.db.open();
		cursor = MainActivity.db.getAllData(DataBase.DB_HIST);
		if (cursor.moveToFirst()) {
			String inf, ps, pp, tr;
			int id;
			do {
				id = cursor.getInt(cursor.getColumnIndex("_id"));
				inf = cursor.getString(cursor.getColumnIndex("verbs"));
				ps = cursor.getString(cursor.getColumnIndex("ps"));
				pp = cursor.getString(cursor.getColumnIndex("pp"));
				tr = cursor.getString(cursor.getColumnIndex("tr"));
				list.add(new IrrVerb(inf, ps, pp, tr, id));
			} while (cursor.moveToNext());
		}
	}

	@Override
	public void showDialog(IrrVerb _irrIrrVerb) {
		Dialog d=new Dialog();
		Bundle args=new Bundle();
		args.putParcelable("verb", _irrIrrVerb);
		d.setArguments(args);
		d.show(getFragmentManager(), "well try");
		
		
	}

	@Override
	public void search(String str) {
		if (lvIrregularVerbs != null) {
			if (isSearched == 0)
				MainActivity.fragpos1 = lvIrregularVerbs
						.getFirstVisiblePosition();
			if (!str.equals("")) {
				isSearched = 1;
				searchLst.clear();
				lvIrregularVerbs.setAdapter(null);
				for (IrrVerb iv : lst) {
					if (iv.verb.startsWith(str)) {
						searchLst.add(iv);
					}
				}
				irregularVerbsAdapter = new IrrVerbsAdapterSecond(
						getActivity(), searchLst, MainActivity.db);
				lvIrregularVerbs.setAdapter(irregularVerbsAdapter);
			} else {
				lvIrregularVerbs.setAdapter(null);
				irregularVerbsAdapter = new IrrVerbsAdapterSecond(
						getActivity(), lst, MainActivity.db);
				lvIrregularVerbs.setAdapter(irregularVerbsAdapter);
				lvIrregularVerbs.setSelection(MainActivity.fragpos1);
				isSearched = 0;

			}
		}
	}

	public void onDestroy(){
		super.onDestroy();
	}
	
	public void onPause() {
		super.onPause();
		MainActivity.fragpos1 = lvIrregularVerbs.getFirstVisiblePosition();
		Log.d("FragLifS", "Fragment onPause");
	}

	public void onResume() {
	    super.onResume();
	    /*if(lastposition!=0){
	    	lvIrregularVerbs.setSelection(lastposition);
	    }*/
	    Log.d("FragLifS", "Fragment onResume");
	  }

	@Override
	public int getPos() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPos() {

	}

}
