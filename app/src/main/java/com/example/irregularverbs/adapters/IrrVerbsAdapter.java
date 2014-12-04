package com.example.irregularverbs.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.irregularverbs.app.DataBase;
import com.example.irregularverbs.app.IrrVerb;
import com.example.irregularverbs.app.R;

import java.util.List;

public class IrrVerbsAdapter extends BaseAdapter {
	
	Context ctx;
	LayoutInflater lInf;
	List<IrrVerb> objects;
	DataBase db;
	
	public IrrVerbsAdapter(Context _ctx,List<IrrVerb>lst,DataBase _db) {
		ctx = _ctx;
		objects = lst;
		lInf = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		db=_db;
	}

	@Override
	public int getCount() {
		return objects.size();
	}

	@Override
	public Object getItem(int position) {
		return objects.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	public  IrrVerb getVerb(int position){
		return (IrrVerb)getItem(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view=convertView;
		if(view == null){
			view = lInf.inflate(R.layout.list_verbs, parent, false);
		}
		IrrVerb iv= getVerb(position);
		((TextView)view.findViewById(R.id.tvInfinitiveList)).setText(iv.verb);
		return view;
	}

}
