package com.mygame.pure.view;

import com.mygame.pure.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;


public class CircleProgressBarBlue extends View {

 
	private Context context;
	private Paint paint; 
	private int color;
	private RectF oval;
	private float STROKE_W = 10;
	private float point;
	
	public CircleProgressBarBlue(Context context) {
		super(context, null);
		this.context = context;
		
		this.paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setStrokeWidth(20);
		paint.setStyle(Style.STROKE);
		color = Color.BLUE;
	}
	
	public CircleProgressBarBlue(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		this.paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setStrokeWidth(STROKE_W/2);
		paint.setStyle(Style.STROKE);
		paint.setAntiAlias(true);  
		color = Color.parseColor("#4c000000");
		oval = new RectF();
	}
	
	/**
	 * 当前的角度
	 * @param point 以角度计算，范围0-360度
	 * 必须在主线程里调用
	 */
	
	public void setPoint(float point){
		this.point = point;
		invalidate();
	}
	/**
	 * 当前的角度
	 * @param point 以角度计算，范围0-360度
	 * 必须在主线程里调用
	 */
	
	public void setProgress(float progress){
		this.point = progress*360;
		
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		float boundDelta = STROKE_W / 2;
		oval.set(boundDelta, boundDelta, getWidth() - boundDelta, getHeight() - boundDelta);
		
		paint.setColor(color);
		canvas.drawArc(oval, 0f, 360f, false, paint); 
		
		boundDelta *= 4;
		oval.set(boundDelta, boundDelta, getWidth() - boundDelta, getHeight() - boundDelta);
		paint.setColor(getResources().getColor(R.color.theme_blue));
		canvas.drawArc(oval, -90f, point, false, paint);
	}
	
	
	

}
