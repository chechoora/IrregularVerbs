package com.example.irregularverbs.app;



import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class Dialog extends DialogFragment {


	IrrVerb sendVerb;
	AlertDialog.Builder adb;
	View view;

	@SuppressLint("ValidFragment")
	@Override
	public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
		sendVerb=getArguments().getParcelable("verb");
		adb = new AlertDialog.Builder(getActivity());
		view = getActivity().getLayoutInflater().inflate(R.layout.dialog, null);
		adb.setView(view);
		adb.setTitle(sendVerb.verb);
		TextView tvPastSimple = (TextView) view.findViewById(
				R.id.tvPastSimple);
		TextView tvTranslate = (TextView) view.findViewById(
				R.id.tvTranslate);
		TextView tvPastParticiple = (TextView) view.findViewById(
				R.id.tvPastParticiple);
		tvPastSimple.setText(sendVerb.pastSimple);
		tvPastParticiple.setText(sendVerb.pastParticiple);
		tvTranslate.setText(sendVerb.translate);
		
		adb.setPositiveButton("Ok", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		return adb.create();
	}

	
    
    @Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelable("myVerb", sendVerb);
	}
	

}
