package com.mygame.pure.fragment;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.mygame.pure.R;
import com.mygame.pure.activity.ActMain;
import com.mygame.pure.activity.ActSpecify;
import com.mygame.pure.bean.BltModel;
import com.mygame.pure.ble.BleService;
import com.mygame.pure.utils.Constants;
import com.mygame.pure.utils.DateUtil;
import com.mygame.pure.utils.DbUtils;
import com.mygame.pure.utils.ToastHelper;
import com.mygame.pure.view.CircleProgressBar;

public class HandFragmentUp extends BaseFragment implements OnClickListener {
	private TextView tvBlueProgress;
	private CircleProgressBar pb;
	private TextView tvBlueTouth;
	private TextView tvAverage;
	private TextView tvYestodayLabel;
	private TextView toSeeMore;
	ActMain main;
	private int checkType;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		main = (ActMain) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.tab_fragment_hand, container,
				false);

		pb = (CircleProgressBar) rootView.findViewById(R.id.cpbUp);
		tvBlueProgress = (TextView) rootView.findViewById(R.id.tvBlueProgress);
		tvBlueTouth = (TextView) rootView.findViewById(R.id.tvBlueTouth);
		tvAverage = (TextView) rootView.findViewById(R.id.tvAverage);
		tvYestodayLabel = (TextView) rootView.findViewById(R.id.tvYestoday);
		toSeeMore = (TextView) rootView.findViewById(R.id.toSeeMore);
		main.addRightImage(R.drawable.btn_news_bg, new OnClickListener() {

			@Override
			public void onClick(View v) {
				main.startActivity(new Intent(getActivity(), ActSpecify.class));
			}
		});
		toSeeMore.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				main.startActivity(new Intent(getActivity(), ActSpecify.class));

			}
		});
		pb.setProgressing(0.0f, tvBlueProgress);
		java.text.DecimalFormat df = new java.text.DecimalFormat("#0.0");
		getData(df);
		return rootView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		checkType = getArguments().getInt("checkType");
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		registerBoradcastReceiver();

	}

	public static HandFragmentUp newInstance(int checkType) {
		HandFragmentUp f = new HandFragmentUp();
		Bundle args = new Bundle();
		args.putInt("checkType", checkType);
		f.setArguments(args);
		return f;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		getActivity().unregisterReceiver(mReceiver);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		/*
		 * if(SelfDefineApplication.getInstance().mService!=null){
		 * if(SelfDefineApplication
		 * .getInstance().mService.mConnectionState==BleService
		 * .STATE_CONNECTED){ tvBlueTouth.setText("已连接");
		 * tvBlueTouth.setVisibility(View.VISIBLE); try { Thread.sleep(1000);
		 * tvBlueTouth.setVisibility(View.GONE); } catch (InterruptedException
		 * e) { // TODO Auto-generated catch block e.printStackTrace(); } }
		 * if(SelfDefineApplication
		 * .getInstance().mService.mConnectionState==BleService
		 * .STATE_DISCONNECTED
		 * ||SelfDefineApplication.getInstance().mService.mConnectionState
		 * ==BleService.STATE_CONNECTING){ tvBlueTouth.setText("断开连接");
		 * tvBlueTouth.setVisibility(View.VISIBLE); try { Thread.sleep(1000);
		 * tvBlueTouth.setVisibility(View.GONE); } catch (InterruptedException
		 * e) { // TODO Auto-generated catch block e.printStackTrace(); } }
		 * 
		 * }
		 */
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.abarLeft: // 左边更多按钮
			ToastHelper.ToastSht("you click left", getActivity());
			break;
		case R.id.abarRight: // 右边检测按钮
			ToastHelper.ToastSht("you click right", getActivity());
			break;
		default:
			break;
		}
	}

	private void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction(Constants.UPDATE_OK);
		myIntentFilter.addAction(Constants.CANCEL_REFRESH);
		myIntentFilter.addAction(BleService.ACTION_GATT_CONNECTED);
		myIntentFilter.addAction(BleService.ACTION_GATT_DISCONNECTED);
		myIntentFilter.addAction(BleService.ACTION_STATUS_WRONG);
		myIntentFilter.addAction(BleService.ACTION_TIME_TOOSHORT);
		myIntentFilter.addAction(BleService.ACTION_START);
		myIntentFilter.addAction(Constants.SYNCHRONOUS_FAILURE);
		myIntentFilter.addAction(Constants.CLEAR_AlL);
		// 注册广播
		getActivity().registerReceiver(mReceiver, myIntentFilter);
	}

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (Constants.UPDATE_OK.equals(action)) {
				int waters = intent.getIntExtra("waters", 0);
				int selectPostion = intent.getIntExtra("selectPostion", 0);
				if (checkType == selectPostion) {
					java.text.DecimalFormat df = new java.text.DecimalFormat(
							"#0.0");
					pb.setProgressing(
							Float.parseFloat(df.format(waters / 45.0f + 20.0)) / 100f,
							tvBlueProgress);
					getData(df);
				}

			} else if (BleService.ACTION_GATT_CONNECTED.equals(action)) {
				tvBlueTouth.setText("已连接");
				tvBlueTouth.setVisibility(View.VISIBLE);
				/*
				 * try { Thread.sleep(1000);
				 * tvBlueTouth.setVisibility(View.GONE); } catch
				 * (InterruptedException e) { // TODO Auto-generated catch block
				 * e.printStackTrace(); }
				 */

			} else if (BleService.ACTION_GATT_DISCONNECTED.equals(action)) {
				tvBlueTouth.setText("断开连接");
				tvBlueTouth.setVisibility(View.VISIBLE);
				pb.setProgressing(0.0f, tvBlueProgress);
				/*
				 * try { Thread.sleep(1000);
				 * tvBlueTouth.setVisibility(View.GONE); } catch
				 * (InterruptedException e) { // TODO Auto-generated catch block
				 * e.printStackTrace(); }
				 */
			} else if (BleService.ACTION_STATUS_WRONG.equals(action)) {
				tvBlueTouth.setText("断开连接");
				tvBlueTouth.setVisibility(View.VISIBLE);
				pb.setProgressing(0.0f, tvBlueProgress);
				/*
				 * try { Thread.sleep(1000);
				 * tvBlueTouth.setVisibility(View.GONE); } catch
				 * (InterruptedException e) { // TODO Auto-generated catch block
				 * e.printStackTrace(); }
				 */
			} else if (BleService.ACTION_TIME_TOOSHORT.equals(action)) {
				Toast.makeText(getActivity(), "请连续按住5秒", 1000).show();
				pb.setProgressing(0.0f, tvBlueProgress);
			} else if (BleService.ACTION_START.equals(action)) {
				pb.setProgressing(0.3f, tvBlueProgress);
			}

		}

	};

	private void getData(java.text.DecimalFormat df) {
		DbUtils db = DbUtils.create(getActivity());
		List<BltModel> blts;
		float averageWater = 0;
		WhereBuilder builder = WhereBuilder.b("date", "==",
				DateUtil.getCurrentDate()).and("modelstate", "==", checkType);
		try {
			blts = db.findAll(Selector.from(BltModel.class).where(builder));
			if (blts != null) {
				int totalWater = 0;

				for (int i = 0; i < blts.size(); i++) {
					totalWater = totalWater
							+ Integer.parseInt(blts.get(i).getWater());

				}
				if (blts.size() > 0) {
					averageWater = totalWater / blts.size();
					// averageWater=Float.parseFloat(df.format(averageWater/45.0f+20.0))/100f;
					tvAverage.setText(Float.parseFloat(df
							.format(averageWater / 45.0f + 20.0)) + "%");
				}

			}
		} catch (DbException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		List<BltModel> yesTodayBlts;
		float averageYesTodayWater = 0;
		// 昨天的数据
		WhereBuilder builder1 = WhereBuilder.b("date", "==",
				DateUtil.dateAddDay(new Date(), -1)).and("modelstate", "==",
				checkType);
		try {
			yesTodayBlts = db.findAll(Selector.from(BltModel.class).where(
					builder1));
			if (yesTodayBlts != null) {
				int totalWater = 0;

				for (int i = 0; i < yesTodayBlts.size(); i++) {
					totalWater = totalWater
							+ Integer.parseInt(yesTodayBlts.get(i).getWater());

				}
				if (yesTodayBlts.size() > 0) {
					averageYesTodayWater = totalWater / yesTodayBlts.size();
				}

				// mAdapter.notifymDataSetChanged(lists);
			} else {
				averageYesTodayWater = 0;
			}
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (averageWater - averageYesTodayWater >= 0) {
			tvYestodayLabel
					.setText("+"
							+ Float.parseFloat(df
									.format((averageWater - averageYesTodayWater) / 45.0f + 20.0))
							+ "%");
		} else {
			tvYestodayLabel
					.setText("-"
							+ Float.parseFloat(df
									.format((averageYesTodayWater - averageWater) / 45.0f + 20.0))
							+ "%");
		}
	}

}
