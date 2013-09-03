package com.derek.cashlog.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * 
 * this is class is to express the daily textView
 * @author derek
 *
 */
	

public class MonthItemView extends TextView {
	private MonthItem item;
	
	public static final String LOGTAG = "MonthItemView";
	
	public MonthItemView(Context context){
		super(context);
	}
	public MonthItemView(Context context, AttributeSet attrs){
		super(context , attrs);
		init();
	}
	private void init() {
		setBackgroundColor(Color.WHITE);
	}
	
	public MonthItem getItem(){
		return item;
	}
	public void setItem(MonthItem item)
	{
		int day = item.getDayValue();
		if(day != 0)
		{
			setText(String.valueOf(day));
		}
		else
		{
			setText("");
		}
	}
	
	public int getDay(int position){
		return item.getDayValue();
	}
	
}
