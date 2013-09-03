package com.derek.cahalog.Spinnerdb;

import android.provider.BaseColumns;

public class FavoriteDatabase {
	
	public FavoriteDatabase(){
		
		
	}
	public static final class CreateFavoriteItemDB implements BaseColumns{
		public static final String DATABASE_TABLE_NAME = "favorite_item_table";
		public static final String FAVORITE_ITEM_NAME = "favorite_item_name";
		public static final String _CREATE = 
		            "CREATE TABLE " + CreateFavoriteItemDB.DATABASE_TABLE_NAME + " ("
				+ CreateFavoriteItemDB._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"+ CreateFavoriteItemDB.FAVORITE_ITEM_NAME + " TEXT" + ");";
	}

}
