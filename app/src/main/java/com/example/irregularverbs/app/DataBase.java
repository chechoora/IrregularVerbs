package com.example.irregularverbs.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class DataBase  {
	private static final String DB_NAME = "verbsDb";
	private static final int DB_VERSION = 1;
	public static final String DB_TABLE = "verbsTab";
	public static final String DB_HIST = "verbsListTab";

	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_V = "verbs";
	public static final String COLUMN_ID_HIST = "_idHist";
	public static final String COLUMN_PS = "ps";
	public static final String COLUMN_PP = "pp";
	public static final String COLUMN_T = "tr";
	private static final String DB_CREATE_HIST = 
		    "create table " + DB_HIST + "(" +
		      COLUMN_ID + " integer primary key autoincrement, " +
		      COLUMN_ID_HIST + " integer, " +
		      COLUMN_V + " text, " +
		      COLUMN_PS + " text, " +
		      COLUMN_PP + " text, " +
		      COLUMN_T + " text); ";
	
	private static final String DB_CREATE = 
		    "create table " + DB_TABLE + "(" +
		      COLUMN_ID + " integer primary key autoincrement, " +
		      COLUMN_V + " text, " +
		      COLUMN_PS + " text," +
		      COLUMN_PP + " text," +
		      COLUMN_T + " text);";
	
	private final Context mCtx;
	private DBHelper mDBHelper;
	private SQLiteDatabase mDB;
	
	private List<String> inf = new ArrayList<String>();
	private List<String> pp = new ArrayList<String>();
	private List<String> ps = new ArrayList<String>();
	private List<String> tr = new ArrayList<String>();

	public DataBase(Context ctx) {
		mCtx = ctx;
	}

	public void open() {
		mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
	    mDB = mDBHelper.getWritableDatabase();
	}


	  public void close() {
	    if (mDBHelper!=null) mDBHelper.close();
	  }
	  
	public void readFromFiles(List<String> lst,String file) {
		try {
			//AssetManager am = mCtx.getAssets();
			InputStream is = new ByteArrayInputStream(file.getBytes());
			InputStreamReader inputStreamReader = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(inputStreamReader);
			String str = "";
			while ((str = br.readLine()) != null) {
				lst.add(str);
			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
			Log.d("LOG_TAG", "хуйговно+ "+ e.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}	 
	}
	
	public void add(IrrVerb v){

	    String selectQuery = "SELECT _id FROM verbsListTab";           
	    Cursor c = mDB.rawQuery(selectQuery, null);
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_V, v.verb);
		cv.put(COLUMN_ID_HIST, v.id);
		cv.put(COLUMN_PS, v.pastSimple);
		cv.put(COLUMN_PP, v.pastParticiple);
		cv.put(COLUMN_T, v.translate);
	    int total = c.getCount();
	    if (total>15){
	    	c.moveToFirst();
		    int id = c.getInt(c.getColumnIndex("_id"));
			mDB.delete(DB_HIST, "_id= " + id, null);
	    }
		mDB.insert(DB_HIST, null, cv);	
	}

	public void delete(){
		  mDB.delete(DB_HIST, null, null);
	  }
	  
	public void delete(int id) {
		mDB.delete(DB_HIST, COLUMN_ID + "= " + id, null);
	}


	  public Cursor getAllData(String table) {
	    return mDB.query(table, null, null, null, null, null, null);
	  }

	  private class DBHelper extends SQLiteOpenHelper {

	    public DBHelper(Context context, String name, CursorFactory factory,
	        int version) {
	      super(context, name, factory, version);
	    }

		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.d("LOG_TAG", "хуйговно");
			ContentValues cv;
			db.execSQL(DB_CREATE);
			readFromFiles(inf, Support.inf);
			readFromFiles(pp, Support.pp);
			readFromFiles(ps, Support.ps);
			readFromFiles(tr, Support.tr);
			for (int i = 0; i < inf.size(); i++) {
				cv = new ContentValues();
				cv.put(COLUMN_V, inf.get(i));
				cv.put(COLUMN_PS, ps.get(i));
				cv.put(COLUMN_PP, pp.get(i));
				cv.put(COLUMN_T, tr.get(i));
				db.insert(DB_TABLE, null, cv);
			}
			db.execSQL(DB_CREATE_HIST);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}

	  }
}
