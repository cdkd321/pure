package com.mygame.pure.view;

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

import com.mygame.pure.R;


public class CircleProgressBar extends View {

 
	private Context context;
	private Paint paint;
	private Bitmap bgBmp;
	private Bitmap fgBmp;
	private int color;
	private RectF oval;
	private float STROKE_W = 20;
	private float point;
	
	public CircleProgressBar(Context context) {
		super(context, null);
		this.context = context;
		
		this.paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setStrokeWidth(20);
		paint.setStyle(Style.STROKE);
		color = Color.BLUE;
	}
	
	public CircleProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		this.paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setStrokeWidth(STROKE_W);
		paint.setStyle(Style.STROKE);
		paint.setAntiAlias(true);
		BitmapDrawable background = (BitmapDrawable) getResources().getDrawable(R.drawable.circle_bg);
		BitmapDrawable forceground = (BitmapDrawable) getResources().getDrawable(R.drawable.circle_light);
		
		bgBmp = background.getBitmap();
		fgBmp = forceground.getBitmap();
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

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		float point = 180.0f;
		
		float boundDelta = STROKE_W / 2;
		oval.set(boundDelta, boundDelta, getWidth() - boundDelta, getHeight() - boundDelta);
		
		paint.setColor(color);
		canvas.drawArc(oval, 0f, 360f, false, paint);
		
		paint.setColor(Color.WHITE);
		canvas.drawArc(oval, -90f, point, false, paint);
	}
	
	
	

}