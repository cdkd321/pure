package com.mygame.pure.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.mygame.pure.R;

public class FirstbootPageActivity extends Activity {
	private ViewPager mViewpager;
	private ImageView pont0;
	private ImageView pont1;
	private ImageView pont2;
	private Button confirm_btn;
	private final String mPageName = "FirstBootPagerActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.firstboot_page_activity);
		mViewpager = (ViewPager) findViewById(R.id.viewpager);
		// ��Ҫ��ҳ��ʾ��Viewװ��������
		LayoutInflater mLi = LayoutInflater.from(this);
		View view1 = mLi.inflate(R.layout.view1, null);
		View view2 = mLi.inflate(R.layout.view2, null);
		View view3 = mLi.inflate(R.layout.view3, null);
		pont0 = (ImageView) findViewById(R.id.pont0);
		pont1 = (ImageView) findViewById(R.id.pont1);
		pont2 = (ImageView) findViewById(R.id.pont2);
		confirm_btn = (Button) view3.findViewById(R.id.confirm_btn);
		// ÿ��ҳ���Title���
		final ArrayList<View> views = new ArrayList<View>();
		views.add(view1);
		views.add(view2);
		views.add(view3);

		// ���ViewPager�����������
		PagerAdapter mPagerAdapter = new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return views.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager) container).removeView(views.get(position));
			}

			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager) container).addView(views.get(position));
				return views.get(position);
			}
		};
		mViewpager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				selectPosition(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		mViewpager.setAdapter(mPagerAdapter);

		confirm_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FirstbootPageActivity.this,
						ActMain.class);
				startActivity(intent);
				finish();

			}
		});
	}

	public void selectPosition(int position) {
		switch (position) {
		case 0:
			pont0.setImageResource(R.drawable.blue_pont);
			pont1.setImageResource(R.drawable.feishe_pont);
			pont2.setImageResource(R.drawable.feishe_pont);
			break;
		case 1:
			pont0.setImageResource(R.drawable.feishe_pont);
			pont1.setImageResource(R.drawable.blue_pont);
			pont2.setImageResource(R.drawable.feishe_pont);
			break;
		case 2:
			pont0.setImageResource(R.drawable.feishe_pont);
			pont1.setImageResource(R.drawable.feishe_pont);
			pont2.setImageResource(R.drawable.blue_pont);
			break;

		default:
			break;
		}
	}

}
