package com.derek.cahalog.Spinnerdb;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.webkit.WebChromeClient.CustomViewCallback;

public class DBOpenHelper   {
	private static final String DATABASE_NAME = "favorite_item.db";
	private static final int DATABASE_VERSION = 1;
	public static SQLiteDatabase favoriteItemDB;
	private FavoriteItemDBHelper mDBHelper;
    private Context mContext;
    
    private class FavoriteItemDBHelper extends SQLiteOpenHelper{
    	// 생성자
		public FavoriteItemDBHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}
		
		public FavoriteItemDBHelper(Context context){
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
	
		// 최초 DB를 만들때 한번만 호출된다.
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(FavoriteDatabase.CreateFavoriteItemDB._CREATE);
	
		}
	
		// 버전이 업데이트 되었을 경우 DB를 다시 만들어 준다.
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS "+FavoriteDatabase.CreateFavoriteItemDB.DATABASE_TABLE_NAME);
			onCreate(db);
		}
    }
    
    public DBOpenHelper(Context context){
    	mContext = context;
    }
 
    public DBOpenHelper open() throws SQLException{
        mDBHelper = new FavoriteItemDBHelper(mContext, DATABASE_NAME, null, DATABASE_VERSION);
        favoriteItemDB = mDBHelper.getWritableDatabase();
        return this;
    }
 
    public void close(){
    	favoriteItemDB.close();
    }
    

	// Insert DB
	public long insertColumn(String item){
		ContentValues values = new ContentValues();
		values.put(FavoriteDatabase.CreateFavoriteItemDB.FAVORITE_ITEM_NAME, item);
		return favoriteItemDB.insert(FavoriteDatabase.CreateFavoriteItemDB.DATABASE_TABLE_NAME, null, values);
		
	}

	// Update DB
	public boolean updateColumn(long id , String item){
		ContentValues values = new ContentValues();
		values.put(FavoriteDatabase.CreateFavoriteItemDB.FAVORITE_ITEM_NAME, item);
		return favoriteItemDB.update(FavoriteDatabase.CreateFavoriteItemDB.DATABASE_TABLE_NAME, values, "_id="+id, null) > 0;
	}

	// Delete ID
	public boolean deleteColumn(long id){
		return favoriteItemDB.delete(FavoriteDatabase.CreateFavoriteItemDB.DATABASE_TABLE_NAME, "_id="+id, null) > 0;
	}
	
	// Delete Contact
	public boolean deleteColumn(String number){
		return favoriteItemDB.delete(FavoriteDatabase.CreateFavoriteItemDB.DATABASE_TABLE_NAME, "contact="+number, null) > 0;
	}
	
	// Select All
	public Cursor getAllColumns(){
		return favoriteItemDB.query(FavoriteDatabase.CreateFavoriteItemDB.DATABASE_TABLE_NAME, null, null, null, null, null, null);
	}

	// ID 컬럼 얻어 오기
	public Cursor getColumn(long id){
		Cursor c = favoriteItemDB.query(FavoriteDatabase.CreateFavoriteItemDB.DATABASE_TABLE_NAME, null, 
				"_id="+id, null, null, null, null);
		if(c != null && c.getCount() != 0)
			c.moveToFirst();
		return c;
	}

	// 이름 검색 하기 (rawQuery)
	public Cursor getMatchName(String name){
		Cursor c = favoriteItemDB.rawQuery( "select * from address where name=" + "'" + name + "'" , null);
		return c;
	}
	
	
	 public List<String> getAllLabels(){
	    	List<String> labels = new ArrayList<String>();
	    	
	        // Select All Query
	        String selectQuery = "SELECT  * FROM " + FavoriteDatabase.CreateFavoriteItemDB.DATABASE_TABLE_NAME;
	     
	        favoriteItemDB = mDBHelper.getReadableDatabase();
	        Cursor cursor = favoriteItemDB.rawQuery(selectQuery, null);
	     
	        // looping through all rows and adding to list
	        if (cursor.moveToFirst()) {
	            do {
	            	labels.add(cursor.getString(1));
	            } while (cursor.moveToNext());
	        }
	        
	        // closing connection
	        cursor.close();
	        favoriteItemDB.close();
	    	
	    	// returning lables
	    	return labels;
	    }
	
	
}
