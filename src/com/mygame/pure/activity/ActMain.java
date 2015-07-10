package com.mygame.pure.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.mygame.pure.R;
import com.mygame.pure.SelfDefineApplication;
import com.mygame.pure.ble.BleService;
import com.mygame.pure.fragment.EyesFragmentDown;
import com.mygame.pure.fragment.EyesFragmentUp;
import com.mygame.pure.fragment.FaceFragmentDown;
import com.mygame.pure.fragment.FaceFragmentUp;
import com.mygame.pure.fragment.HandFragmentDown;
import com.mygame.pure.fragment.HandFragmentUp;
import com.mygame.pure.fragment.HomeRootFragment;
import com.mygame.pure.fragment.NeckFragmentDown;
import com.mygame.pure.fragment.NeckFragmentUp;
import com.mygame.pure.utils.Constants;
import com.mygame.pure.view.VerticalViewPager;

/**
 * 涓荤晫闈� 浣跨敤ViewPager + VerticalViewPager 浣滀负绋嬪簭涓绘鏋� 涓荤晫闈㈠氨鍙互涓婁笅宸﹀彸婊戝姩
 * 
 * @author tom
 */
public class ActMain extends BaseActivity implements OnClickListener {
	protected com.mygame.pure.ble.BleService mBleService;
	private List<View> baseList;
	private ViewPager viewPager;
	public static View llTab1, llTab2, llTab3, llTab4;
	int[] viewPageId = new int[] { R.id.check_one, R.id.check_two,
			R.id.check_three, R.id.check_four };
	public static ImageView ivImg;
	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTrasaction;
	HomeRootFragment fragment;

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
		listFragments = getOneFragments(type);
		/*
		 * switch (type) { case 0: listFragments = getOneFragments(); break;
		 * case 1: listFragments = getTwoFragments(); break; case 2:
		 * listFragments = getThreeFragments(); break; case 3: listFragments =
		 * getFourFragments(); break; default: break; }
		 */

		return listFragments;
	}

	private List<Fragment> getOneFragments(int type) {
		List<Fragment> listFragments = new ArrayList<Fragment>();
		HandFragmentUp fragment = HandFragmentUp.newInstance(type);
		listFragments.add(fragment);
		HandFragmentDown fragment2 = HandFragmentDown.newInstance(type);
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
		addBackImage(R.drawable.more, new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ActMain.this, MoreAct.class);
				startActivity(intent);

			}
		});
		ivImg = (ImageView) findViewById(R.id.ivImg);

		/*
		 * llTab1.setOnClickListener(this); llTab2.setOnClickListener(this);
		 * llTab3.setOnClickListener(this); llTab4.setOnClickListener(this);
		 */
		fragmentManager = getSupportFragmentManager();
		fragmentTrasaction = fragmentManager.beginTransaction();
		fragment = HomeRootFragment.newInstance(0);
		fragmentTrasaction.replace(R.id.fragment_content, fragment);
		fragmentTrasaction.commit();

		/*
		 * viewPager = (ViewPager) findViewById(R.id.check_list); baseList =
		 * getList();
		 * 
		 * HistoryAdapter adapter = new HistoryAdapter(baseList);
		 * 
		 * for (int i = 0; i < 4; i++) { List<Fragment> fragments0 =
		 * getFragmentList(i); VerticalPagerAdapter fragmentAdapter = new
		 * VerticalPagerAdapter( getSupportFragmentManager(), fragments0);
		 * VerticalViewPager page = (VerticalViewPager) baseList.get(i)
		 * .findViewById(viewPageId[i]); page.setOnPageChangeListener(new
		 * OnPageChangeListener() {
		 * 
		 * @Override public void onPageSelected(int arg0) { switch (arg0) { case
		 * 0: setTitle("检测中心"); break; case 1: setTitle("历史数据"); break; }
		 * 
		 * }
		 * 
		 * @Override public void onPageScrolled(int arg0, float arg1, int arg2)
		 * { // TODO Auto-generated method stub
		 * 
		 * }
		 * 
		 * @Override public void onPageScrollStateChanged(int arg0) { // TODO
		 * Auto-generated method stub
		 * 
		 * } }); page.setAdapter(fragmentAdapter); }
		 * viewPager.setAdapter(adapter);
		 */
		llTab1.setSelected(true);
		/*
		 * viewPager.setOnPageChangeListener(new OnPageChangeListener() {
		 * 
		 * @Override public void onPageSelected(int arg0) {
		 * SelfDefineApplication.getInstance().selectPostion = arg0;
		 * setTabSelected(arg0); }
		 * 
		 * @Override public void onPageScrolled(int arg0, float arg1, int arg2)
		 * { }
		 * 
		 * @Override public void onPageScrollStateChanged(int arg0) { } });
		 */
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
			// viewPager.setCurrentItem(0);
			setTabSelected(0);
			Intent intent = new Intent(Constants.SELECT_ONE);
			sendBroadcast(intent);
			break;
		case R.id.llTab2:
			Intent intent1 = new Intent(Constants.SELECT_TWO);
			sendBroadcast(intent1);
			setTabSelected(1);
			break;
		case R.id.llTab3:
			Intent intent2 = new Intent(Constants.SELECT_THREE);
			sendBroadcast(intent2);
			setTabSelected(2);
			break;
		case R.id.llTab4:
			Intent intent3 = new Intent(Constants.SELECT_FOUR);
			sendBroadcast(intent3);
			setTabSelected(3);
			// startActivity(new Intent(v.getContext(), MoreAct.class));
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
	private Timer timer = new Timer();
	private static Boolean isQuit = false;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isQuit == false) {
				isQuit = true;
				Toast.makeText(getBaseContext(), "再按一次返回桌面", Toast.LENGTH_SHORT)
						.show();
				TimerTask task = null;
				task = new TimerTask() {
					@Override
					public void run() {
						isQuit = false;
					}
				};
				timer.schedule(task, 2000);
			} else {
				moveTaskToBack(false);

				return true;
			}
		}

		return false;
	}
}
