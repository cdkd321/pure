package com.mygame.pure.activity;

import java.util.ArrayList;
import java.util.List;

import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.mygame.pure.R;
import com.mygame.pure.SelfDefineApplication;
import com.mygame.pure.adapter.HistoryAdapter;
import com.mygame.pure.adapter.VerticalPagerAdapter;
import com.mygame.pure.ble.BleService;
import com.mygame.pure.fragment.EyesFragmentDown;
import com.mygame.pure.fragment.EyesFragmentUp;
import com.mygame.pure.fragment.FaceFragmentDown;
import com.mygame.pure.fragment.FaceFragmentUp;
import com.mygame.pure.fragment.HandFragmentDown;
import com.mygame.pure.fragment.HandFragmentUp;
import com.mygame.pure.fragment.NeckFragmentUp;
import com.mygame.pure.fragment.NeckFragmentDown;
import com.mygame.pure.view.VerticalViewPager;

/**
 * 主界面 使用ViewPager + VerticalViewPager 作为程序主框架 主界面就可以上下左右滑动
 * 
 * @author tom
 */
public class ActMain extends BaseActivity implements OnClickListener {
	protected com.mygame.pure.ble.BleService mBleService;
	private List<View> baseList;
	private ViewPager viewPager;
	private View llTab1, llTab2, llTab3, llTab4;
	int[] viewPageId = new int[] { R.id.check_one, R.id.check_two,
			R.id.check_three, R.id.check_four };
	private ImageView ivImg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_main);
		initTab();
	}

	public List<View> getList() {

		int[] layoutId = new int[] { R.layout.check_one, R.layout.check_two,
				R.layout.check_three, R.layout.check_four };
		List<View> mList = new ArrayList<View>();
		for (int i = 0; i < 4; i++) {
			View view1 = View.inflate(this, layoutId[i], null);
			mList.add(view1);
		}
		return mList;
	}

	public List<Fragment> getFragmentList(int type) {
		List<Fragment> listFragments = new ArrayList<Fragment>();

		switch (type) {
		case 0:
			listFragments = getOneFragments();
			break;
		case 1:
			listFragments = getTwoFragments();
			break;
		case 2:
			listFragments = getThreeFragments();
			break;
		case 3:
			listFragments = getFourFragments();
			break;
		default:
			break;
		}

		return listFragments;
	}

	private List<Fragment> getOneFragments() {
		List<Fragment> listFragments = new ArrayList<Fragment>();
		HandFragmentUp fragment = new HandFragmentUp();
		listFragments.add(fragment);
		HandFragmentDown fragment2 = new HandFragmentDown();
		listFragments.add(fragment2);
		return listFragments;
	}

	public List<Fragment> getTwoFragments() {
		List<Fragment> listFragments = new ArrayList<Fragment>();
		FaceFragmentUp fragment = new FaceFragmentUp();
		listFragments.add(fragment);
		FaceFragmentDown fragment2 = new FaceFragmentDown();
		listFragments.add(fragment2);
		return listFragments;

	}

	public List<Fragment> getThreeFragments() {
		List<Fragment> listFragments = new ArrayList<Fragment>();
		EyesFragmentUp fragment = new EyesFragmentUp();
		listFragments.add(fragment);
		EyesFragmentDown fragment2 = new EyesFragmentDown();
		listFragments.add(fragment2);
		return listFragments;
	}

	private List<Fragment> getFourFragments() {
		List<Fragment> listFragments = new ArrayList<Fragment>();
		NeckFragmentUp fragment = new NeckFragmentUp();
		listFragments.add(fragment);
		NeckFragmentDown fragment2 = new NeckFragmentDown();
		listFragments.add(fragment2);
		return listFragments;
	}

	private void initTab() {
		llTab1 = findViewById(R.id.llTab1);
		llTab2 = findViewById(R.id.llTab2);
		llTab3 = findViewById(R.id.llTab3);
		llTab4 = findViewById(R.id.llTab4);
		getTkActionBar();
		setTitle("检测中心");
		ivImg = (ImageView) findViewById(R.id.ivImg);

		ivImg.setOnClickListener(this);

		llTab1.setOnClickListener(this);
		llTab2.setOnClickListener(this);
		llTab3.setOnClickListener(this);
		llTab4.setOnClickListener(this);

		viewPager = (ViewPager) findViewById(R.id.check_list);
		baseList = getList();

		HistoryAdapter adapter = new HistoryAdapter(baseList);

		for (int i = 0; i < 4; i++) {
			List<Fragment> fragments0 = getFragmentList(0);
			VerticalPagerAdapter fragmentAdapter = new VerticalPagerAdapter(
					getSupportFragmentManager(), fragments0);
			VerticalViewPager page = (VerticalViewPager) baseList.get(i)
					.findViewById(viewPageId[i]);
			page.setOnPageChangeListener(new OnPageChangeListener() {

				@Override
				public void onPageSelected(int arg0) {
					switch (arg0) {
					case 0:
						setTitle("检测中心");
						break;
					case 1:
						setTitle("历史记录");
						break;
					}

				}

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onPageScrollStateChanged(int arg0) {
					// TODO Auto-generated method stub

				}
			});
			page.setAdapter(fragmentAdapter);
		}
		viewPager.setAdapter(adapter);
		llTab1.setSelected(true);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				setTabSelected(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		Intent i = new Intent(this, BleService.class);
		bindService(i, mServiceConnection, BIND_AUTO_CREATE);
		BluetoothAdapter.getDefaultAdapter().enable();
	}

	private void setTabSelected(int i) {
		llTab1.setSelected(i == 0);
		llTab2.setSelected(i == 1);
		llTab3.setSelected(i == 2);
		llTab4.setSelected(i == 3);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.llTab1:
			viewPager.setCurrentItem(0);
			setTabSelected(0);
			break;
		case R.id.llTab2:
			viewPager.setCurrentItem(1);
			setTabSelected(1);
			break;
		case R.id.llTab3:
			viewPager.setCurrentItem(2);
			setTabSelected(2);
			break;
		case R.id.llTab4:
			viewPager.setCurrentItem(3);
			setTabSelected(3);
			//startActivity(new Intent(v.getContext(), MoreAct.class));
			break;
		case R.id.ivImg:
			VerticalViewPager vPager = (VerticalViewPager) viewPager
					.findViewById(viewPageId[viewPager.getCurrentItem()]);
			if (vPager.getChildCount() > 0) {
				if (vPager.getCurrentItem() > 0) {
					vPager.setCurrentItem(0);
					ivImg.setBackgroundResource(R.drawable.back);
				} else {
					vPager.setCurrentItem(1);
					ivImg.setBackgroundResource(R.drawable.arrow_up);
				}
			} else {
				vPager.setCurrentItem(0);
			}
			break;
		default:
			break;
		}
	}

	// Code to manage Service lifecycle.
	private final ServiceConnection mServiceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName componentName,
				IBinder service) {
			mBleService = ((com.mygame.pure.ble.BleService.LocalBinder) service)
					.getService();
			SelfDefineApplication.getInstance().mService = mBleService;
			if (!mBleService.initialize()) {
				// Log.e(TAG, "Unable to initialize Bluetooth");
				finish();
			}
			// Automatically connects to the device upon successful start-up
			// initialization.
			// mBleService.connect(mDeviceAddress);
		}

		@Override
		public void onServiceDisconnected(ComponentName componentName) {
			mBleService = null;
		}
	};
}
