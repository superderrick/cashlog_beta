package com.derek.cashlog.activity;

import com.derek.cahalog.Spinnerdb.DBOpenHelper;

import android.app.Activity;
import android.os.Bundle;

public class DerekSpinner extends Activity {
	
	protected DBOpenHelper spinDataBaseHelper = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {
		setDataBase();
	}

	private void setDataBase() {
		spinDataBaseHelper = new DBOpenHelper(this.getApplicationContext());
	}
	
	 @Override
	    protected void onDestroy() {
	    	super.onDestroy();
	    	if(spinDataBaseHelper != null)
	    		spinDataBaseHelper.close();
	    }
}
