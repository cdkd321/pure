package com.mygame.pure.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mygame.pure.R;

public class MyrogressBar extends LinearLayout {

	private LinearLayout layout;
	private TextView progress_tv;
	private float mProgress;

	public MyrogressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	@Override
    public void draw(Canvas canvas) {
    	// TODO Auto-generated method stub
    	super.draw(canvas);
    	progress_tv.setText((mProgress*100+"").replace(".0", ""));
    	progress_tv.setPadding((int)(getWidth()*mProgress), 0, 0, 0);
    }
	@Override
	protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
		// TODO Auto-generated method stub
		java.text.DecimalFormat df = new java.text.DecimalFormat("#0");
		progress_tv.setText((df.format(mProgress*100)+"").replace(".0", ""));
    	progress_tv.setPadding((int)(getWidth()*mProgress), 0, 0, 0);
		return super.drawChild(canvas, child, drawingTime);
		
	}

	private void initView(Context context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layout = (LinearLayout) inflater.inflate(R.layout.mybar_layout, this);
		progress_tv = (TextView) layout.findViewById(R.id.progress_tv);

	}

	public void setProgress(float progress) {
		this.mProgress = progress;
		invalidate();
	}

}
