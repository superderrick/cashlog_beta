package com.derek.cashlog.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.p2p.WifiP2pManager.GroupInfoListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.derek.cashlog.widget.CalendarMonthAdapter;
import com.derek.cashlog.widget.CalendarMonthView;
import com.derek.cashlog.widget.TitleGridViewAdapter;
import com.derek.cashlog.widget.CalendarMonthView.OndataSelectionListener;
import com.derek.cashlog.widget.MonthItem;
import com.example.cashlog.R;

public class CalenderMain extends Activity {
	public static final String LOGTAG = "CalenderMain";
	//reference 
	private CalendarMonthView   mCalendarView;
	
	//adapter
	private CalendarMonthAdapter monthViewAdapter;
	
	//gui component
	private TextView monthText;
	private Button mPreviousButton;
	private Button mNextButton;
	private Button mInputButton; 
	
	//test 
	private MonthItem mTempItem;
	
	private GridView mTitleGridView;
	private TitleGridViewAdapter adapter ;
	
	
	private int curYear;
	private int curMonth;
	
	// this is the date information 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calender_main);
		init();
	}

	private void init() {
		setCalender();
		setGuiComponent();
	}
	

	@Override
	public void onBackPressed() {
		Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.finish_app);
		builder.setPositiveButton(R.string.answer_yes, new DialogInterface.OnClickListener() {
      
        public void onClick(DialogInterface dialog, int which) {    
         android.os.Process.killProcess(android.os.Process.myPid());      
                  // 프로세스 및 스레드 죽이기.
         finish();
        }
       });
		
       builder.setNegativeButton(R.string.answer_no, new DialogInterface.OnClickListener() {
     
        public void onClick(DialogInterface dialog, int which) {
         dialog.cancel();
        }
       });
       builder.show();
		//		super.onBackPressed();
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			
		}
		return super.onKeyDown(keyCode, event);
	}

	private void setCalender() {
		setMonthView();
		setListener();		
	}

	private void setGuiComponent() {
		setCurrentDay();
		setPrevousButton();
		setNextButton();
		setTitleGridView();
		setInPutButton();
	}



	private void setInPutButton() {
		mInputButton = (Button)findViewById(R.id.new_button);
		mInputButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if( mTempItem != null && mTempItem.getDayValue() !=0)
				{
					Intent intent = new Intent(CalenderMain.this, InputActivity.class);
					intent.putExtra("Info",mTempItem);
					startActivity(intent);
				}
				else
				{
					Toast t = Toast.makeText(getApplicationContext(), getResources().getString(R.string.input_correctly), Toast.LENGTH_SHORT);
					t.show();
				}
			}
		});
	}

	private void setTitleGridView() {
		mTitleGridView = (GridView)findViewById(R.id.title_gridview);
		adapter = new TitleGridViewAdapter(this);
		mTitleGridView.setBackgroundColor(Color.YELLOW);
		mTitleGridView.setVerticalSpacing(2);
		mTitleGridView.setHorizontalSpacing(2);
		mTitleGridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
		mTitleGridView.setAdapter(adapter);
		
	}

	private void setNextButton() {
		mNextButton = (Button)findViewById(R.id.monthNext);
		mNextButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				monthViewAdapter.setNextMonth();
        		monthViewAdapter.notifyDataSetChanged();
        		setMonthText();
			}
		});
	}

	private void setPrevousButton() {
		mPreviousButton = (Button)findViewById(R.id.monthPrevious);
		mPreviousButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				monthViewAdapter.setPreviousMonth();
        		monthViewAdapter.notifyDataSetChanged();
        		setMonthText();
			}
		});
	}

	private void setCurrentDay() {
		monthText = (TextView)findViewById(R.id.monthText);
		setMonthText();
	}

	private void setMonthText() {
		curYear = monthViewAdapter.getCurrentYear();
        curMonth = monthViewAdapter.getCurrentMonth();
        monthText.setText(curYear + "년" + (curMonth+1) + "월");
	}

	private void setListener() {
		mCalendarView.setOnDataSelectionListener(new OndataSelectionListener() {
			
			@Override
			public void onDataSelected(AdapterView parent, View view, int position,
					long id) {
					MonthItem curItem = (MonthItem) monthViewAdapter.getItem(position);
					mTempItem = curItem;
					int day = curItem.getDayValue();
					Log.d("CalendarMonthViewActivity", "Selected : " + day);
			}
		});
	}

	private void setMonthView() {
		mCalendarView = (CalendarMonthView)findViewById(R.id.monthView);
		monthViewAdapter = new CalendarMonthAdapter(this);
		mCalendarView.setAdapter(monthViewAdapter);
	}

	
}
