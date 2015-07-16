package com.mygame.pure.fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.xclcharts.chart.PointD;
import org.xclcharts.chart.SplineData;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.mygame.pure.R;
import com.mygame.pure.SelfDefineApplication;
import com.mygame.pure.activity.ActMain;
import com.mygame.pure.activity.ActSpecify;
import com.mygame.pure.activity.DeviceListActivity;
import com.mygame.pure.activity.ZXInfoAct;
import com.mygame.pure.adapter.HistoryAdapter;
import com.mygame.pure.bean.Average;
import com.mygame.pure.bean.BltModel;
import com.mygame.pure.ble.BleService;
import com.mygame.pure.utils.Constants;
import com.mygame.pure.utils.DateUtil;
import com.mygame.pure.utils.DbUtils;
import com.mygame.pure.utils.ToastHelper;
import com.mygame.pure.view.AbOuterScrollView;
import com.mygame.pure.view.CircleProgressBar;
import com.mygame.pure.view.CircleProgressBarBlue;
import com.mygame.pure.view.MyTextView;
import com.mygame.pure.view.MyrogressBar;
import com.mygame.pure.view.SplineChart03View;
import com.mygame.pure.view.VerticalViewPager;

public class HomeRootFragment extends Fragment implements OnClickListener {

	private Bundle savedInstanceState;
	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTrasaction;
	private VerticalViewPager viewPager;
	private int checkType;
	private ArrayList<View> viewPageList;
	private CircleProgressBar pb;
	private TextView tvBlueProgress;
	private MyTextView tvBlueTouth;
	private TextView tvAverage;
	private TextView tvYestodayLabel;
	private TextView pingjun_tishi;
	private MyTextView toSeeMore;
	private View cell_bottom;
	private View cell_top;
	private ActMain actActivity;
	private Handler mHandler;
	Float perProgres;

