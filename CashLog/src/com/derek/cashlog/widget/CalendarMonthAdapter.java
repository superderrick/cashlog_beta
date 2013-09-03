package com.derek.cashlog.widget;

import java.sql.Time;
import java.util.Calendar;

import android.content.Context;
import android.graphics.Color;
import android.net.rtp.RtpStream;
import android.util.AttributeSet;
import android.util.Log;
import android.util.MonthDisplayHelper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

public class CalendarMonthAdapter extends BaseAdapter {
	
	//logtag 
	public static final String LOGTAG = "CalendarMonthAdapter";
	
	//context 
	private Context mContext;
	
	//calender information
	private int countColumn = 7;
	
	private int mStartDay;
	private int weekOfFirstDay;
	private int currentYear;
	private int currentMonth;
	
	private int firstDay;
	private int lastDay;
	
	//colorValue
	public static int oddColor = Color.BLUE;
	public static int headColor = Color.GREEN;
	
	private int selectedPosition = -1;
	private MonthItem [] items;

	Calendar mCalendar;
	boolean recreateItems = false;
	
	public CalendarMonthAdapter(Context context) {
		super();

		mContext = context;
		
		init();
	}
	
	public CalendarMonthAdapter(Context context, AttributeSet attrs) {
		super();

		mContext = context;
		
		init();
	}

	private void init() {
		items = new MonthItem[7 * 6];
		
		// biring the current the calender referece .
		mCalendar = Calendar.getInstance();
		
		recalculate();
		resetDayNumbers();
		
	}
	
	private void resetDayNumbers() {
		for (int i = 0; i < 42; i++) {
			// calculate day number
			int dayNumber = (i+1) - firstDay;
			if (dayNumber < 1 || dayNumber > lastDay) {
				dayNumber = 0;
			}
			
	        // save as a data item
	        items[i] = new MonthItem(dayNumber,currentMonth+1,currentYear);
		}
	}

	private void recalculate() {
		
		// set to the first day of the month
		mCalendar.set(Calendar.DAY_OF_MONTH, 1);
		
		// get week day this value is start number 
		int dayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK);
		
		// dayOfWeek - 1 
		firstDay = getFirstDay(dayOfWeek);
		
		mStartDay = mCalendar.getFirstDayOfWeek();
		currentYear = mCalendar.get(Calendar.YEAR);
		currentMonth = mCalendar.get(Calendar.MONTH);
		
		// lastDAy
		lastDay = getMonthLastDay(currentYear, currentMonth);
		
		
		int diff = mStartDay - Calendar.SUNDAY - 1;
		weekOfFirstDay = getFirstDayOfWeek();
		
		Log.e(LOGTAG, " eachValue : " + dayOfWeek + " firstday" + firstDay +  "lastday" + lastDay + "weekofday" + weekOfFirstDay);
	}

	private int getFirstDayOfWeek() {
		int startDay = Calendar.getInstance().getFirstDayOfWeek();
		if(startDay == Calendar.SATURDAY)
		{
			 return android.text.format.Time.SATURDAY;
		}else if (startDay == Calendar.MONDAY) {
            return android.text.format.Time.MONDAY;
        } else {
            return android.text.format.Time.SUNDAY;
        }
	}

	private int getMonthLastDay(int currentYear, int currentMonth) {
		switch (currentMonth) {
		case 0:
		case 2:
		case 4:
		case 6:
		case 7:
		case 9:
		case 11:
			return (31);
		
	    case 3:
	    case 5:
	    case 8:
	    case 10:
	    	return (30);
	    
	    default:
	    	if((currentYear%4 ==0) && (currentYear%100!=0) || (currentYear%400 ==0)){
	    		return (29);
	    	}
	    	else
	    	{
	    		return(28);
	    	}
			

		}
	}

	private void getWeekDay() {
		int dayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK);
		weekOfFirstDay = getFirstDay(dayOfWeek);
		Log.e(LOGTAG, " getWeekDay () is called ....");
		Log.e(LOGTAG, " weekOfFirstDay: " + weekOfFirstDay);
	}

	private int getFirstDay(int day) {
		int result = 0;
		if(day == Calendar.SUNDAY){
			result = 0;
		}
		else if(day == Calendar.MONDAY)
		{
			result = 1;
		}
		else if(day == Calendar.TUESDAY)
		{
			result = 2;
		}
		else if(day == Calendar.WEDNESDAY)
		{
			result = 3;
		}
		else if(day == Calendar.THURSDAY)
		{
			result = 4;
		}
		else if(day == Calendar.FRIDAY)
		{
			result = 5;
		}
		else if(day == Calendar.SATURDAY)
		{
			result = 6;
		}
		return result;
	}

	private void setTheFristDay() {
		// set the current month' day 1 
		 mCalendar.set(Calendar.DAY_OF_MONTH, 1);		
	}
	

	public int getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(int currentYear) {
		this.currentYear = currentYear;
	}

	public int getCurrentMonth() {
		return currentMonth;
	}

	public void setCurrentMonth(int currentMonth) {
		this.currentMonth = currentMonth;
	}
	

	public int getNumColumns() {
		return 7;
	}

	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 7 * 6;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return items[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		MonthItemView itemView;
		
		if(convertView == null)
		{
			itemView = new MonthItemView(mContext);
		}
		else
		{
			itemView = (MonthItemView)convertView;
		}
		
		// create a params
				GridView.LayoutParams params = new GridView.LayoutParams(
						GridView.LayoutParams.MATCH_PARENT,
						90);
				
				// calculate row and column
				int rowIndex = (position / countColumn);
				int columnIndex = position % countColumn;
				// set item data and properties
				itemView.setItem(items[position]);
				itemView.setLayoutParams(params);
				itemView.setPadding(2, 2, 2, 2);
				
				// set properties
				itemView.setGravity(Gravity.CENTER);
				if (columnIndex == 0) {
					itemView.setTextColor(Color.RED);
				} else {
					itemView.setTextColor(Color.BLACK);
				}
				// set background color
				if (position == getSelectedPosition()) {
					if(exportCurrentDay(position) != 0)
		        	itemView.setBackgroundColor(Color.YELLOW);
		        } else {
		        	itemView.setBackgroundColor(Color.WHITE);
		        }
				
				return itemView;
	}
	
	public int getSelectedPosition() {
		return selectedPosition;
	}
	
	public void setSelectedPosition(int selectedPosition) {
		this.selectedPosition = selectedPosition;
	}
	
	public void setPreviousMonth() {
		mCalendar.add(Calendar.MONTH, -1);
        recalculate();
        
        resetDayNumbers();
        selectedPosition = -1;
	}
	
	public void setNextMonth() {
		mCalendar.add(Calendar.MONTH, 1);
        recalculate();
        
        resetDayNumbers();
        selectedPosition = -1;
	}
	
	public int  exportCurrentDay(int position){
		return items[position].getDayValue();
	}
}
