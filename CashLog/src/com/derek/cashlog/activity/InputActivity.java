package com.derek.cashlog.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Text;

import com.derek.cahalog.Spinnerdb.DBOpenHelper;
import com.derek.cahalog.Spinnerdb.FavoriteItemInfo;
import com.derek.cashlog.widget.MonthItem;
import com.example.cashlog.R;
import com.example.cashlog.R.string;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class InputActivity extends DerekSpinner {
	//logtag 
	public static final String LOGTAG = "InputActivity";
	

	//dialog title 
	public static final int ADD_SORT_DIALOG = 0;
	public static final int ADD_SPINNER_ITEM_DIALOG = 1;
	
	//dataBase Information
	private DBOpenHelper mDBOpenHelper;
    private List<String> itemlables ;
    private ArrayAdapter<String> dataAdapter;
	ArrayAdapter<string> adapter;
	
	//gui component 
	private Spinner mSpinner;
	private TextView mYearView;
	private TextView mMonthView;
	private TextView mDayView;
	private Intent mIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.input_layout);
		init();
	}
	private void init() {
		setGuiComPonent();
		shutAllDialog();
	}

	private void setGuiComPonent() {
		settingSpiner();
		setDateInfo();
	}

	private void setDateInfo() {
		setCurrentYear();
		setCurrentMonth();
		setCurrentDay();
	}
	private void setCurrentDay() {
		mDayView = (TextView)findViewById(R.id.text_day);
		Bundle bundle = getIntent().getExtras();
		MonthItem currentItem = bundle.getParcelable("Info");
		mDayView.setText(""+currentItem.getDayValue() + getResources().getString(R.string.info_day) );
	}
	private void setCurrentMonth() {
		mMonthView = (TextView)findViewById(R.id.text_month);
		Bundle bundle = getIntent().getExtras();
		MonthItem currentItem = bundle.getParcelable("Info");
		mMonthView.setText(""+currentItem.getMonthValue() +  getResources().getString(R.string.info_month));
	}
	private void setCurrentYear() {
		mYearView = (TextView)findViewById(R.id.text_year);
		Bundle bundle = getIntent().getExtras();
		MonthItem currentItem = bundle.getParcelable("Info");
		mYearView.setText(""+currentItem.getYearValue() + getResources().getString(R.string.info_year));
//		mYearView.setText(mIntent.getExtras().get("year").toString()+getResources().getString(R.string.info_year));
		
	}
	private void settingSpiner() {
		setSpinnerArray();
	}
	
	private void setSpinnerArray() {
		itemlables = new ArrayList<String>();
		mSpinner = (Spinner)findViewById(R.id.edit_spinner);
		
		mSpinner.setOnItemSelectedListener(mFavoriteListner);
		mSpinner.setOnItemLongClickListener(mFavoriteLongClickListener);
	
		 initialData();
		 loadData();
	}


	private void loadData() {
		itemlables = mDBOpenHelper.getAllLabels();
		 dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, itemlables);
		 dataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		 mSpinner.setAdapter(dataAdapter);
	}
	
	private void initialData() {
		 // DB Create and Open
		mDBOpenHelper = new DBOpenHelper(this);
		mDBOpenHelper.open();
		
		Cursor c = mDBOpenHelper.getAllColumns();
		Log.e(LOGTAG, " current  :" + c.getCount());
		if(c.getCount() < 7)
		{
			mDBOpenHelper.insertColumn(getResources().getString(R.string.spinner_noitem));
			mDBOpenHelper.insertColumn(getResources().getString(R.string.spinner_food));
			mDBOpenHelper.insertColumn(getResources().getString(R.string.spinner_transport));
			mDBOpenHelper.insertColumn(getResources().getString(R.string.spinner_asif));
			mDBOpenHelper.insertColumn(getResources().getString(R.string.spinner_date));
			mDBOpenHelper.insertColumn(getResources().getString(R.string.spinner_suddeny));
			mDBOpenHelper.insertColumn(getResources().getString(R.string.spinner_neww));
		}
	}


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		shutAllDialog();
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		switch (id) {
		case ADD_SORT_DIALOG:
		{
			dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
			dialog.setContentView(R.layout.popup_dialog_two_buttons_with_edittext);
			dialog.getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
					WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);									
			break;
		}
		case ADD_SPINNER_ITEM_DIALOG:
		{
			dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
			dialog.setContentView(R.layout.popup_dialog_two_buttons_with_edittext);
			dialog.getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
					WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);									
			break;
		}
		default:
			break;
		}
		return dialog;
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog, Bundle args) {
		// TODO Auto-generated method stub
		switch (id) {
		case ADD_SORT_DIALOG:
		{
			final EditText titleEditText = (EditText)dialog.findViewById(R.id.edittext_for_title);
			EditText title = (EditText)dialog.findViewById(R.id.edittext_for_title);
			
			Button button = null;
			button = (Button)dialog.findViewById(R.id.okay_button);
			button.setOnClickListener(new View.OnClickListener() {					
				@Override
				public void onClick(View v) {
					Log.e(LOGTAG, titleEditText.getText().toString());
					mDBOpenHelper.open();
					Cursor c = mDBOpenHelper.getAllColumns();
					
					if(c.getCount()<11)
					{
						mDBOpenHelper.insertColumn(titleEditText.getText().toString());
						loadData();
						mSpinner.setSelection(itemlables.size()-1);
					}
					else
					{
						mSpinner.setSelection(0);
						Toast tt = Toast.makeText(getApplicationContext(), getResources().getString(R.string.input_no_one), Toast.LENGTH_SHORT);
						tt.show();
					}
					removeDialog(ADD_SORT_DIALOG);
				}
			});
			
			button = (Button)dialog.findViewById(R.id.cancel_button);
			button.setOnClickListener(new View.OnClickListener() {					
				@Override
				public void onClick(View v) {
					mSpinner.setSelection(0);
					removeDialog(ADD_SORT_DIALOG);
				}
			});	
			break;
		}
			

		}
		super.onPrepareDialog(id, dialog, args);
	}
	
	private void shutAllDialog(){
		removeDialog(ADD_SORT_DIALOG);
	}
	
   private OnItemLongClickListener mFavoriteLongClickListener = new OnItemLongClickListener() {

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View parent, int no,
			long arg3) {
		Toast t = Toast.makeText(getApplicationContext(), "sd" + no, Toast.LENGTH_SHORT);
		t.show();
		return true;
	}
};	

   
   private OnItemSelectedListener mFavoriteListner = new OnItemSelectedListener() {

	@Override
	public void onItemSelected(AdapterView<?> arg0, View view, int Selectedno,
			long arg3) {
		if(Selectedno ==6)
		{
			showDialog(ADD_SORT_DIALOG);
		}
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
	}
};
	     

}