	public static HomeRootFragment newInstance(int checkType) {
		HomeRootFragment f = new HomeRootFragment();
		Bundle args = new Bundle();
		args.putInt("checkType", checkType);
		f.setArguments(args);
		return f;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.actActivity = (ActMain) activity;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		getActivity().unregisterReceiver(mReceiver);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.savedInstanceState = savedInstanceState;
		mHandler = new Handler();
		View view = inflater.inflate(R.layout.homerootfragment, container,
				false);
		viewPager = (VerticalViewPager) view.findViewById(R.id.vertical_page);
		registerBoradcastReceiver();
		actActivity.llTab1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				setTabSelected(0);
				tvBlueTouth.setText("");
				toSeeMore.setText("");
				SelfDefineApplication.getInstance().selectPostion = 0;
				checkType = 0;
				initDownView(cell_bottom);
				initUPView(cell_top);
			}
		});
		actActivity.llTab2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				setTabSelected(1);
				tvBlueTouth.setText("");
				toSeeMore.setText("");
				SelfDefineApplication.getInstance().selectPostion = 1;
				checkType = 1;
				initDownView(cell_bottom);
				initUPView(cell_top);
			}
		});
		actActivity.llTab3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				setTabSelected(2);
				tvBlueTouth.setText("");
				toSeeMore.setText("");
				SelfDefineApplication.getInstance().selectPostion = 2;
				checkType = 2;
				initDownView(cell_bottom);
				initUPView(cell_top);
			}
		});
		actActivity.llTab4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				setTabSelected(3);
				tvBlueTouth.setText("");
				toSeeMore.setText("");
				SelfDefineApplication.getInstance().selectPostion = 3;
				checkType = 3;
				initDownView(cell_bottom);
				initUPView(cell_top);
			}
		});
		viewPageList = new ArrayList<View>();
		cell_top = LayoutInflater.from(getActivity()).inflate(
				R.layout.tab_fragment_hand, null);
		initUPView(cell_top);
		viewPageList.add(cell_top);

		cell_bottom = LayoutInflater.from(getActivity()).inflate(
				R.layout.tab_fragment_hand_down, null);
		initDownView(cell_bottom);
		viewPageList.add(cell_bottom);
		actActivity.ivImg.setOnClickListener(this);
		viewPager.setAdapter(new PagerAdapter() {

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				super.destroyItem(container, position, object);

				container.removeView(viewPageList.get(position
						% viewPageList.size()));
			}

			@Override
			public int getCount() {
				return viewPageList.size();
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {

				container.addView(viewPageList.get(position
						% viewPageList.size()));
				return viewPageList.get(position);
			}

			@Override
			public boolean isViewFromObject(View view, Object object) {
				return view == object;
			}
		});
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				if (arg0 == 0) {
					actActivity.ivImg.setBackgroundResource(R.drawable.back);
					actActivity.getTkActionBar();
					actActivity.setTitle("检测中心");
					actActivity.ivImg.setVisibility(View.VISIBLE);
					
					actActivity.addRightImage(R.drawable.news_pressed, new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							Intent intent = new Intent(getActivity(), ZXInfoAct.class);
							startActivity(intent);
						}
					});
					
				} else {
					actActivity.ivImg
							.setBackgroundResource(R.drawable.arrow_up);
					actActivity.setTitle("历史记录");
					actActivity.ivImg.setVisibility(View.VISIBLE);
					View view=new View(actActivity);
					actActivity.getTkActionBar().setRightView(view, null);
				}

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

		return view;
	}

	private void setTabSelected(int i) {
		actActivity.llTab1.setSelected(i == 0);
		actActivity.llTab2.setSelected(i == 1);
		actActivity.llTab3.setSelected(i == 2);
		actActivity.llTab4.setSelected(i == 3);
	}

	/**
	 * 鍒濆鍖栦笂闈age
	 * 
	 * @param view
	 */
	public void initUPView(View view) {
		pb = (CircleProgressBar) view.findViewById(R.id.cpbUp);
		tvBlueProgress = (TextView) view.findViewById(R.id.tvBlueProgress);
		tvBlueTouth = (MyTextView) view.findViewById(R.id.tvBlueTouth);
		tvAverage = (TextView) view.findViewById(R.id.tvAverage);
		tvYestodayLabel = (TextView) view.findViewById(R.id.tvYestoday);
		toSeeMore = (MyTextView) view.findViewById(R.id.toSeeMore);

		pb.setProgressing(0.0f, tvBlueProgress, false);
		java.text.DecimalFormat df = new java.text.DecimalFormat("#0.0");
		getData(df);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		checkType = getArguments().getInt("checkType");
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	/**
	 * 鑾峰彇涓婇潰page鐨勬暟鎹�
	 * 
	 * @param df
	 */
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
				} else {
					tvAverage.setText("0.0" + "%");
				}

			}
		} catch (DbException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		List<BltModel> yesTodayBlts;
		float averageYesTodayWater = 0;
		// 鏄ㄥぉ鐨勬暟鎹�
		WhereBuilder builder1 = WhereBuilder.b("date", "==",
				DateUtil.getDateStr(DateUtil.getCurrentDate(), -1)).and("modelstate", "==",
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
					tvYestodayLabel
					.setText(Float.parseFloat(df
									.format((averageYesTodayWater) / 45.0f + 20.0))
							+ "%");
				}else{
					
				}

				// mAdapter.notifymDataSetChanged(lists);
			} else {
				averageYesTodayWater = 0;
			}
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*if (averageWater - averageYesTodayWater > 0) {
			tvYestodayLabel
					.setText("+"
							+ Float.parseFloat(df
									.format((averageWater - averageYesTodayWater) / 45.0f + 20.0))
							+ "%");
		} else if (averageWater - averageYesTodayWater == 0) {
			tvYestodayLabel.setText("0.0%");
		} else {
			tvYestodayLabel
					.setText("-"
							+ Float.parseFloat(df
									.format((averageYesTodayWater - averageWater) / 45.0f + 20.0))
							+ "%");
		}*/
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
		// 娉ㄥ唽骞挎挱
		getActivity().registerReceiver(mReceiver, myIntentFilter);
	}

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			final java.text.DecimalFormat df = new java.text.DecimalFormat(
					"#0.0");
			if (Constants.UPDATE_OK.equals(action)) {

				final int waters = intent.getIntExtra("waters", 0);
				int selectPostion = intent.getIntExtra("selectPostion", 0);
				perProgres = Float.parseFloat(df.format(waters / 45.0f + 20.0)) / 100f;
				System.out.println("perProgres" + perProgres);
				if (checkType == selectPostion) {
					System.out.println("checkType" + checkType);
					pb.setProgressing(perProgres, tvBlueProgress, false);
					getData(df);
					if (checkType == 0) {
						if (Float.parseFloat(df.format(waters / 45.0f + 20.0)) < 30) {

							mHandler.postDelayed(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									tvBlueTouth.setText("干燥");
									tvBlueTouth.setVisibility(View.VISIBLE);
								}
							}, 1500);

						} else if (Float.parseFloat(df
								.format(waters / 45.0f + 20.0)) > 38) {

							mHandler.postDelayed(new Runnable() {

								@Override
								public void run() {
									tvBlueTouth.setText("湿润");
									tvBlueTouth.setVisibility(View.VISIBLE);
								}
							}, 1500);

						} else {

							mHandler.postDelayed(new Runnable() {

								@Override
								public void run() {
									tvBlueTouth.setText("正常");
									tvBlueTouth.setVisibility(View.VISIBLE);
								}
							}, 1500);

						}
					} else if (checkType == 1) {
						if (Float.parseFloat(df.format(waters / 45.0f + 20.0)) < 32) {

							mHandler.postDelayed(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									tvBlueTouth.setText("干燥");
									tvBlueTouth.setVisibility(View.VISIBLE);
								}
							}, 1500);

						} else if (Float.parseFloat(df
								.format(waters / 45.0f + 20.0)) > 42) {

							mHandler.postDelayed(new Runnable() {

								@Override
								public void run() {
									tvBlueTouth.setText("湿润");
									tvBlueTouth.setVisibility(View.VISIBLE);
								}
							}, 1500);

						} else {

							mHandler.postDelayed(new Runnable() {

								@Override
								public void run() {
									tvBlueTouth.setText("正常");
									tvBlueTouth.setVisibility(View.VISIBLE);
								}
							}, 1500);

						}
					} else if (checkType == 2) {
						if (Float.parseFloat(df.format(waters / 45.0f + 20.0)) < 35) {

							mHandler.postDelayed(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									tvBlueTouth.setText("干燥");
									tvBlueTouth.setVisibility(View.VISIBLE);
								}
							}, 1500);

						} else if (Float.parseFloat(df
								.format(waters / 45.0f + 20.0)) > 45) {

							mHandler.postDelayed(new Runnable() {

								@Override
								public void run() {
									tvBlueTouth.setText("湿润");
									tvBlueTouth.setVisibility(View.VISIBLE);
								}
							}, 1500);

						} else {

							mHandler.postDelayed(new Runnable() {

								@Override
								public void run() {
									tvBlueTouth.setText("正常");
									tvBlueTouth.setVisibility(View.VISIBLE);
								}
							}, 1500);

						}
					} else if (checkType == 3) {
						if (Float.parseFloat(df.format(waters / 45.0f + 20.0)) < 35) {

							mHandler.postDelayed(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									tvBlueTouth.setText("干燥");
									tvBlueTouth.setVisibility(View.VISIBLE);
								}
							}, 1500);

						} else if (Float.parseFloat(df
								.format(waters / 45.0f + 20.0)) > 45) {

							mHandler.postDelayed(new Runnable() {

								@Override
								public void run() {
									tvBlueTouth.setText("湿润");
									tvBlueTouth.setVisibility(View.VISIBLE);
								}
							}, 1500);

						} else {

							mHandler.postDelayed(new Runnable() {

								@Override
								public void run() {
									tvBlueTouth.setText("正常");
									tvBlueTouth.setVisibility(View.VISIBLE);
								}
							}, 1500);

						}
					}
					mHandler.postDelayed(new Runnable() {

						@Override
						public void run() {
							toSeeMore.setText("查看详细结果  >>");
							toSeeMore.setVisibility(View.VISIBLE);
						}
					}, 1500);

					toSeeMore.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							Intent intent = new Intent(getActivity(),
									ActSpecify.class);
							intent.putExtra("progress", perProgres);
							intent.putExtra("progressText", tvBlueProgress
									.getText().toString());
							intent.putExtra("checkType", checkType);
							startActivity(intent);

						}
					});
					toSeeMore.setVisibility(View.VISIBLE);
				}

				int waters1 = intent.getIntExtra("waters", 0);

				DbUtils db = DbUtils.create(getActivity());
				List<BltModel> blts;
				float averageWater = 0;
				WhereBuilder builder = WhereBuilder.b("date", "==",
						DateUtil.getCurrentDate()).and("modelstate", "==",
						checkType);
				try {
					blts = db.findAll(Selector.from(BltModel.class).where(
							builder));
					if (blts != null) {
						int totalWater = 0;

						for (int i = 0; i < blts.size(); i++) {
							totalWater = totalWater
									+ Integer.parseInt(blts.get(i).getWater());

						}
						if (blts.size() > 0) {
							averageWater = totalWater / blts.size();
							// averageWater=Float.parseFloat(df.format(averageWater/45.0f+20.0))/100f;
							tvAverageLevelData
									.setText(Float.parseFloat(df
											.format(averageWater / 45.0f + 20.0))
											+ "%");
							detectionTimes.setText("检测次数 " + blts.size() + "次");
						}

					}
				} catch (DbException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				List<BltModel> yesTodayBlts;
				float averageYesTodayWater = 0;
				// 鏄ㄥぉ鐨勬暟鎹�
				WhereBuilder builder1 = WhereBuilder.b("date", "==",
						DateUtil.dateAddDay(new Date(), -1)).and("modelstate",
						"==", checkType);
				try {
					yesTodayBlts = db.findAll(Selector.from(BltModel.class)
							.where(builder1));
					if (yesTodayBlts != null) {
						int totalWater = 0;

						for (int i = 0; i < yesTodayBlts.size(); i++) {
							totalWater = totalWater
									+ Integer.parseInt(yesTodayBlts.get(i)
											.getWater());

						}
						if (yesTodayBlts.size() > 0) {
							averageYesTodayWater = totalWater
									/ yesTodayBlts.size();
							tvThanLastDayData
							.setText(Float.parseFloat(df
									.format(averageWater / 45.0f + 20.0))
									+ "%");
						}else{
							tvThanLastDayData
							.setText(0
									+ "%");
						}

						// mAdapter.notifymDataSetChanged(lists);
					} else {
						averageYesTodayWater = 0;
					}
				} catch (DbException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*if (averageWater - averageYesTodayWater >= 0) {
					tvThanLastDayData
							.setText("+"
									+ Float.parseFloat(df
											.format((averageWater - averageYesTodayWater) / 45.0f + 20.0))
									+ "%");
				} else {
					tvThanLastDayData
							.setText("-"
									+ Float.parseFloat(df
											.format((averageYesTodayWater - averageWater) / 45.0f + 20.0))
									+ "%");
				}*/
				String[] tishi=getResources().getStringArray(R.array.water_suggestion);
				String[] tishijiaohao=getResources().getStringArray(R.array.water_suggestion_jiaogao);
				String[] water_suggestion_feichanggao=getResources().getStringArray(R.array.water_suggestion_feichanggao);
				refreshTishi(tishi, tishijiaohao, water_suggestion_feichanggao);
				
				refreshChartView(tvDate.getText().toString());
				reFreshDegreeView();
				java.text.DecimalFormat dfc = new java.text.DecimalFormat(
						"#0.00");
				Float d = Float.parseFloat(tvAverageLevelData.getText()
						.toString().replace("%", "")) / 60f;
				if (checkType == 0) {
					if (Float.parseFloat(tvAverageLevelData.getText()
							.toString().replace("%", "")) < 30) {

						tvJzzkData.setText("干燥");

					} else if (Float.parseFloat(tvAverageLevelData.getText()
							.toString().replace("%", "")) > 38) {
						tvJzzkData.setText("湿润");
						

					} else {
						tvJzzkData.setText("正常");

					}
				} else if (checkType == 1) {
					if (Float.parseFloat(tvAverageLevelData.getText()
							.toString().replace("%", ""))< 32) {

						tvJzzkData.setText("干燥");

					} else if (Float.parseFloat(tvAverageLevelData.getText()
							.toString().replace("%", "")) > 42) {

						tvJzzkData.setText("湿润");

					} else {
						tvJzzkData.setText("正常");

					}
				} else if (checkType == 2) {
					if (Float.parseFloat(tvAverageLevelData.getText()
							.toString().replace("%", "")) < 35) {

						tvJzzkData.setText("干燥");

					} else if (Float.parseFloat(tvAverageLevelData.getText()
							.toString().replace("%", "")) > 45) {
						tvJzzkData.setText("湿润");

					} else {
						tvJzzkData.setText("正常");

					}
				} else if (checkType == 3) {
					if (Float.parseFloat(tvAverageLevelData.getText()
							.toString().replace("%", "")) < 35) {

						tvJzzkData.setText("干燥");

					} else if (Float.parseFloat(tvAverageLevelData.getText()
							.toString().replace("%", "")) > 45) {

						tvJzzkData.setText("湿润");

					} else {
						tvJzzkData.setText("正常");

					}
				}
				progressBar.setProgress(Float.parseFloat(dfc.format(d)));

			} else if (BleService.ACTION_GATT_CONNECTED.equals(action)) {
				tvBlueTouth.setMyText("已连接");
				tvBlueTouth.setVisibility(View.VISIBLE);

				/*
				 * try { Thread.sleep(1000);
				 * tvBlueTouth.setVisibility(View.GONE); } catch
				 * (InterruptedException e) { // TODO Auto-generated catch block
				 * e.printStackTrace(); }
				 */

			} else if (BleService.ACTION_GATT_DISCONNECTED.equals(action)) {
				tvBlueTouth.setMyText("断开连接");
				tvBlueTouth.setVisibility(View.VISIBLE);
				pb.setProgressing(0.0f, tvBlueProgress, false);
				toSeeMore.setVisibility(View.INVISIBLE);
				/*
				 * try { Thread.sleep(1000);
				 * tvBlueTouth.setVisibility(View.GONE); } catch
				 * (InterruptedException e) { // TODO Auto-generated catch block
				 * e.printStackTrace(); }
				 */
			} else if (BleService.ACTION_STATUS_WRONG.equals(action)) {
				tvBlueTouth.setMyText("断开连接");
				tvBlueTouth.setVisibility(View.VISIBLE);
				pb.setProgressing(0.0f, tvBlueProgress, false);
				toSeeMore.setVisibility(View.INVISIBLE);
				/*
				 * try { Thread.sleep(1000);
				 * tvBlueTouth.setVisibility(View.GONE); } catch
				 * (InterruptedException e) { // TODO Auto-generated catch block
				 * e.printStackTrace(); }
				 */
			} else if (BleService.ACTION_TIME_TOOSHORT.equals(action)) {
				Toast.makeText(getActivity(), "请连续按住5秒", 1000).show();
				pb.setProgressing(0.0f, tvBlueProgress, false);
			} else if (BleService.ACTION_START.equals(action)) {
				pb.setProgressing(0.5f, tvBlueProgress, true);
				tvBlueTouth.setText("");
				toSeeMore.setText("");
			}

		}

	};
	

	private void reFreshDegreeView() {
		CircleProgressBarBlue pb = (CircleProgressBarBlue) cell_bottom
				.findViewById(R.id.cpb);
		TextView tvDegreeText = (TextView) cell_bottom
				.findViewById(R.id.tvDegreeText);
		pb.setProgress(Float.parseFloat(tvAverageLevelData.getText().toString()
				.replace("%", "")) / 100f);
		tvDegreeText.setText(tvAverageLevelData.getText().toString());
	}

	private ImageView clickPageLeft;
	private ImageView clickPageRight;
	private RadioGroup rGroup;
	private TextView detectionTimes;
	private SplineChart03View chartView;
	private View date_record;
	private TextView tvDate;
	private TextView tvAverageLevelData;
	private TextView tvThanLastDay;
	private TextView tvThanLastDayData;
	private MyrogressBar progressBar;
	private int selectFlag;
	private TextView shunengdu_title;
	private TextView tvJzzkData;

	private void initDownView(final View view) {
		String[] tishi=getResources().getStringArray(R.array.water_suggestion);
		String[] tishijiaohao=getResources().getStringArray(R.array.water_suggestion_jiaogao);
		String[] water_suggestion_feichanggao=getResources().getStringArray(R.array.water_suggestion_feichanggao);
		ViewPager vpager = (ViewPager) view.findViewById(R.id.vPager);
		AbOuterScrollView scroll = (AbOuterScrollView) view
				.findViewById(R.id.onlysv);
		clickPageLeft = (ImageView) view.findViewById(R.id.clickPageLeft);
		clickPageRight = (ImageView) view.findViewById(R.id.clickPageRight);
		rGroup = (RadioGroup) view.findViewById(R.id.rGroup);
		detectionTimes = (TextView) view.findViewById(R.id.detection_times);
		shunengdu_title = (TextView) view.findViewById(R.id.shunengdu_title);
		chartView = (SplineChart03View) view.findViewById(R.id.spline_chart);
		pingjun_tishi=(TextView) view.findViewById(R.id.pingjun_tishi);
		clickPageLeft.setOnClickListener(this);
		clickPageRight.setOnClickListener(this);
		List<View> viewList = new ArrayList<View>();
		date_record = View.inflate(getActivity(), R.layout.date_record, null);
		tvDate = (TextView) date_record.findViewById(R.id.tvDate);
		tvJzzkData = (TextView) date_record.findViewById(R.id.tvJzzkData);
		tvAverageLevelData = (TextView) date_record
				.findViewById(R.id.tvAverageLevelData);
		tvThanLastDay = (TextView) date_record.findViewById(R.id.tvThanLastDay);
		tvThanLastDayData = (TextView) date_record
				.findViewById(R.id.tvThanLastDayData);
		tvDate.setText(DateUtil.getCurrentDate());
		progressBar = (MyrogressBar) view.findViewById(R.id.myProgress);

		biJiaoToday(DateUtil.getDateStr(DateUtil.getCurrentDate(), -1),
				DateUtil.getCurrentDate(), tvThanLastDayData);
		java.text.DecimalFormat df = new java.text.DecimalFormat("#0.00");
		Float d = Float.parseFloat(tvAverageLevelData.getText().toString()
				.replace("%", "")) / 60f;
		refreshTishi(tishi, tishijiaohao, water_suggestion_feichanggao);
		progressBar.setProgress(Float.parseFloat(df.format(d)));
		refreshChartView(tvDate.getText().toString());
		scroll.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				int eventAction = event.getAction();
				switch (eventAction) {
				case MotionEvent.ACTION_UP:
					actActivity.ivImg.setVisibility(View.VISIBLE);
					break;
				case MotionEvent.ACTION_DOWN:
					actActivity.ivImg.setVisibility(View.GONE);
					break;
				default:
					break;
				}
				return false;
			}
		});
		/*
		 * DbUtils db = DbUtils.create(getActivity()); List<BltModel> blts;
		 * float averageWater = 0; java.text.DecimalFormat df = new
		 * java.text.DecimalFormat("#0.0"); WhereBuilder builder =
		 * WhereBuilder.b("date", "==", DateUtil.getCurrentDate()); try { blts =
		 * db.findAll(Selector.from(BltModel.class).where(builder)); if (blts !=
		 * null) { int totalWater = 0; for (int i = 0; i < blts.size(); i++) {
		 * totalWater = totalWater + Integer.parseInt(blts.get(i).getWater());
		 * 
		 * } if (blts.size() > 0) { averageWater = totalWater / blts.size();
		 * tvAverageLevelData.setText(Float.parseFloat(df .format(averageWater /
		 * 45.0f + 20.0)) + "%"); }
		 * 
		 * } } catch (DbException e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); }
		 */

		viewList.add(date_record);
		rGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				switch (checkedId) {
				case R.id.rbLeft:
					selectFlag = 0;
					refreshChartView(DateUtil.getCurrentDate());
					tvDate.setText(DateUtil.getCurrentDate());
					tvThanLastDay.setText("前一天");
					biJiaoToday(
							DateUtil.getDateStr(DateUtil.getCurrentDate(), -1),
							DateUtil.getCurrentDate(), tvThanLastDayData);
					
					break;
				case R.id.rbRight:
					selectFlag = 2;
					Average averagem = refreshMonthChartView(
							df.format(getFirstDayOfMonth(new Date())),
							df.format(getLastDayOfMonth(new Date())));
					tvDate.setText(df.format(getFirstDayOfMonth(new Date()))
							+ "~" + df.format(getLastDayOfMonth(new Date())));
					tvAverageLevelData.setText(averagem.getAverage() + "%");
					tvThanLastDay.setText("前一月");
					detectionTimes.setText("检测次数 " + averagem.getCount() + "次");
					break;
				case R.id.rbMid:
					selectFlag = 1;
					Average average = refreshWeekChartView(DateUtil.getDateStr(
							df.format(getFirstDayOfWeek(new Date())), -1));
					tvDate.setText(DateUtil.getDateStr(
							df.format(getFirstDayOfWeek(new Date())), -1)
							+ "~" + df.format(getLastDayOfWeek(new Date())));
					tvAverageLevelData.setText(average.getAverage() + "%");
					tvThanLastDay.setText("前一周");
					detectionTimes.setText("检测次数 " + average.getCount() + "次");
					break;

				default:
					break;
				}
				java.text.DecimalFormat dfd = new java.text.DecimalFormat(
						"#0.00");
				Float d = Float.parseFloat(tvAverageLevelData.getText()
						.toString().replace("%", "")) / 60f;
				String[] tishi=getResources().getStringArray(R.array.water_suggestion);
				String[] tishijiaohao=getResources().getStringArray(R.array.water_suggestion_jiaogao);
				String[] water_suggestion_feichanggao=getResources().getStringArray(R.array.water_suggestion_feichanggao);
				refreshTishi(tishi, tishijiaohao, water_suggestion_feichanggao);
				if (checkType == 0) {
					if (Float.parseFloat(tvAverageLevelData.getText()
							.toString().replace("%", "")) < 30) {

						tvJzzkData.setText("干燥");

					} else if (Float.parseFloat(tvAverageLevelData.getText()
							.toString().replace("%", "")) > 38) {
						tvJzzkData.setText("湿润");
						

					} else {
						tvJzzkData.setText("正常");

					}
				} else if (checkType == 1) {
					if (Float.parseFloat(tvAverageLevelData.getText()
							.toString().replace("%", ""))< 32) {

						tvJzzkData.setText("干燥");

					} else if (Float.parseFloat(tvAverageLevelData.getText()
							.toString().replace("%", "")) > 42) {

						tvJzzkData.setText("湿润");

					} else {
						tvJzzkData.setText("正常");

					}
				} else if (checkType == 2) {
					if (Float.parseFloat(tvAverageLevelData.getText()
							.toString().replace("%", "")) < 35) {

						tvJzzkData.setText("干燥");

					} else if (Float.parseFloat(tvAverageLevelData.getText()
							.toString().replace("%", "")) > 45) {
						tvJzzkData.setText("湿润");

					} else {
						tvJzzkData.setText("正常");

					}
				} else if (checkType == 3) {
					if (Float.parseFloat(tvAverageLevelData.getText()
							.toString().replace("%", "")) < 35) {

						tvJzzkData.setText("干燥");

					} else if (Float.parseFloat(tvAverageLevelData.getText()
							.toString().replace("%", "")) > 45) {

						tvJzzkData.setText("湿润");

					} else {
						tvJzzkData.setText("正常");

					}
				}
				progressBar.setProgress(Float.parseFloat(dfd.format(d)));
				reFreshDegreeView(view);
			}
		});
		/*
		 * for (int i = 0; i < 1; i++) { View view = View.inflate(activity,
		 * R.layout.date_record, null); viewList.add(view); }
		 */
		HistoryAdapter adapter = new HistoryAdapter(viewList);
		reFreshDegreeView(view);
		vpager.setAdapter(adapter);
		// registerBoradcastReceiver();
	}

	public void refreshTishi(String[] tishi, String[] tishijiaohao,
			String[] water_suggestion_feichanggao) {
		if (checkType == 0) {
			shunengdu_title.setText("手部的肌肤水嫩度");
			
			if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>20&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=21.9){
				pingjun_tishi.setText(tishi[0]);
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=22&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=23.9){
				pingjun_tishi.setText(tishi[1]);
				
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=24&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=25.9){
				pingjun_tishi.setText(tishi[2]);
				
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=26&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=27.9){
				pingjun_tishi.setText(tishi[3]);
				
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=28&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=28.9){
				pingjun_tishi.setText(tishi[4]);
				
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=29&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=29.9){
				pingjun_tishi.setText(tishi[5]);
				
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=30&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=31.6){
				pingjun_tishi.setText(tishi[6]);
				
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=31.7&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=33.2){
				pingjun_tishi.setText(tishi[7]);
				
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=33.3&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=34.8){
				pingjun_tishi.setText(tishi[8]);
				
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=34.9&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=36.4){
				pingjun_tishi.setText(tishi[9]);
				
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=36.5&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=38){
				pingjun_tishi.setText(tishi[10]);
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=38&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=40){
				int position=(int)(Math.random()*tishijiaohao.length);
				pingjun_tishi.setText(tishijiaohao[position]);
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>40){
				int position=(int)(Math.random()*water_suggestion_feichanggao.length);
				pingjun_tishi.setText(water_suggestion_feichanggao[position]);
			};
		} else if (checkType == 1) {
			shunengdu_title.setText("脸部的肌肤水嫩度");
			if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>20&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=22.4){
				pingjun_tishi.setText(tishi[0]);
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=22.5&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=24.9){
				pingjun_tishi.setText(tishi[1]);
				
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=25&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=27.4){
				pingjun_tishi.setText(tishi[2]);
				
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=27.5&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=29.9){
				pingjun_tishi.setText(tishi[3]);
				
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=30&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=30.9){
				pingjun_tishi.setText(tishi[4]);
				
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=31&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=31.9){
				pingjun_tishi.setText(tishi[5]);
				
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=32&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=33.9){
				pingjun_tishi.setText(tishi[6]);
				
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=34&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=35.9){
				pingjun_tishi.setText(tishi[7]);
				
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=36&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=37.9){
				pingjun_tishi.setText(tishi[8]);
				
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=38&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=39.9){
				pingjun_tishi.setText(tishi[9]);
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=40&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=41.9){
				pingjun_tishi.setText(tishi[10]);
				
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=42&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=44){
				int position=(int)(Math.random()*tishijiaohao.length);
				pingjun_tishi.setText(tishijiaohao[position]);
				
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=44){
				int position=(int)(Math.random()*water_suggestion_feichanggao.length);
				pingjun_tishi.setText(water_suggestion_feichanggao[position]);
				
			};
		} else if (checkType == 2) {
			shunengdu_title.setText("眼部的肌肤水嫩度");
			if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>20&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=22.9){
				pingjun_tishi.setText(tishi[0]);
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=23&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=25.9){
				pingjun_tishi.setText(tishi[1]);
				
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=26&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=28.9){
				pingjun_tishi.setText(tishi[2]);
				
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=29&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=31.9){
				pingjun_tishi.setText(tishi[3]);
				
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=32&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=33.4){
				pingjun_tishi.setText(tishi[4]);
				
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=33.5&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=34.9){
				pingjun_tishi.setText(tishi[5]);
				
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=35&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=36.9){
				pingjun_tishi.setText(tishi[6]);
				
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=37&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=38.9){
				pingjun_tishi.setText(tishi[7]);
				
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=39&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=40.9){
				pingjun_tishi.setText(tishi[8]);
				
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=41&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=42.9){
				pingjun_tishi.setText(tishi[9]);
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=43&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=44.9){
				pingjun_tishi.setText(tishi[10]);
				
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=45&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=47){
				int position=(int)(Math.random()*tishijiaohao.length);
				pingjun_tishi.setText(tishijiaohao[position]);
				
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=47){
				int position=(int)(Math.random()*water_suggestion_feichanggao.length);
				pingjun_tishi.setText(water_suggestion_feichanggao[position]);
				
			};
		} else {
			shunengdu_title.setText("颈部的肌肤水嫩度");
			if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>20&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=22.9){
				pingjun_tishi.setText(tishi[0]);
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=23&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=25.9){
				pingjun_tishi.setText(tishi[1]);
				
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=26&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=28.9){
				pingjun_tishi.setText(tishi[2]);
				
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=29&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=31.9){
				pingjun_tishi.setText(tishi[3]);
				
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=32&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=33.4){
				pingjun_tishi.setText(tishi[4]);
				
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=33.5&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=34.9){
				pingjun_tishi.setText(tishi[5]);
				
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=35&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=36.9){
				pingjun_tishi.setText(tishi[6]);
				
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=37&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=38.9){
				pingjun_tishi.setText(tishi[7]);
				
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=39&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=40.9){
				pingjun_tishi.setText(tishi[8]);
				
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=41&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=42.9){
				pingjun_tishi.setText(tishi[9]);
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=43&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=44.9){
				pingjun_tishi.setText(tishi[10]);
				
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=45&&Float.parseFloat(tvAverageLevelData.getText().toString()
							.replace("%", ""))<=47){
				int position=(int)(Math.random()*tishijiaohao.length);
				pingjun_tishi.setText(tishijiaohao[position]);
				
			}else if(Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", ""))>=47){
				int position=(int)(Math.random()*water_suggestion_feichanggao.length);
				pingjun_tishi.setText(water_suggestion_feichanggao[position]);
				
			};
		}
	}

	@Override
	public void onClick(View v) {
		java.text.DecimalFormat dfDec = new java.text.DecimalFormat("#0.00");
		String[] tishi=getResources().getStringArray(R.array.water_suggestion);
		String[] tishijiaohao=getResources().getStringArray(R.array.water_suggestion_jiaogao);
		String[] water_suggestion_feichanggao=getResources().getStringArray(R.array.water_suggestion_feichanggao);
		switch (v.getId()) {
		case R.id.abarLeft: // 宸﹁竟鏇村鎸夐挳
			ToastHelper.ToastSht("you click left", getActivity());
			break;
		case R.id.abarRight: // 鍙宠竟妫�祴鎸夐挳
			ToastHelper.ToastSht("you click right", getActivity());
			break;
		case R.id.clickPageLeft:
			if (selectFlag == 0) {
				tvDate.setText(DateUtil.getDateStr(tvDate.getText().toString(),
						-1));
				tvThanLastDay.setText("今天");
				biJiaoLastDay(tvDate.getText().toString(),
						DateUtil.getCurrentDate(), tvThanLastDayData);
				refreshChartView(tvDate.getText().toString());

			} else if (selectFlag == 1) {
				String[] dates = tvDate.getText().toString().split("~");
				tvDate.setText(DateUtil.getDateStr(dates[0], -7) + "~"
						+ DateUtil.getDateStr(dates[1], -7));
				Average average = refreshWeekChartView(DateUtil.getDateStr(
						dates[0], -7));

				tvAverageLevelData.setText(average.getAverage() + "%");
				detectionTimes.setText("检测次数 " + average.getCount() + "次");

			} else if (selectFlag == 2) {
				String[] dates = tvDate.getText().toString().split("~");
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				try {
					tvDate.setText(df.format(getReduceMonth(df.parse(dates[0])))
							+ "~"
							+ df.format(getReduceMonth(df.parse(dates[1]))));

					Average averagem = refreshMonthChartView(
							df.format(getReduceMonth(df.parse(dates[0]))),
							df.format(getReduceMonth(df.parse(dates[1]))));
					tvAverageLevelData.setText(averagem.getAverage() + "%");
					detectionTimes.setText("检测次数 " + averagem.getCount() + "次");
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
             
			Float dl = Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", "")) / 60f;
			refreshTishi(tishi, tishijiaohao, water_suggestion_feichanggao);
			reFreshDegreeView(cell_bottom);
			progressBar.setProgress(Float.parseFloat(dfDec.format(dl)));
			break;
		case R.id.clickPageRight:
			if (selectFlag == 0) {
				if (!tvDate.getText().toString()
						.equals(DateUtil.getCurrentDate())) {
					tvDate.setText(DateUtil.getDateStr(tvDate.getText()
							.toString(), 1));
					if (tvDate.getText().toString()
							.equals(DateUtil.getCurrentDate())) {
						tvThanLastDay.setText("前一天");
						biJiaoToday(DateUtil.getDateStr(
								DateUtil.getCurrentDate(), -1),
								DateUtil.getCurrentDate(), tvThanLastDayData);
					} else {
						biJiaoLastDay(tvDate.getText().toString(),
								DateUtil.getCurrentDate(), tvThanLastDayData);
						tvThanLastDay.setText("今天");
					}

				} else {
					tvThanLastDay.setText("前一天");
					biJiaoToday(tvDate.getText().toString(),
							DateUtil.getCurrentDate(), tvThanLastDayData);
				}
				refreshChartView(tvDate.getText().toString());

			} else if (selectFlag == 1) {
				String[] dates = tvDate.getText().toString().split("~");
				tvDate.setText(DateUtil.getDateStr(dates[0], 7) + "~"
						+ DateUtil.getDateStr(dates[1], 7));
				Average average = refreshWeekChartView(DateUtil.getDateStr(
						dates[0], 7));

				tvAverageLevelData.setText(average.getAverage() + "%");
				detectionTimes.setText("检测次数 " + average.getCount() + "次");
			} else if (selectFlag == 2) {
				String[] dates = tvDate.getText().toString().split("~");
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				try {
					tvDate.setText(df.format(getAddMonth(df.parse(dates[0])))
							+ "~" + df.format(getAddMonth(df.parse(dates[1]))));
					Average averagem = refreshMonthChartView(
							df.format(getAddMonth(df.parse(dates[0]))),
							df.format(getAddMonth(df.parse(dates[1]))));
					tvAverageLevelData.setText(averagem.getAverage() + "%");
					detectionTimes.setText("检测次数 " + averagem.getCount() + "次");
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			reFreshDegreeView(cell_bottom);
			Float dr = Float.parseFloat(tvAverageLevelData.getText().toString()
					.replace("%", "")) / 60f;
			refreshTishi(tishi, tishijiaohao, water_suggestion_feichanggao);
			progressBar.setProgress(Float.parseFloat(dfDec.format(dr)));
			break;
		case R.id.ivImg:

			if (viewPager.getCurrentItem() > 0) {
				viewPager.setCurrentItem(0);
				actActivity.ivImg.setBackgroundResource(R.drawable.back);
				actActivity.ivImg.setVisibility(View.VISIBLE);
			} else {
				viewPager.setCurrentItem(1);
				actActivity.ivImg.setBackgroundResource(R.drawable.arrow_up);
				actActivity.ivImg.setVisibility(View.VISIBLE);
			}

			break;
		default:
			break;
		}
	}

	private void reFreshDegreeView(View view) {
		CircleProgressBarBlue pb = (CircleProgressBarBlue) view
				.findViewById(R.id.cpb);
		TextView tvDegreeText = (TextView) view.findViewById(R.id.tvDegreeText);
		pb.setProgress(Float.parseFloat(tvAverageLevelData.getText().toString()
				.replace("%", "")) / 100f);
		tvDegreeText.setText(tvAverageLevelData.getText().toString());
	}

	/**
	 * 鍒锋柊鍥捐〃
	 */
	private void refreshChartView(String date) {
		// 绾�鐨勬暟鎹泦
		List<PointD> linePoint1 = new ArrayList<PointD>();
		if (hourAvera(DateUtil.getCurrentDate(), "01") != 0) {
			linePoint1.add(new PointD(4d, hourAvera(DateUtil.getCurrentDate(),
					"01")));
		}
		if (hourAvera(date, "02") != 0) {

			linePoint1.add(new PointD(8d, hourAvera(DateUtil.getCurrentDate(),
					"02")));
		}
		if (hourAvera(date, "03") != 0) {
			linePoint1.add(new PointD(12d, hourAvera(DateUtil.getCurrentDate(),
					"03")));

		}
		if (hourAvera(date, "04") != 0) {
			linePoint1.add(new PointD(16d, hourAvera(DateUtil.getCurrentDate(),
					"04")));

		}
		if (hourAvera(date, "05") != 0) {

			linePoint1.add(new PointD(20d, hourAvera(DateUtil.getCurrentDate(),
					"05")));
		}
		if (hourAvera(date, "06") != 0) {

			linePoint1.add(new PointD(24d, hourAvera(DateUtil.getCurrentDate(),
					"06")));
		}
		if (hourAvera(date, "07") != 0) {
			linePoint1.add(new PointD(28d, hourAvera(DateUtil.getCurrentDate(),
					"07")));

		}
		if (hourAvera(date, "08") != 0) {

			linePoint1.add(new PointD(32d, hourAvera(DateUtil.getCurrentDate(),
					"08")));
		}
		if (hourAvera(date, "09") != 0) {

			linePoint1.add(new PointD(36d, hourAvera(DateUtil.getCurrentDate(),
					"09")));
		}
		if (hourAvera(date, "10") != 0) {

			linePoint1.add(new PointD(40d, hourAvera(DateUtil.getCurrentDate(),
					"10")));
		}
		if (hourAvera(date, "11") != 0) {
			linePoint1.add(new PointD(44d, hourAvera(DateUtil.getCurrentDate(),
					"11")));

		}
		if (hourAvera(date, "12") != 0) {

			linePoint1.add(new PointD(48d, hourAvera(DateUtil.getCurrentDate(),
					"12")));
		}
		if (hourAvera(date, "13") != 0) {

			linePoint1.add(new PointD(52d, hourAvera(DateUtil.getCurrentDate(),
					"13")));
		}
		if (hourAvera(date, "14") != 0) {

			linePoint1.add(new PointD(56d, hourAvera(DateUtil.getCurrentDate(),
					"14")));
		}
		if (hourAvera(date, "15") != 0) {

			linePoint1.add(new PointD(60d, hourAvera(DateUtil.getCurrentDate(),
					"15")));
		}
		if (hourAvera(date, "16") != 0) {

			linePoint1.add(new PointD(64d, hourAvera(DateUtil.getCurrentDate(),
					"16")));
		}
		if (hourAvera(date, "17") != 0) {
			linePoint1.add(new PointD(68d, hourAvera(DateUtil.getCurrentDate(),
					"17")));

		}
		if (hourAvera(date, "18") != 0) {

			linePoint1.add(new PointD(72d, hourAvera(DateUtil.getCurrentDate(),
					"18")));
		}
		if (hourAvera(date, "19") != 0) {
			linePoint1.add(new PointD(76d, hourAvera(DateUtil.getCurrentDate(),
					"19")));

		}
		if (hourAvera(date, "20") != 0) {

			linePoint1.add(new PointD(80d, hourAvera(DateUtil.getCurrentDate(),
					"20")));
		}
		if (hourAvera(date, "21") != 0) {

			linePoint1.add(new PointD(84d, hourAvera(DateUtil.getCurrentDate(),
					"21")));
		}
		if (hourAvera(date, "22") != 0) {

			linePoint1.add(new PointD(88d, hourAvera(DateUtil.getCurrentDate(),
					"22")));
		}
		if (hourAvera(date, "23") != 0) {

			linePoint1.add(new PointD(92d, hourAvera(DateUtil.getCurrentDate(),
					"23")));
		}
		if (hourAvera(date, "24") != 0) {

			linePoint1.add(new PointD(96d, hourAvera(DateUtil.getCurrentDate(),
					"24")));
		}
		SplineData dataSeries1 = new SplineData("线一", linePoint1, Color.rgb(
				179, 147, 197));
		// 鎶婄嚎寮勭粏鐐�
		dataSeries1.getLinePaint().setStrokeWidth(2);
		// dataSeries1.setLabelVisible(true);
		LinkedList<SplineData> chartData = new LinkedList<SplineData>();
		LinkedList<String> labels = new LinkedList<String>();
		// 璁惧畾鏁版嵁婧�
		chartData.add(dataSeries1);
		chartView.setChartLabels(labels);
		chartView.setChartData(chartData);
		chartView.initView();
	}

	/**
	 * 鍒锋柊鏈堝浘琛�
	 */

	private Average refreshMonthChartView(String startDate, String endDate) {
		Average averageEntity = new Average();
		float average = 0;
		int count = 0;
		double m=0;
		List<PointD> linePoint1 = new ArrayList<PointD>();
		String ends[] = endDate.split("-");
		int totalDay = Integer.parseInt(ends[2]);
		double pointd = (Double) 100d / totalDay;
		for (int i = 0; i < totalDay; i++) {
			average = average
					+ dayAvera(DateUtil.getDateStr(startDate, i)).getAverage();
			count = count
					+ dayAvera(DateUtil.getDateStr(startDate, i)).getCount();
			if (dayAvera(DateUtil.getDateStr(startDate, i)).getAverage() != 0) {
				linePoint1.add(new PointD(i * pointd, dayAvera(
						DateUtil.getDateStr(startDate, i)).getAverage()));
				m++;
			}

		}
		SplineData dataSeries1 = new SplineData("线一", linePoint1, Color.rgb(
				179, 147, 197));
		// 鎶婄嚎寮勭粏鐐�
		dataSeries1.getLinePaint().setStrokeWidth(2);
		// dataSeries1.setLabelVisible(true);
		LinkedList<SplineData> chartData = new LinkedList<SplineData>();
		LinkedList<String> chartLabels = new LinkedList<String>();
		chartLabels.add("1");
		chartLabels.add("15");
		chartLabels.add("30");
		// 璁惧畾鏁版嵁婧�
		chartData.add(dataSeries1);
		chartView.setChartData(chartData);
		chartView.setChartLabels(chartLabels);
		if(m!=0){
			java.text.DecimalFormat df = new java.text.DecimalFormat("#0.0");
			averageEntity.setAverage(Float.parseFloat(df.format(average/m)));
		}else{
			averageEntity.setAverage(0.0f);
		}
		//averageEntity.setAverage(average);
		averageEntity.setCount(count);
		chartView.initView();
		return averageEntity;
	}

	/**
	 * 刷新周图表
	 */
	private Average refreshWeekChartView(String date) {
		// 绾�鐨勬暟鎹泦
		Average averageEntity = new Average();
		double average = 0;
		int count = 0;
		double i=0;
		List<PointD> linePoint1 = new ArrayList<PointD>();
		if (dayAvera(date).getAverage() != 0) {
			average = average + dayAvera(date).getAverage();
			count = count + dayAvera(date).getCount();
			linePoint1.add(new PointD(14d, dayAvera(date).getAverage()));
			i++;
		}
		if (dayAvera(DateUtil.getDateStr(date, 1)).getAverage() != 0) {
			average = average
					+ dayAvera(DateUtil.getDateStr(date, 1)).getAverage();
			count = count + dayAvera(DateUtil.getDateStr(date, 1)).getCount();
			linePoint1.add(new PointD(28d, dayAvera(
					DateUtil.getDateStr(date, 1)).getAverage()));
			i++;
		}
		if (dayAvera(DateUtil.getDateStr(date, 2)).getAverage() != 0) {
			average = average
					+ dayAvera(DateUtil.getDateStr(date, 2)).getAverage();
			count = count + dayAvera(DateUtil.getDateStr(date, 2)).getCount();
			linePoint1.add(new PointD(42d, dayAvera(
					DateUtil.getDateStr(date, 2)).getAverage()));
			i++;

		}
		if (dayAvera(DateUtil.getDateStr(date, 3)).getAverage() != 0) {
			average = average
					+ dayAvera(DateUtil.getDateStr(date, 3)).getAverage();
			count = count + dayAvera(DateUtil.getDateStr(date, 3)).getCount();
			linePoint1.add(new PointD(56d, dayAvera(
					DateUtil.getDateStr(date, 3)).getAverage()));
			i++;

		}
		if (dayAvera(DateUtil.getDateStr(date, 4)).getAverage() != 0) {
			average = average
					+ dayAvera(DateUtil.getDateStr(date, 4)).getAverage();
			count = count + dayAvera(DateUtil.getDateStr(date, 4)).getCount();
			linePoint1.add(new PointD(70d, dayAvera(
					DateUtil.getDateStr(date, 4)).getAverage()));
			i++;

		}
		if (dayAvera(DateUtil.getDateStr(date, 5)).getAverage() != 0) {
			average = average
					+ dayAvera(DateUtil.getDateStr(date, 5)).getAverage();
			count = count + dayAvera(DateUtil.getDateStr(date, 5)).getCount();
			linePoint1.add(new PointD(84d, dayAvera(
					DateUtil.getDateStr(date, 5)).getAverage()));
			i++;

		}
		if (dayAvera(DateUtil.getDateStr(date, 6)).getAverage() != 0) {
			average = average
					+ dayAvera(DateUtil.getDateStr(date, 6)).getAverage();
			count = count + dayAvera(DateUtil.getDateStr(date, 6)).getCount();
			linePoint1.add(new PointD(98d, dayAvera(
					DateUtil.getDateStr(date, 6)).getAverage()));
			i++;

		}
		SplineData dataSeries1 = new SplineData("线一", linePoint1, Color.rgb(
				179, 147, 197));
		// 鎶婄嚎寮勭粏鐐�
		dataSeries1.getLinePaint().setStrokeWidth(2);
		// dataSeries1.setLabelVisible(true);
		LinkedList<SplineData> chartData = new LinkedList<SplineData>();
		LinkedList<String> chartLabels = new LinkedList<String>();
		chartLabels.add("日");
		chartLabels.add("一");
		chartLabels.add("二");
		chartLabels.add("三");
		chartLabels.add("四");
		chartLabels.add("五");
		chartLabels.add("六");
		// 璁惧畾鏁版嵁婧�
		chartData.add(dataSeries1);
		chartView.setChartData(chartData);
		chartView.setChartLabels(chartLabels);
		chartView.initView();
		if(i!=0){
			java.text.DecimalFormat df = new java.text.DecimalFormat("#0.0");
			averageEntity.setAverage(Float.parseFloat(df.format(average/i)));
		}else{
			averageEntity.setAverage(0.0f);
		}
		
		averageEntity.setCount(count);
		return averageEntity;
	}

	/**
	 * 今天的时间和前面的时间比较
	 * 
	 * @param lastDay
	 * @param today
	 * @param tvbiJiao
	 */
	private void biJiaoToday(String lastDay, String today, TextView tvbiJiao) {
		java.text.DecimalFormat df = new java.text.DecimalFormat("#0.0");
		DbUtils db = DbUtils.create(getActivity());
		List<BltModel> blts;
		float averageWater = 0;
		WhereBuilder builder = WhereBuilder.b("date", "==", today).and(
				"modelstate", "==", checkType);
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
					tvAverageLevelData.setText(Float.parseFloat(df
							.format(averageWater / 45.0f + 20.0)) + "%");
					detectionTimes.setText("检测次数 " + blts.size() + "次");

				} else {
					tvAverageLevelData.setText("0.0%");
				}

			} else {

				tvAverageLevelData.setText("0.0%");
			}
		} catch (DbException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		List<BltModel> yesTodayBlts;
		float averageYesTodayWater = 0;
		// 鏄ㄥぉ鐨勬暟鎹�
		WhereBuilder builder1 = WhereBuilder.b("date", "==", lastDay).and(
				"modelstate", "==", checkType);
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
					tvbiJiao.setText(
							+ Float.parseFloat(df
									.format((averageYesTodayWater) / 45.0f + 20.0))
							+ "%");
				}else{
					tvbiJiao.setText("+0%");
				}

				// mAdapter.notifymDataSetChanged(lists);
			} else {
				averageYesTodayWater = 0;
			}
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*if (averageWater - averageYesTodayWater > 0) {
			tvbiJiao.setText("+"
					+ Float.parseFloat(df
							.format((averageWater - averageYesTodayWater) / 45.0f + 20.0))
					+ "%");
		} else if (averageYesTodayWater - averageWater == 0) {
			tvbiJiao.setText("+0%");
		} else {
			tvbiJiao.setText("-"
					+ Float.parseFloat(df
							.format((averageYesTodayWater - averageWater) / 45.0f + 20.0))
					+ "%");
		}*/
	}

	/**
	 * 前面的时间和后面的时间比较
	 * 
	 * @param lastDay
	 * @param today
	 * @param tvbiJiao
	 */
	private void biJiaoLastDay(String lastDay, String today, TextView tvbiJiao) {
		java.text.DecimalFormat df = new java.text.DecimalFormat("#0.0");
		DbUtils db = DbUtils.create(getActivity());
		List<BltModel> blts;
		float averageWater = 0;
		WhereBuilder builder = WhereBuilder.b("date", "==", today).and(
				"modelstate", "==", checkType);
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
					tvbiJiao.setText(
							Float.parseFloat(df
									.format(averageWater / 45.0f + 20.0))
							+ "%");
				}else{
					tvbiJiao.setText(
							 "0.0%");
				}

			}
		} catch (DbException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		List<BltModel> yesTodayBlts;
		float averageYesTodayWater = 0;
		// 鏄ㄥぉ鐨勬暟鎹�
		WhereBuilder builder1 = WhereBuilder.b("date", "==", lastDay).and(
				"modelstate", "==", checkType);
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
					tvAverageLevelData
							.setText(Float.parseFloat(df
									.format(averageYesTodayWater / 45.0f + 20.0))
									+ "%");
					detectionTimes.setText("检测次数 " + yesTodayBlts.size() + "次");
				} else {
					tvAverageLevelData.setText("0.0%");
					detectionTimes.setText("检测次数  0" + "次");
				}

				// mAdapter.notifymDataSetChanged(lists);
			} else {
				averageYesTodayWater = 0;

				tvAverageLevelData.setText("0.0%");
			}
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*if (averageYesTodayWater - averageWater > 0) {
			tvbiJiao.setText("+"
					+ Float.parseFloat(df
							.format((averageYesTodayWater - averageWater) / 45.0f + 20.0))
					+ "%");
		} else if (averageYesTodayWater - averageWater == 0) {
			tvbiJiao.setText("+0%");
		} else {
			tvbiJiao.setText("-"
					+ Float.parseFloat(df
							.format((averageWater - averageYesTodayWater) / 45.0f + 20.0))
					+ "%");
		}*/
	}

	/**
	 * 从数据库中查询某个小时检查的平均值
	 * 
	 * @param lastDay
	 * @param today
	 * @param tvbiJiao
	 */
	private float hourAvera(String today, String hour) {
		java.text.DecimalFormat df = new java.text.DecimalFormat("#0.0");
		DbUtils db = DbUtils.create(getActivity());
		List<BltModel> blts;
		float averageWater = 0;
		WhereBuilder builder = WhereBuilder.b("date", "==", today)
				.and("hour", "==", hour).and("modelstate", "==", checkType);
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
					averageWater = Float.parseFloat(df
							.format(averageWater / 45.0f + 20.0));

				}

			}
		} catch (DbException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return 0;
		}
		return averageWater;

	}

	/**
	 * 从数据库中查询某天检查的平均值
	 * 
	 * @param lastDay
	 * @param today
	 * @param tvbiJiao
	 */
	private Average dayAvera(String day) {
		Average average = new Average();
		java.text.DecimalFormat df = new java.text.DecimalFormat("#0.0");
		DbUtils db = DbUtils.create(getActivity());
		List<BltModel> blts;
		float averageWater = 0;
		WhereBuilder builder = WhereBuilder.b("date", "==", day).and(
				"modelstate", "==", checkType);
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
					average.setCount(blts.size());
					averageWater = Float.parseFloat(df
							.format(averageWater / 45.0f + 20.0));
					average.setAverage(averageWater);

				}

			}
		} catch (DbException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return average;
		}
		return average;

	}

	/**
	 * 取得当前日期所在周的第一天
	 */
	public Date getFirstDayOfWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // 星期一
		return c.getTime();
	}

	/**
	 * 取得当前日期所在周的最后一天
	 */
	public Date getLastDayOfWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 5); // 星期天
		return c.getTime();
	}

	/**
	 * 取得当前日期所在月的最后一天
	 */
	public Date getLastDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();// 获取当前日期
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		cal.add(Calendar.MONTH, 1);// 月增加1天
		cal.add(Calendar.DAY_OF_MONTH, -1);// 日期倒数一日,既得到本月最后一天
		return cal.getTime();
	}

	/**
	 * 月增加一天
	 */
	public Date getAddMonth(Date date) {
		Calendar cal = Calendar.getInstance();// 获取当前日期
		cal.setTime(date);
		if (cal.get(Calendar.MONTH) == 11) {
			cal.set(Calendar.MONTH, 0);
			cal.add(Calendar.YEAR, 1);
		} else {
			cal.add(Calendar.MONTH, 1);// 月增加1天
		}
		if (cal.get(Calendar.DAY_OF_MONTH) != 1) {
			int dateOfMonth = cal.getActualMaximum(Calendar.DATE);
			cal.set(Calendar.DAY_OF_MONTH, dateOfMonth);
		}
		return cal.getTime();
	}

	/**
	 * 月减少一天
	 */
	public Date getReduceMonth(Date date) {
		Calendar cal = Calendar.getInstance();// 获取当前日期
		cal.setTime(date);
		if (cal.get(Calendar.MONTH) == 0) {
			cal.set(Calendar.MONTH, 11);
			cal.add(Calendar.YEAR, -1);
		} else {
			cal.add(Calendar.MONTH, -1);// 月增加1天
		}
		if (cal.get(Calendar.DAY_OF_MONTH) != 1) {
			int dateOfMonth = cal.getActualMaximum(Calendar.DATE);
			cal.set(Calendar.DAY_OF_MONTH, dateOfMonth);
		}

		return cal.getTime();
	}

	/**
	 * 取得当前日期所在月的第一天
	 */
	public Date getFirstDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();// 获取当前日期
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天

		return cal.getTime();
	}

}
