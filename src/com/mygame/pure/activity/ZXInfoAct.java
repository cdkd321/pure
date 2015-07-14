package com.mygame.pure.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.fragment.AbAlertDialogFragment.AbDialogOnClickListener;
import com.ab.soap.AbSoapListener;
import com.ab.soap.AbSoapParams;
import com.ab.soap.AbSoapUtil;
import com.ab.util.AbDialogUtil;
import com.mygame.pure.R;
import com.mygame.pure.fragment.AFragment;
import com.mygame.pure.fragment.BFragment;
import com.mygame.pure.fragment.CFragment;
import com.mygame.pure.fragment.DFragment;

/**
 * 
 * 
 * 项目名称：pure 类名称：ZXInfoAct 类描述： 创建人：admin 创建时间：2015-7-13 下午2:07:35
 * 修改人：wanjianhua 修改时间：2015-7-13 下午2:07:35 修改备注： 资讯
 * 
 * @version
 * 
 */
public class ZXInfoAct extends BaseActivity implements OnClickListener {
	private TextView tv_zx1, tv_zx2, tv_zx3, tv_zx4;
	private ViewPager tab_pager;
	SlidePagerAdapter mPagerAdapter;
	static final int NUM_ITEMS = 4;
	private ImageButton back_btn;
	private AbSoapUtil mAbSoapUtil = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_zx_main);
		initView();
		mAbSoapUtil = AbSoapUtil.getInstance(this);
		mAbSoapUtil.setTimeout(10000);
		// sendPost();
		// getId();
		// gettob();
		// getListIDByType();
		getlisttobbyId();
	}

	public void initView() {
		tab_pager = (ViewPager) findViewById(R.id.tab_pager);
		mPagerAdapter = new SlidePagerAdapter(getSupportFragmentManager());
		back_btn = (ImageButton) findViewById(R.id.back_btn);
		tab_pager.setAdapter(mPagerAdapter);
		tv_zx1 = (TextView) findViewById(R.id.tv_zx1);
		tv_zx2 = (TextView) findViewById(R.id.tv_zx2);
		tv_zx3 = (TextView) findViewById(R.id.tv_zx3);
		tv_zx4 = (TextView) findViewById(R.id.tv_zx4);
		tv_zx1.setOnClickListener(this);
		tv_zx2.setOnClickListener(this);
		tv_zx3.setOnClickListener(this);
		tv_zx4.setOnClickListener(this);
		tv_zx1.setTextColor(Color.parseColor("#00abc6"));
		tv_zx2.setTextColor(Color.BLACK);
		tv_zx3.setTextColor(Color.BLACK);
		tv_zx4.setTextColor(Color.BLACK);
		tab_pager.setCurrentItem(0);
		tab_pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				if (arg0 == 0) {
					tv_zx1.setTextColor(Color.parseColor("#00abc6"));
					tv_zx2.setTextColor(Color.BLACK);
					tv_zx3.setTextColor(Color.BLACK);
					tv_zx4.setTextColor(Color.BLACK);
				} else if (arg0 == 1) {
					tv_zx1.setTextColor(Color.BLACK);
					tv_zx2.setTextColor(Color.parseColor("#00abc6"));
					tv_zx3.setTextColor(Color.BLACK);
					tv_zx4.setTextColor(Color.BLACK);
				} else if (arg0 == 2) {
					tv_zx1.setTextColor(Color.BLACK);
					tv_zx2.setTextColor(Color.BLACK);
					tv_zx3.setTextColor(Color.parseColor("#00abc6"));
					tv_zx4.setTextColor(Color.BLACK);
				} else {
					tv_zx1.setTextColor(Color.BLACK);
					tv_zx2.setTextColor(Color.BLACK);
					tv_zx3.setTextColor(Color.BLACK);
					tv_zx4.setTextColor(Color.parseColor("#00abc6"));
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
		back_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	/* PagerAdapter class */
	public class SlidePagerAdapter extends FragmentPagerAdapter {
		public SlidePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			/*
			 * IMPORTANT: This is the point. We create a RootFragment acting as
			 * a container for other fragments
			 */
			if (position == 0) {
				return new AFragment();
			} else if (position == 1) {
				return new BFragment();
			} else if (position == 2) {
				return new CFragment();
			} else {
				return new DFragment();
			}
		}

		@Override
		public int getCount() {
			return NUM_ITEMS;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_zx1:
			tv_zx1.setTextColor(Color.parseColor("#00abc6"));
			tv_zx2.setTextColor(Color.BLACK);
			tv_zx3.setTextColor(Color.BLACK);
			tv_zx4.setTextColor(Color.BLACK);
			tab_pager.setCurrentItem(0);
			break;
		case R.id.tv_zx2:
			tv_zx1.setTextColor(Color.BLACK);
			tv_zx2.setTextColor(Color.parseColor("#00abc6"));
			tv_zx3.setTextColor(Color.BLACK);
			tv_zx4.setTextColor(Color.BLACK);
			tab_pager.setCurrentItem(1);
			break;
		case R.id.tv_zx3:
			tv_zx1.setTextColor(Color.BLACK);
			tv_zx2.setTextColor(Color.BLACK);
			tv_zx3.setTextColor(Color.parseColor("#00abc6"));
			tv_zx4.setTextColor(Color.BLACK);
			tab_pager.setCurrentItem(2);
			break;
		case R.id.tv_zx4:
			tv_zx1.setTextColor(Color.BLACK);
			tv_zx2.setTextColor(Color.BLACK);
			tv_zx3.setTextColor(Color.BLACK);
			tv_zx4.setTextColor(Color.parseColor("#00abc6"));
			tab_pager.setCurrentItem(3);
			break;
		default:
			break;
		}
	}

	/*
	 * 首先取得列表项,将列表项填充到顶部,获得每个列表项对应的id
	 */
	public void sendPost() {
		String urlString3 = "http://miliapp.ebms.cn/webservice/newstype.asmx?op=GetList";
		String nameSpace3 = "http://tempuri.org/";
		String methodName3 = "GetList";
		AbSoapParams params3 = new AbSoapParams();
		params3.put("user1", "APP");
		params3.put("pass1", "4C85AF5AD4D0CC9349A8A468C38F292E");
		params3.put("appid", "3");
		mAbSoapUtil.call(urlString3, nameSpace3, methodName3, params3,
				new AbSoapListener() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						@SuppressWarnings("unused")
						String arString = arg1;
					}

					@Override
					public void onFailure(int arg0, String arg1, Throwable arg2) {

					}
				});
	}

	/*
	 * 获取每个id
	 */
	public void getId() {
		String urlString3 = "http://miliapp.ebms.cn/webservice/news.asmx?op=GetListID";
		String nameSpace3 = "http://tempuri.org/";
		String methodName3 = "GetListID";
		AbSoapParams params3 = new AbSoapParams();
		params3.put("user1", "APP");
		params3.put("pass1", "4C85AF5AD4D0CC9349A8A468C38F292E");
		params3.put("appid", "3");
		mAbSoapUtil.call(urlString3, nameSpace3, methodName3, params3,
				new AbSoapListener() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						@SuppressWarnings("unused")
						String arString = arg1;
						AbDialogUtil.showAlertDialog(getActivity(), "���ؽ��",
								arg1, new AbDialogOnClickListener() {

									@Override
									public void onNegativeClick() {
										// TODO Auto-generated method
										// stub

									}

									@Override
									public void onPositiveClick() {
										// TODO Auto-generated method
										// stub
									}
								});
					}

					@Override
					public void onFailure(int arg0, String arg1, Throwable arg2) {
						Toast.makeText(getApplicationContext(), arg1, 1).show();
					}
				});
	}

	/*
	 * 获取每个id
	 */
	public void gettob() {
		String urlString3 = "http://miliapp.ebms.cn/webservice/news.asmx?op=GetListatob";
		String nameSpace3 = "http://tempuri.org/";
		String methodName3 = "GetListatob";
		AbSoapParams params3 = new AbSoapParams();
		params3.put("user1", "APP");
		params3.put("pass1", "4C85AF5AD4D0CC9349A8A468C38F292E");
		params3.put("appid", "3");
		params3.put("start", "1");
		params3.put("count", "10");
		mAbSoapUtil.call(urlString3, nameSpace3, methodName3, params3,
				new AbSoapListener() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						@SuppressWarnings("unused")
						String arString = arg1;
						AbDialogUtil.showAlertDialog(getActivity(), "���ؽ��",
								arg1, new AbDialogOnClickListener() {

									@Override
									public void onNegativeClick() {
										// TODO Auto-generated method
										// stub

									}

									@Override
									public void onPositiveClick() {
										// TODO Auto-generated method
										// stub
									}
								});
					}

					@Override
					public void onFailure(int arg0, String arg1, Throwable arg2) {
						Toast.makeText(getApplicationContext(), arg1, 1).show();
					}
				});
	}

	/*
	 * 获取每个id
	 */
	public void getListIDByType() {
		String urlString3 = "http://miliapp.ebms.cn/webservice/news.asmx?op=GetListIDByType";
		String nameSpace3 = "http://tempuri.org/";
		String methodName3 = "GetListIDByType";
		AbSoapParams params3 = new AbSoapParams();
		params3.put("user1", "APP");
		params3.put("pass1", "4C85AF5AD4D0CC9349A8A468C38F292E");
		params3.put("appid", "3");
		params3.put("typeid", "1");
		mAbSoapUtil.call(urlString3, nameSpace3, methodName3, params3,
				new AbSoapListener() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						@SuppressWarnings("unused")
						String arString = arg1;
						AbDialogUtil.showAlertDialog(getActivity(), "���ؽ��",
								arg1, new AbDialogOnClickListener() {

									@Override
									public void onNegativeClick() {
										// TODO Auto-generated method
										// stub
									}

									@Override
									public void onPositiveClick() {
										// TODO Auto-generated method
										// stub
									}
								});
					}

					@Override
					public void onFailure(int arg0, String arg1, Throwable arg2) {
						Toast.makeText(getApplicationContext(), arg1, 1).show();
					}
				});
	}

	public void getlisttobbyId() {
		String urlString3 = "http://miliapp.ebms.cn/webservice/news.asmx?op=GetListatobByType";
		String nameSpace3 = "http://tempuri.org/";
		String methodName3 = "GetListatobByType";
		AbSoapParams params3 = new AbSoapParams();
		params3.put("user1", "APP");
		params3.put("pass1", "4C85AF5AD4D0CC9349A8A468C38F292E");
		params3.put("appid", "3");
		params3.put("typeid", "1");
		params3.put("start", "1");
		params3.put("count", "10");
		mAbSoapUtil.call(urlString3, nameSpace3, methodName3, params3,
				new AbSoapListener() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						@SuppressWarnings("unused")
						String arString = arg1;
						AbDialogUtil.showAlertDialog(getActivity(), "���ؽ��",
								arg1, new AbDialogOnClickListener() {

									@Override
									public void onNegativeClick() {
										// TODO Auto-generated method
										// stub
									}

									@Override
									public void onPositiveClick() {
										// TODO Auto-generated method
										// stub
									}
								});
					}

					@Override
					public void onFailure(int arg0, String arg1, Throwable arg2) {
						Toast.makeText(getApplicationContext(), arg1, 1).show();
					}
				});
	}
}
