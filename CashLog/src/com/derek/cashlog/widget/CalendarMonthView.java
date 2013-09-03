package com.derek.cashlog.widget;



import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CalendarMonthView extends GridView {
	
	//static public MonthItem [] items;
	// listener
	public interface OndataSelectionListener{
		public void onDataSelected(AdapterView parent , View view , int position , long id);
	}
	
	//adapter
	CalendarMonthAdapter adapter;
	
	//listener instance
	private OndataSelectionListener selectionListener;

	public CalendarMonthView(Context context) {
		super(context);
		init();
	}
	public CalendarMonthView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	private void init() {
		setProperty();
		setColumns();
		setListener();
	}
	private void setListener() {
		  setOnItemClickListener(new OnItemClickAdapter());
	}
	private void setColumns() {
		setNumColumns(7);
	}
	private void setProperty() {
		//  this method is the ability to regulate  the grid view Ui property
		setBackgroundColor(Color.LTGRAY);
		setVerticalSpacing(2);
		setHorizontalSpacing(2);
		setStretchMode(GridView.STRETCH_COLUMN_WIDTH);		
	}
	
	public BaseAdapter getAdapter() {
		return (BaseAdapter)super.getAdapter();
	}
	
	public void setAdapter(BaseAdapter adapter) {
		this.adapter = (CalendarMonthAdapter) adapter;
		super.setAdapter(adapter);
	}
	
	public void setOnDataSelectionListener(OndataSelectionListener listener)
	{
		selectionListener = listener;
	}
	
	public OndataSelectionListener getOnDataSelectionListener(){
		return selectionListener;
	}
	
	class OnItemClickAdapter implements OnItemClickListener {
		
		public OnItemClickAdapter() {
			
		}

		public void onItemClick(AdapterView parent, View v, int position, long id) {
			 
			if (adapter != null) {
				adapter.setSelectedPosition(position);
				adapter.notifyDataSetInvalidated();
				if(adapter.exportCurrentDay(position) != 0)
				{
					Toast t = Toast.makeText(getContext(), ""+adapter.exportCurrentDay(position), Toast.LENGTH_SHORT);
					t.show();
				}
				
			}

			if (selectionListener != null) {
				selectionListener.onDataSelected(parent, v, position, id);
			}
			
			
		}
		
	}

}
