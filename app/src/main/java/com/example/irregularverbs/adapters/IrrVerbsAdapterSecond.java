package com.example.irregularverbs.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.example.irregularverbs.app.DataBase;
import com.example.irregularverbs.app.IrrVerb;
import com.example.irregularverbs.app.R;

import java.util.ArrayList;
import java.util.List;

public class IrrVerbsAdapterSecond  extends BaseAdapter{
	
	Context ctx;
	LayoutInflater lInf;
	List<IrrVerb> objects;
	DataBase db;
	List<View> viewsToDelete=new ArrayList<View>();
	
	public IrrVerbsAdapterSecond(Context _ctx,List<IrrVerb>lst,DataBase _db) {
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
			view = lInf.inflate(R.layout.list_second, parent, false);
		}
		viewsToDelete.add(view);
		IrrVerb iv= getVerb(position);
		((TextView)view.findViewById(R.id.tvInfinitiveListSecond)).setText(iv.verb);
		((Button)view.findViewById(R.id.btnDeleteSec)).setTag(position);
		((Button)view.findViewById(R.id.btnDeleteSec)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int id = getVerb((Integer) v.getTag()).id;
				db.delete(id);
				IrrVerbsAdapterSecond.this.notifyDataSetInvalidated();
				int t=(Integer) v.getTag();
				viewsToDelete.remove(t);
				objects.remove(t);
				IrrVerbsAdapterSecond.this.notifyDataSetChanged();
			}
		});
		
		
		return view;
	}
	

	/*ArrayList<IrrVerb> getVerbs(){
		ArrayList<IrrVerb> fav = new ArrayList<IrrVerb>();
	    for (IrrVerb p : objects) {
	      if (p.favorite==1)
	        fav.add(p);
	    }
	    return fav;
	}*/
	

}
