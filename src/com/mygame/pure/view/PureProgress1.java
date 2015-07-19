package com.mygame.pure.view;

import com.mygame.pure.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PureProgress1 extends LinearLayout {
	private LinearLayout layout;
	private TextView progress_tv;
	private float mProgress;
	private Context mContext;
	public PureProgress1(Context context) {
		super(context);
		this.mContext=context;
		initView(context);
	}
	public PureProgress1(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	@Override
    public void draw(Canvas canvas) {
    	// TODO Auto-generated method stub
    	super.draw(canvas);
    	/*progress_tv.setText((mProgress*100+"").replace(".0", ""));
    	progress_tv.setPadding((int)(getWidth()*mProgress), 0, 0, 0);*/
    }
	@Override
	protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
		// TODO Auto-generated method stub
		java.text.DecimalFormat df = new java.text.DecimalFormat("#0.0");
		
    	progress_tv.setPadding((int)(getWidth()*mProgress), 0, 0, 0);
		return super.drawChild(canvas, child, drawingTime);
		
	}

	private void initView(Context context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layout = (LinearLayout) inflater.inflate(R.layout.progress_1, this);
		progress_tv = (TextView) layout.findViewById(R.id.progress_text);

	}

	public void setProgress(float progress,int levelIndex,String textValue) {
		this.mProgress = progress;
		if(levelIndex==0){
		    Drawable rightDrawable = getResources().getDrawable(R.drawable.arrow_dry);  
            rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());  
            progress_tv.setCompoundDrawables(null, null, null, rightDrawable);  
            progress_tv.setText(textValue);
			progress_tv.setTextColor(mContext.getResources().getColor(R.color.progress1));
		}else if(levelIndex==1){
			 Drawable rightDrawable = getResources().getDrawable(R.drawable.arrow_normal);  
	            rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());  
	            progress_tv.setCompoundDrawables(null, null, null, rightDrawable); 
	            progress_tv.setText(textValue);
			progress_tv.setTextColor(mContext.getResources().getColor(R.color.progress2));
		}else{
			 Drawable rightDrawable = getResources().getDrawable(R.drawable.arrow_wet);  
	         rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());  
	         progress_tv.setCompoundDrawables(null, null, null, rightDrawable);
	         progress_tv.setText(textValue);
			progress_tv.setTextColor(mContext.getResources().getColor(R.color.progress3));
		}
		invalidate();
	}

}
