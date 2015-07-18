package com.mygame.pure.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
	private boolean isBind = false;
	private ContentResolver mContentResolver;
	private RelativeLayout LinearLayout1;
	public static View touming;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.act_main);
		initTab();
		mContentResolver = getContentResolver();
		setLockPatternEnabled(false);
		share = getSharedPreferences("longke", Activity.MODE_PRIVATE); // 鎸囧畾鎿嶄綔鐨勬枃浠跺悕

	}

	public void setLockPatternEnabled(boolean enabled) {
		setBoolean(android.provider.Settings.System.LOCK_PATTERN_ENABLED,
				enabled);
	}

	private void setBoolean(String systemSettingKey, boolean enabled) {
		android.provider.Settings.System.putInt(mContentResolver,
				systemSettingKey, enabled ? 1 : 0);
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

	private SharedPreferences share;
	private String mAddress;
	private boolean isStop;

	@Override
	public void onResume() {
		super.onResume();

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

		/*
		 * if(isBind){ if(mServiceConnection!=null){
		 * unbindService(mServiceConnection); } isBind=false; }
		 */

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

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
		LinearLayout1 = (RelativeLayout) findViewById(R.id.LinearLayout1);
		touming = findViewById(R.id.touming);
		touming.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

			}
		});
		LinearLayout1.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				return false;
			}
		});

		getTkActionBar();
		setTitle(R.string.TestCentre);
		addBackImage(R.drawable.more, new OnClickListener() {

			@Override
			public void onClick(View v) {
//				Intent intent = new Intent(ActMain.this, MoreAct.class);
				Intent intent = new Intent(ActMain.this, ActSpecify.class);
				startActivityForResult(intent, 0);

			}
		});
		addRightImage(R.drawable.news_pressed, new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ActMain.this, ZXInfoAct.class);
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
		isBind = bindService(i, mServiceConnection, BIND_AUTO_CREATE);
		BluetoothAdapter.getDefaultAdapter().enable();
	}

	private void setTabSelected(int i) {
		llTab1.setSelected(i == 0);
		llTab2.setSelected(i == 1);
		llTab3.setSelected(i == 2);
		llTab4.setSelected(i == 3);
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		if (arg0 == 0) {
			if (TextUtils.isEmpty(share.getString("LAST_CONNECT_MAC", ""))) {
				Intent intent = new Intent(getActivity(),
						DeviceListActivity.class);
				intent.putExtra("uid", "");
				startActivityForResult(intent, 1);
			}
			;
		} else {

			if (arg2 != null) {
				BluetoothDevice device = arg2.getExtras().getParcelable(
						BluetoothDevice.EXTRA_DEVICE);
				mAddress = device.getAddress();
				if (SelfDefineApplication.getInstance().mService != null) {
					if (arg1 == Activity.RESULT_OK) {
						SelfDefineApplication.getInstance().mService
								.connect(mAddress);
					}

				}
			}
		}
		/*
		 * if(TextUtils.isEmpty(share.getString("LAST_CONNECT_MAC", ""))){
		 * Intent intent=new Intent(getActivity(),DeviceListActivity.class);
		 * startActivity(intent); };
		 */
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
			if (TextUtils.isEmpty(share.getString("LAST_CONNECT_MAC", ""))) {
				Intent intent = new Intent(getActivity(),
						DeviceListActivity.class);
				intent.putExtra("uid", "");
				startActivityForResult(intent, 1);
			}
			;
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
				Toast.makeText(getBaseContext(), R.string.exittoast,
						Toast.LENGTH_SHORT).show();
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
