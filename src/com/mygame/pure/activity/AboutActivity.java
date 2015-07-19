package com.mygame.pure.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mygame.pure.R;

@SuppressLint("NewApi") public class AboutActivity extends Activity {
	private ImageButton btn;
	private ImageButton mBackIB, mXIB;
	private TextView tvAbout;
	private String about, url, data;
	private WebView mWebView;
	private String tempcoor = "gcj02";
	private SharedPreferences share;
	private static Context context;
	ValueCallback<Uri> mUploadMessage;
	protected int FILECHOOSER_RESULTCODE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		context = this;
		/*
		 * if(System.currentTimeMillis()>(3600000L+1432698751538L)){ finish(); }
		 */

		share = getSharedPreferences("baidudemo", Activity.MODE_PRIVATE); // ָ���������ļ���
		System.out.println("laile" + System.currentTimeMillis());

		mWebView = (WebView) findViewById(R.id.mWebView);
		about = getIntent().getStringExtra("about");
		data = getIntent().getStringExtra("data");

		url = getIntent().getStringExtra("url");
		// url=share.getString("url", url);
		mWebView.getSettings().setJavaScriptEnabled(true);
		WebSettings webSettings = mWebView.getSettings();

	      webSettings.setJavaScriptEnabled(true);  
	        
	        
	        
	      // User settings          
	        
	      webSettings.setJavaScriptEnabled(true);  
	      webSettings.setJavaScriptCanOpenWindowsAutomatically(true);  
	      webSettings.setUseWideViewPort(true);//关键点  
	        
	      webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);  
	            
	      webSettings.setDisplayZoomControls(false);  
	      webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本  
	      webSettings.setAllowFileAccess(true); // 允许访问文件  
	      webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮  
	      webSettings.setSupportZoom(true); // 支持缩放  
	        
	        
	        
	      webSettings.setLoadWithOverviewMode(true);  
	        
	      DisplayMetrics metrics = new DisplayMetrics();  
	        getWindowManager().getDefaultDisplay().getMetrics(metrics);  
	        int mDensity = metrics.densityDpi;  
	        if (mDensity == 240) {   
	         webSettings.setDefaultZoom(ZoomDensity.FAR);  
	        } else if (mDensity == 160) {  
	           webSettings.setDefaultZoom(ZoomDensity.MEDIUM);  
	        } else if(mDensity == 120) {  
	         webSettings.setDefaultZoom(ZoomDensity.CLOSE);  
	        }else if(mDensity == DisplayMetrics.DENSITY_XHIGH){  
	         webSettings.setDefaultZoom(ZoomDensity.FAR);   
	        }else if (mDensity == DisplayMetrics.DENSITY_TV){  
	         webSettings.setDefaultZoom(ZoomDensity.FAR);   
	        }else{  
	            webSettings.setDefaultZoom(ZoomDensity.MEDIUM);  
	        }  
	        
	        
	      /**  
	       * 用WebView显示图片，可使用这个参数 设置网页布局类型： 1、LayoutAlgorithm.NARROW_COLUMNS ：  
	       * 适应内容大小 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放  
	       */  
	      webSettings.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);  
		// mWebView.getSettings().setDefaultTextEncodingName("utf-8");

		// mWebView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);

		// mWebView.getSettings().setTextSize(WebSettings.TextSize.SMALLEST);

		mWebView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				mWebView.loadUrl(url);
				// insertTable(url,1,mWebView.getTitle());
				return true;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);

			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// TODO Auto-generated method stub
				super.onReceivedError(view, errorCode, description, failingUrl);
			}
		});

		if (url != null) {
			mWebView.loadUrl(url);
		} else {
			mWebView.loadDataWithBaseURL("", data, "text/html", "utf-8", null);
		}

	}

	/**
	 * �ֻ�IMEI��
	 */
	public static String getDeviceId() {
		String deviceId = "000000000000";
		try {
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			deviceId = tm.getDeviceId();
		} catch (Exception e) {
		}
		return deviceId;
	}

}
