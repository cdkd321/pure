package com.mygame.pure.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mygame.pure.R;

public class AboutActivity extends Activity {
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
		WebSettings settings = mWebView.getSettings();

		settings.setJavaScriptEnabled(true);
		settings.setJavaScriptCanOpenWindowsAutomatically(false);

		// Various plug-ins

		settings.setPluginState(WebSettings.PluginState.ON);

		// Viewing preferences
		settings.setBuiltInZoomControls(true);
		settings.setNeedInitialFocus(false);
		settings.setLoadWithOverviewMode(true);
		settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
		settings.setLoadsImagesAutomatically(true);

		// Cache
		settings.setAppCacheEnabled(true);
		settings.setDomStorageEnabled(true);
		settings.setAllowFileAccess(true);
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
