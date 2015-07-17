package com.mygame.pure.activity;

import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.fragment.AbAlertDialogFragment.AbDialogOnClickListener;
import com.ab.soap.AbSoapListener;
import com.ab.soap.AbSoapParams;
import com.ab.soap.AbSoapUtil;
import com.ab.util.AbDialogUtil;
import com.mygame.pure.R;
import com.mygame.pure.adapter.PagerView;
import com.mygame.pure.bean.Banner;
import com.mygame.pure.fragment.AFragment;
import com.mygame.pure.fragment.BFragment;
import com.mygame.pure.fragment.CFragment;
import com.mygame.pure.fragment.DFragment;
import com.squareup.picasso.Picasso;

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
	private LinearLayout tab_pager;
	SlidePagerAdapter mPagerAdapter;
	static final int NUM_ITEMS = 4;
	private ImageButton back_btn;
	private AbSoapUtil mAbSoapUtil = null;
	private FragmentManager mFM = null;
	public static ViewPager banner;
	private static ImageHandler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_zx_main);
		handler = new ImageHandler(new WeakReference<ZXInfoAct>(this));
		initView();
		mAbSoapUtil = AbSoapUtil.getInstance(this);
		mAbSoapUtil.setTimeout(10000);
		sendPost();
		getTopG();
		// getId();
		// gettob();
		// getListIDByType();
		// getlisttobbyId();

		changePerson();

	}// end of onCreate

	public void initView() {
		banner = (ViewPager) findViewById(R.id.banner);
		tab_pager = (LinearLayout) findViewById(R.id.tab_pager);
		mPagerAdapter = new SlidePagerAdapter(getSupportFragmentManager());
		back_btn = (ImageButton) findViewById(R.id.back_btn);
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

	private View last, now;
	View v1, v2;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_zx1:
			now = tab_pager.getChildAt(0);
			tv_zx1.setTextColor(Color.parseColor("#00abc6"));
			tv_zx2.setTextColor(Color.BLACK);
			tv_zx3.setTextColor(Color.BLACK);
			tv_zx4.setTextColor(Color.BLACK);
			changePerson();
			break;
		case R.id.tv_zx2:
			now = tab_pager.getChildAt(1);
			changeBussiness();
			tv_zx1.setTextColor(Color.BLACK);
			tv_zx2.setTextColor(Color.parseColor("#00abc6"));
			tv_zx3.setTextColor(Color.BLACK);
			tv_zx4.setTextColor(Color.BLACK);
			break;
		case R.id.tv_zx3:
			now = tab_pager.getChildAt(2);
			thrBussiness();
			tv_zx1.setTextColor(Color.BLACK);
			tv_zx2.setTextColor(Color.BLACK);
			tv_zx3.setTextColor(Color.parseColor("#00abc6"));
			tv_zx4.setTextColor(Color.BLACK);
			break;
		case R.id.tv_zx4:
			now = tab_pager.getChildAt(3);
			FouBussiness();
			tv_zx1.setTextColor(Color.BLACK);
			tv_zx2.setTextColor(Color.BLACK);
			tv_zx3.setTextColor(Color.BLACK);
			tv_zx4.setTextColor(Color.parseColor("#00abc6"));
			break;
		default:
			break;
		}
	}

	private void changePerson() {
		Fragment f = new AFragment();
		if (null == mFM)
			mFM = getSupportFragmentManager();
		FragmentTransaction ft = mFM.beginTransaction();
		ft.replace(R.id.tab_pager, f);
		ft.commit();
	}

	private void changeBussiness() {
		Fragment f = new BFragment();
		if (null == mFM)
			mFM = getSupportFragmentManager();
		FragmentTransaction ft = mFM.beginTransaction();
		ft.replace(R.id.tab_pager, f);
		ft.commit();
	}

	private void thrBussiness() {
		Fragment f = new CFragment();
		if (null == mFM)
			mFM = getSupportFragmentManager();
		FragmentTransaction ft = mFM.beginTransaction();
		ft.replace(R.id.tab_pager, f);
		ft.commit();
	}

	private void FouBussiness() {
		Fragment f = new DFragment();
		if (null == mFM)
			mFM = getSupportFragmentManager();
		FragmentTransaction ft = mFM.beginTransaction();
		ft.replace(R.id.tab_pager, f);
		ft.commit();
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
						String a = arg1.replaceAll("Table1=anyType", "/");
						String[] bStrings = a.split("/");
						String[] aStrings = bStrings[1].split("Title=");
						String[] cStrings = aStrings[1].split(";");
						String[] aStrings1 = bStrings[2].split("Title=");
						String[] cStrings1 = aStrings1[1].split(";");
						String[] aStrings11 = bStrings[3].split("Title=");
						String[] cStrings11 = aStrings11[1].split(";");
						String[] aStrings111 = bStrings[4].split("Title=");
						String[] cStrings111 = aStrings111[1].split(";");
						tv_zx1.setText(cStrings[0]);
						tv_zx2.setText(cStrings1[0]);
						tv_zx3.setText(cStrings11[0]);
						tv_zx4.setText(cStrings111[0]);
						/*
						 * try { Thread.sleep(3000);
						 * 
						 * } catch (InterruptedException e) { // TODO
						 * Auto-generated catch block e.printStackTrace(); }
						 */
					}

					@Override
					public void onFailure(int arg0, String arg1, Throwable arg2) {

					}
				});

	}

	/*
	 * 首先取得列表项,将列表项填充到顶部,获得每个列表项对应的id
	 */
	public void getTopG() {

		String urlString3 = "http://miliapp.ebms.cn/webservice/newsbanner.asmx?op=GetList";
		String nameSpace3 = "http://tempuri.org/";
		String methodName3 = "GetList";
		AbSoapParams params3 = new AbSoapParams();
		params3.put("user1", "APP");
		params3.put("pass1", "4C85AF5AD4D0CC9349A8A468C38F292E");
		params3.put("appid", "3");
		mAbSoapUtil.call(urlString3, nameSpace3, methodName3, params3,
				new AbSoapListener() {
					@Override
					public void onSuccess(int arg0, final String arg1) {
						// TODO Auto-generated method stub
						@SuppressWarnings("unused")
						String arString = arg1;

						// AbDialogUtil.showAlertDialog(getActivity(),
						// "���ؽ��",
						// arg1, new AbDialogOnClickListener() {
						//
						// @Override
						// public void onNegativeClick() {
						// TODO Auto-generated method
						// stub
						LayoutInflater inflater = LayoutInflater
								.from(getApplicationContext());

						String str = arg1.replace("Table1=anyType{", ">");
						String[] arry = str.split(">");
						final ArrayList<Banner> banners = new ArrayList<Banner>();
						ArrayList<View> views = new ArrayList<View>();
						for (int i = 1; i < arry.length; i++) {
							View view1 = (View) inflater.inflate(
									R.layout.item1, null);
							ImageView img_tb = (ImageView) view1
									.findViewById(R.id.img_tb);
							TextView tv_text = (TextView) view1
									.findViewById(R.id.tv_text);

							Banner banner = new Banner();
							String[] tab = arry[i].split(";");
							String image = tab[3].replace("Image=", "").trim();
							final String url = tab[9].replace("Url=", "");
							banner.setImage(image);
							banner.setUrl(url);
							banners.add(banner);
							Picasso.with(getApplicationContext())
									.load("http://miliapp.ebms.cn" + image)
									.into(img_tb);
							view1.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View arg0) {
									Intent intent = new Intent(ZXInfoAct.this,
											AboutActivity.class);
									intent.putExtra("url", url);
									startActivity(intent);
								}
							});
							views.add(view1);
						}

						banner.setAdapter(new PagerView(views));

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

	private static class ImageHandler extends Handler {

		/**
		 * 请求更新显示的View。
		 */
		protected static final int MSG_UPDATE_IMAGE = 1;
		/**
		 * 请求暂停轮播。
		 */
		protected static final int MSG_KEEP_SILENT = 2;
		/**
		 * 请求恢复轮播。
		 */
		protected static final int MSG_BREAK_SILENT = 3;
		/**
		 * 记录最新的页号，当用户手动滑动时需要记录新页号，否则会使轮播的页面出错。
		 * 例如当前如果在第一页，本来准备播放的是第二页，而这时候用户滑动到了末页，
		 * 则应该播放的是第一页，如果继续按照原来的第二页播放，则逻辑上有问题。
		 */
		protected static final int MSG_PAGE_CHANGED = 4;

		// 轮播间隔时间
		protected static final long MSG_DELAY = 3000;

		// 使用弱引用避免Handler泄露.这里的泛型参数可以不是Activity，也可以是Fragment等
		private WeakReference<ZXInfoAct> weakReference;
		private int currentItem = 0;

		protected ImageHandler(WeakReference<ZXInfoAct> wk) {
			weakReference = wk;
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			ZXInfoAct activity = weakReference.get();
			if (activity == null) {
				// Activity已经回收，无需再处理UI了
				return;
			}
			// 检查消息队列并移除未发送的消息，这主要是避免在复杂环境下消息出现重复等问题。
			if (activity.handler.hasMessages(MSG_UPDATE_IMAGE)) {
				activity.handler.removeMessages(MSG_UPDATE_IMAGE);
			}
			switch (msg.what) {
			case MSG_UPDATE_IMAGE:
				currentItem++;
				banner.setCurrentItem(currentItem);
				// 准备下次播放
				handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
				break;
			case MSG_KEEP_SILENT:
				// 只要不发送消息就暂停了
				break;
			case MSG_BREAK_SILENT:
				handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
				break;
			case MSG_PAGE_CHANGED:
				// 记录当前的页号，避免播放的时候页面显示不正确。
				currentItem = msg.arg1;
				break;
			default:
				break;
			}
		}
	}
}
