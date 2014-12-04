package com.example.irregularverbs.app;

import android.os.Parcel;
import android.os.Parcelable;

public class IrrVerb implements Parcelable {
	public String verb;
	String pastSimple;
	String pastParticiple;
	String translate;
	public int id;

	public IrrVerb(String _verb, String _pastSimple, String _pastParticiple,
			String _translate,int _id) {
		verb = _verb;
		pastSimple = _pastSimple;
		pastParticiple = _pastParticiple;
		translate = _translate;
		id = _id;
	}
	
	

	public static final Creator CREATOR = new Creator() {
		public IrrVerb createFromParcel(Parcel in) {
			return new IrrVerb(in);
		}

		public IrrVerb[] newArray(int size) {
			return new IrrVerb[size];
		}
	};
	
	public IrrVerb(Parcel in) {
		verb = in.readString();
		pastSimple = in.readString();
		pastParticiple = in.readString();
		translate = in.readString();
		id = in.readInt();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(verb);
		dest.writeString(pastSimple);
		dest.writeString(pastParticiple);
		dest.writeString(translate);
		dest.writeInt(id);
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public String toString() {
		return id + " " + verb + " " + translate;
	}
	
}
