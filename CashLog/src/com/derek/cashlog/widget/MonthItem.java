package com.derek.cashlog.widget;

import android.os.Parcel;
import android.os.Parcelable;

public class MonthItem implements Parcelable{
	private int dayValue;
	private int monthValue;
	private int yearValue;
	
	public int getMonthValue() {
		return monthValue;
	}

	public void setMonthValue(int monthValue) {
		this.monthValue = monthValue;
	}

	public int getYearValue() {
		return yearValue;
	}

	public void setYearValue(int yearValue) {
		this.yearValue = yearValue;
	}

	public MonthItem(){
		
	}
	
	public MonthItem(int day){
		dayValue = day;
	}
	
	public MonthItem(int day , int month , int year)
	{
		dayValue = day;
		monthValue = month;
		yearValue = year;
	}
	
	public MonthItem(Parcel in)
	{
		readFromParcel(in);
	}

	private void readFromParcel(Parcel in) {
		dayValue = in.readInt();
		monthValue = in.readInt();
		yearValue = in.readInt();
	}

	public int getDayValue() {
		return dayValue;
	}

	public void setDayValue(int dayValue) {
		this.dayValue = dayValue;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(dayValue);
		dest.writeInt(monthValue);
		dest.writeInt(yearValue);
	}
	
	  public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
	        public MonthItem createFromParcel(Parcel in) {
	             return new MonthItem(in);
	       }

	       public MonthItem[] newArray(int size) {
	            return new MonthItem[size];
	       }
	   };
	
}
