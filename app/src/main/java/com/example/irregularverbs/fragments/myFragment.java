package com.example.irregularverbs.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import com.example.irregularverbs.adapters.IrrVerbsAdapter;
import com.example.irregularverbs.app.*;

import java.util.ArrayList;
import java.util.List;


@SuppressLint("ValidFragment")
public class myFragment extends android.support.v4.app.Fragment implements Support {
	
	//stuff for Listview
	ListView lvIrregularVerbs;
	IrrVerbsAdapter irregularVerbsAdapter;
	Intent intent;
	int lastposition;
	Parcelable state;
	
	//all stuff for DB;
	Cursor cursor;
	ArrayList<IrrVerb>lst= new ArrayList<IrrVerb>();
	List<IrrVerb> searchLst = new ArrayList<IrrVerb>();
	
	//all stuff for dialogs
	AlertDialog.Builder adb;
	IrrVerb sendVerb = new IrrVerb("Some","Shit","maybe","but",8);
	LinearLayout view;
	
	//for action bar 
	SharedPreferences sp;
	Editor ed;
	SearchView mSearchView;
	@SuppressLint("ValidFragment")
	//and maybe not here
	
	static int isSerached=0;
	

	
	public myFragment(){
		super();
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_biatch, container, false);
		lvIrregularVerbs = (ListView) root.findViewById(R.id.lvSimple);
		/*if (savedInstanceState != null) {
			lastposition=savedInstanceState.getInt("position");
	
		}*/
		fullList(lst);
		irregularVerbsAdapter = new IrrVerbsAdapter(getActivity(), lst, MainActivity.db);
		lvIrregularVerbs.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		lvIrregularVerbs.setAdapter(irregularVerbsAdapter);
		lvIrregularVerbs.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				sendVerb = irregularVerbsAdapter.getVerb(position);
				MainActivity.db.add(sendVerb);
				
				showDialog(sendVerb);
			}
		});
		irregularVerbsAdapter.notifyDataSetChanged();	
		lvIrregularVerbs.setSelection(MainActivity.fragpos0);
		return root;
		
	}
	
	public void fullList(List<IrrVerb> list) {
		list.clear();
		MainActivity.db.open();
		cursor = MainActivity.db.getAllData(DataBase.DB_TABLE);
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
		//MainActivity.db.close();
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
			if (isSerached == 0)
				MainActivity.fragpos0 = lvIrregularVerbs
						.getFirstVisiblePosition();
			if (!str.equals("")) {
				isSerached = 1;
				searchLst.clear();
				lvIrregularVerbs.setAdapter(null);
				for (IrrVerb iv : lst) {
					if (iv.verb.startsWith(str)) {
						searchLst.add(iv);
					}
				}
				irregularVerbsAdapter = new IrrVerbsAdapter(getActivity(),
						searchLst, MainActivity.db);
				lvIrregularVerbs.setAdapter(irregularVerbsAdapter);
			} else {
				lvIrregularVerbs.setAdapter(null);
				irregularVerbsAdapter = new IrrVerbsAdapter(getActivity(), lst,
						MainActivity.db);
				lvIrregularVerbs.setAdapter(irregularVerbsAdapter);
				lvIrregularVerbs.setSelection(MainActivity.fragpos0);
				isSerached = 0;

			}
		}
	}

	public int getPos(){
		return lvIrregularVerbs.getFirstVisiblePosition();
	}

	public void onDestroy(){
		 MainActivity.fragpos0=lvIrregularVerbs.getFirstVisiblePosition();
		super.onDestroy();
	}
	
	public void onPause() {
	    super.onPause(); 
	    Log.d("FragLifS", "Fragment onPause");
	  }
	
	public void onResume() {
	    super.onResume();
	   /* if(lastposition!=0){
	    	lvIrregularVerbs.setSelection(lastposition);
	    }
	    Log.d("FragLifS", "Fragment onResume");*/
	  }


	@Override
	public void setPos() {
		/*lvIrregularVerbs.setAdapter(null);
		irregularVerbsAdapter = new IrrVerbsAdapter(getActivity(), lst,
				MainActivity.db);
		lvIrregularVerbs.setAdapter(irregularVerbsAdapter);*/
		lvIrregularVerbs.setSelection(MainActivity.fragpos0);
	}
	
  
   /* @Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("position", lvIrregularVerbs.getLastVisiblePosition());
	}*/

	

}
