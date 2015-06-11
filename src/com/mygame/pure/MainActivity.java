package com.mygame.pure;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;

public class MainActivity extends BaseActivity {

	
	// 定义数组来存放Fragment界面
	private Class<?> fragmentArray[] = { HandFragment.class, FaceFragment.class, EyesFragment.class,
			NeckFragment.class };

	// 定义数组来存放按钮图片
	private int mImageViewArray[] = {
			R.drawable.tab_hand_bg, R.drawable.tab_face_bg,
			R.drawable.tab_eye_bg, R.drawable.tab_neck_bg
	};

	// Tab选项卡的文字
	private String mTextviewArray[] = { "手", "脸", "眼", "颈" };

	private LayoutInflater layoutInflater;

	private FragmentTabHost mTabHost;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_main);
		
		initTab();
	}

	private void initTab() {
		// 实例化布局对象
		layoutInflater = LayoutInflater.from(this);
	
		// 实例化TabHost对象，得到TabHost
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.fragment_content);
	
		// 得到fragment的个数
		int count = fragmentArray.length;
	
		for (int i = 0; i < count; i++) {
			// 为每一个Tab按钮设置图标、文字和内容
			TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i]).setIndicator(getTabItemView(i));
			
			// 将Tab按钮添加进Tab选项卡中
			mTabHost.addTab(tabSpec, fragmentArray[i], null);
		}
	
		mTabHost.setCurrentTab(0);
	}
	
	
	/**
	 * 给Tab按钮设置图标和文字
	 */
	private View getTabItemView(int index) {
		View view = layoutInflater.inflate(R.layout.tab_item_view, null);
		ImageView ivTab = (ImageView)view.findViewById(R.id.ivTab);
		Button btnTab = (Button) view.findViewById(R.id.btnTab);
		btnTab.setText(mTextviewArray[index]);
		ivTab.setImageResource(mImageViewArray[index]);
		return view;
	}
}
