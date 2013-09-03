package com.derek.cashlog.widget;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class TitleGridViewAdapter extends BaseAdapter {
	private Context mContext;
	String [] mWeeksTitleFields = {"일","월","화","수","목","금","토"};
	
	public TitleGridViewAdapter(Context context){
		mContext = context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 7;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	 @Override
	 public View getView(int position, View oldView, ViewGroup parent) {
	  // TODO Auto-generated method stub
	  View dateView=null;
	  
	  if(oldView == null)
	  {
		  dateView = new TextView(mContext);
		GridView.LayoutParams params = new GridView.LayoutParams(
				GridView.LayoutParams.MATCH_PARENT,
				50);
		dateView.setLayoutParams(params);
	  }
	  else if (position < 7) {
		  dateView = new TextView(mContext);
		  if(position ==0){
			  ((TextView)dateView).setTextColor(Color.RED);
		  }
		  else if(position == 6)
		  {
			  ((TextView)dateView).setTextColor(Color.BLUE);
		  }
	   ((TextView)dateView).setText(mWeeksTitleFields[position]);
	   dateView.setPadding(2, 2, 2, 2);
	   ((TextView) dateView).setGravity(Gravity.CENTER);
//	   v.setBackgroundColor(Color.GRAY);
	  }
	  else {
		  dateView = oldView;
	  }
	  return dateView;
	 }
	}

