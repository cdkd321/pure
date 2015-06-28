package com.mygame.pure.view;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.xclcharts.chart.CustomLineData;
import org.xclcharts.chart.PointD;
import org.xclcharts.chart.SplineChart;
import org.xclcharts.chart.SplineData;
import org.xclcharts.common.DensityUtil;
import org.xclcharts.common.IFormatterTextCallBack;
import org.xclcharts.event.click.PointPosition;
import org.xclcharts.renderer.XChart;
import org.xclcharts.renderer.XEnum;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class SplineChart03View  extends DemoView {
	

	private String TAG = "SplineChart03View";
	private SplineChart chart = new SplineChart();
	//分类轴标签集合
	private LinkedList<String> labels = new LinkedList<String>();
	private LinkedList<SplineData> chartData = new LinkedList<SplineData>();
	
	private Paint mPaintTooltips = new Paint(Paint.ANTI_ALIAS_FLAG);
	
	//setCategoryAxisCustomLines
	// splinechart支持横向和竖向定制线
	private List<CustomLineData> mXCustomLineDataset = new ArrayList<CustomLineData>();
	private List<CustomLineData> mYCustomLineDataset = new ArrayList<CustomLineData>();
	
	public SplineChart03View(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}
	
	public SplineChart03View(Context context, AttributeSet attrs){   
	    super(context, attrs);   
	    initView();
	 }
	 
	 public SplineChart03View(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			initView();
	 }
	 
	 public void initView()
	 {
			chartLabels();
			//chartCustomeLines();
			//chartDataSet();	
			chartRender();
			invalidate();
	 }
	 
	 
	public LinkedList<SplineData> getChartData() {
		return chartData;
	}

	public void setChartData(LinkedList<SplineData> chartData) {
		this.chartData = chartData;
	}

	@Override  
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {  
	    super.onSizeChanged(w, h, oldw, oldh);  
	   //图所占范围大小
	    chart.setChartRange(w,h);
	}  				
	
	
	private void chartRender()
	{
		try {
						
			//设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....
			int [] ltrb = new int[4];
			ltrb[0] = DensityUtil.dip2px(getContext(), 40); //left	
			ltrb[1] = DensityUtil.dip2px(getContext(), 20); //top	
			ltrb[2] = DensityUtil.dip2px(getContext(), 20); //right	
			ltrb[3] = DensityUtil.dip2px(getContext(), 15); //bottom	
			//int [] ltrb = getBarLnDefaultSpadding();
			chart.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);	
			//chart.disableScale();
			//显示边框
			//chart.showRoundBorder();
			
			//数据源	
			chart.setCategories(labels);
			chart.setDataSource(chartData);
			chart.disableScale();
			chart.disablePanMode();
			
						
			//坐标系
			//数据轴最大值
			chart.getDataAxis().setAxisMax(60);
			chart.getDataAxis().setAxisMin(20);
			//数据轴刻度间隔
			chart.getDataAxis().setAxisSteps(10);
			//chart.setCustomLines(mYCustomLineDataset); //y轴
						
			
			//标签轴最大值
			chart.setCategoryAxisMax(100);	
			//标签轴最小值
			chart.setCategoryAxisMin(0);	
			//chart.setCustomLines(mXCustomLineDataset); //y轴
			//chart.setCategoryAxisCustomLines(mXCustomLineDataset); //x轴
			
			//设置图的背景色
			/*chart.setApplyBackgroundColor(true);
			chart.setBackgroundColor( Color.rgb(212, 194, 129) );
			chart.getBorder().setBorderLineColor(Color.rgb(179, 147, 197));*/
					
			//调轴线与网络线风格
			chart.getCategoryAxis().hideTickMarks();
			//chart.getDataAxis().hideAxisLine();
			chart.getCategoryAxis().getAxisPaint().setColor(Color.rgb(179, 147, 197));
			chart.getDataAxis().getAxisPaint().setColor(Color.rgb(179, 147, 197));
			chart.getCategoryAxis().getTickLabelPaint().setColor(Color.rgb(179, 147, 197));
			chart.getDataAxis().getTickLabelPaint().setColor(Color.rgb(179, 147, 197));
			chart.getDataAxis().getTickLabelPaint().setTextSize(40);
			chart.getDataAxis().hideTickMarks();		
			chart.getPlotGrid().showHorizontalLines();
			//chart.hideTopAxis();
			//chart.hideRightAxis();				
			
			chart.getPlotGrid().getHorizontalLinePaint().setColor(Color.rgb(179, 147, 197));
			chart.getCategoryAxis().getAxisPaint().setColor( 
						chart.getPlotGrid().getHorizontalLinePaint().getColor());
			chart.getCategoryAxis().getAxisPaint().setStrokeWidth(
					chart.getPlotGrid().getHorizontalLinePaint().getStrokeWidth());
			
				
			//定义交叉点标签显示格式,特别备注,因曲线图的特殊性，所以返回格式为:  x值,y值
			//请自行分析定制
			chart.setDotLabelFormatter(new IFormatterTextCallBack(){
	
				@Override
				public String textFormatter(String value) {
					// TODO Auto-generated method stub						
					String label = "["+value+"]";				
					return (label);
				}
				
			});
			chart.getDataAxis().setLabelFormatter(new IFormatterTextCallBack(){
				
				@Override
				public String textFormatter(String value) {
					// TODO Auto-generated method stub							
					Double tmp = Double.parseDouble(value);
					DecimalFormat df=new DecimalFormat("#0");
					String label = df.format(tmp).toString();				
					return (label +"%");
				}
				
			});
			//标题
			/*chart.setTitle("Spline Chart");
			chart.addSubtitle("(XCL-Charts Demo)");*/
			
			/*//激活点击监听
			chart.ActiveListenItemClick();
			//为了让触发更灵敏，可以扩大5px的点击监听范围
			chart.extPointClickRange(5);
			chart.showClikedFocus();*/
			
			//显示平滑曲线
			chart.setCrurveLineStyle(XEnum.CrurveLineStyle.BEZIERCURVE);
			
			//图例显示在正下方
			chart.getPlotLegend().hideBorder();
			chart.getPlotLegend().hideBox();
			chart.getAxisTitle().setLowerAxisTitleOffsetY(0);
			//标题
			
			//chart.addSubtitle("(XCL-Charts Demo)");	
			//轴标题
			//chart.getAxisTitle().setLowerTitle("(年份)");
			
			//透明度
			chart.getPlotLegend().hide();
			chart.getPlotLegend().setVerticalAlign(XEnum.VerticalAlign.BOTTOM);
			chart.getPlotLegend().setHorizontalAlign(XEnum.HorizontalAlign.CENTER);
												
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, e.toString());
		}
	}
	private void chartDataSet()
	{
		//线1的数据集
		List<PointD> linePoint1 = new ArrayList<PointD>();
		linePoint1.add(new PointD(5d, 28d));
		
		linePoint1.add(new PointD(12d, 22d));
		linePoint1.add(new PointD(25d, 25d));
		linePoint1.add(new PointD(30d, 30d));
		linePoint1.add(new PointD(45d, 25d));
		
		linePoint1.add(new PointD(55d, 33d));
		linePoint1.add(new PointD(62d, 45d));
		
		
		linePoint1.add(new PointD(75d, 43d));
		linePoint1.add(new PointD(82d, 55d));
		linePoint1.add(new PointD(90d, 60d));
		linePoint1.add(new PointD(96d, 58d));
		
		SplineData dataSeries1 = new SplineData("线一",linePoint1,
				Color.rgb(179, 147, 197) );	
		//把线弄细点
		dataSeries1.getLinePaint().setStrokeWidth(2);
		//dataSeries1.setLabelVisible(true);	
		
			
			
		//设定数据源		
		chartData.add(dataSeries1);				
		/*chartData.add(dataSeries2);	
		chartData.add(dataSeries3);	*/
	}
	
	private void chartLabels()
	{
		labels.add("");
		labels.add("");
		labels.add("");
		labels.add("");
		labels.add("");
		labels.add("");
		labels.add("");
	}
	
	/**
	 * 期望线/分界线
	 */
	private void chartCustomeLines()
	{				
		CustomLineData cdx1 =new CustomLineData("稍好",30d,Color.rgb(35, 172, 57),5);
		CustomLineData cdx2 =new CustomLineData("舒适",40d,Color.rgb(69, 181, 248),5);		
		cdx1.setLabelVerticalAlign(XEnum.VerticalAlign.MIDDLE);		
		mXCustomLineDataset.add(cdx1);
		mXCustomLineDataset.add(cdx2);	
		
				
		CustomLineData cdy1 = new CustomLineData("定制线",45d,Color.rgb(69, 181, 248),5);
		cdy1.setLabelHorizontalPostion(Align.CENTER);		
		mYCustomLineDataset.add(cdy1);	
	}
	
	
	@Override
	public void render(Canvas canvas) {
	    try{
	        chart.render(canvas);
	    } catch (Exception e){
	    	Log.e(TAG, e.toString());
	    }
	}
	
	@Override
	public List<XChart> bindChart() {
		// TODO Auto-generated method stub		
		List<XChart> lst = new ArrayList<XChart>();
		lst.add(chart);		
		return lst;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub		
		
		super.onTouchEvent(event);
				
		if(event.getAction() == MotionEvent.ACTION_UP) 
		{			
			//triggerClick(event.getX(),event.getY());	
		}
		return true;
	}
	
	
	//触发监听
	private void triggerClick(float x,float y)
	{
		if(!chart.getListenItemClickStatus()) return;
		
		PointPosition record = chart.getPositionRecord(x,y);			
		if( null == record) return;
	
		if(record.getDataID() >= chartData.size()) return;
		SplineData lData = chartData.get(record.getDataID());
		List<PointD> linePoint =  lData.getLineDataSet();	
		int pos = record.getDataChildID();
		int i = 0;
		Iterator it = linePoint.iterator();
		while(it.hasNext())
		{
			PointD  entry=(PointD)it.next();	
			
			if(pos == i)
			{							 						
				Double xValue = entry.x;
				Double yValue = entry.y;	
			    	     			     
			     	float r = record.getRadius();
					chart.showFocusPointF(record.getPosition(),r + r*0.8f);		
					chart.getFocusPaint().setStyle(Style.FILL);
					chart.getFocusPaint().setStrokeWidth(3);		
					if(record.getDataID() >= 2)
					{
						chart.getFocusPaint().setColor(Color.BLUE);
					}else{
						chart.getFocusPaint().setColor(Color.RED);
					}
			     //在点击处显示tooltip
					mPaintTooltips.setColor(Color.RED);				
					chart.getToolTip().setCurrentXY(x,y);
					chart.getToolTip().addToolTip(" Key:"+lData.getLineKey(),mPaintTooltips);		
					chart.getToolTip().addToolTip(
							" Current Value:" +Double.toString(xValue)+","+Double.toString(yValue),mPaintTooltips);
					chart.getToolTip().getBackgroundPaint().setAlpha(100);
					this.invalidate();
					
			     break;
			}
	        i++;
		}//end while
				
	}

}
