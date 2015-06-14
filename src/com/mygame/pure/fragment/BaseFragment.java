package com.mygame.pure.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.mygame.pure.R;
import com.mygame.pure.SelfDefineApplication;
import com.mygame.pure.bean.Constant;
import com.mygame.pure.http.AjaxParams;
import com.mygame.pure.http.FinalHttp;
import com.mygame.pure.info.User;
import com.mygame.pure.view.PureActionBar;
import com.mygame.pure.view.PureActionBar.IProvideTkActionBar;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * “我”菜单
 * 
 * @author 
 * 
 */
public class BaseFragment extends Fragment implements IProvideTkActionBar {

	protected SharedPreferences preferences;
	public User user;
	public String ipAddress;
	public ImageLoader imageLoader = ImageLoader.getInstance();
	protected PureActionBar mTkActionBar;
	protected View rootView;
	private FinalHttp http;
	Activity activity = null;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		http = SelfDefineApplication.getInstance().getFinalHttp();
		preferences = Constant.Preference.getSharedPreferences(getActivity());
		getCurrentUser();
		return container;
	}

	public FinalHttp getFinalHttp() {
		return http;
	}

	public void setRootView(View view) {
		this.rootView = view;
	}

	@Override
	public PureActionBar getTkActionBar() {
		if (rootView != null) {
			mTkActionBar = (PureActionBar) rootView
					.findViewById(R.id.actionBar);
		}
		return mTkActionBar;
	}

	/**
	 * 添加顶部标题栏标题
	 */
	public void setTitle(CharSequence title) {
		getTkActionBar();
		if (mTkActionBar != null) {
			mTkActionBar.setTitle(title, null);
		}
	}

	/**
	 * 添加顶部actionbar左侧返回按钮
	 * 
	 * @param listener
	 *            如果为null，就finish
	 */
	protected void addBackBtn(final OnClickListener listener) {
		getTkActionBar();
		if (mTkActionBar != null) {
			mTkActionBar.addBackText(R.string.back, listener);
		}
	}

	/**
	 * 添加顶部标题栏左侧返回按钮
	 * 
	 * @param listener
	 */
	protected void addBackImage(int drawId, final OnClickListener listener) {
		getTkActionBar();
		if (mTkActionBar != null) {
			mTkActionBar.addBackImage(drawId, listener);
		}
	}

	protected void addRightImage(int drawId, OnClickListener listener) {
		getTkActionBar();
		if (mTkActionBar != null) {
			mTkActionBar.addRightImage(drawId, listener);
		}
	}

	protected void addRightBtn(int id, OnClickListener listener) {
		getTkActionBar();
		if (mTkActionBar != null) {
			mTkActionBar.addRightText(id, listener);
		}
	}

	/**
	 * 获取当前登录用户信息
	 */
	protected void getCurrentUser() {
		try {
			// user = new Gson().fromJson(
			// preferences.getString(Preference.LOGIN_USER, null),
			// new TypeToken<Response<LoginUserBean>>() {
			// }.getType());
			user = SelfDefineApplication.getInstance().getUser();
		} catch (Exception e) {

		}
	}

	/**
	 * 隐藏输入法
	 */
	protected void hideInput() {
		View view = getActivity().getWindow().peekDecorView();
		if (view != null) {
			InputMethodManager inputmanger = (InputMethodManager) getActivity()
					.getSystemService(getActivity().INPUT_METHOD_SERVICE);
			inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

	/**
	 * 返回按钮处理
	 * 
	 * @param view
	 */
	public void onBackClick(View view) {
		getActivity().finish();
	}

	/**
	 * 隐藏覆盖在整个fragment上的空视图--表示没有数据的情况，可以动态添加一个自定义视图
	 * 
	 * @param rootView
	 *            -- fragment的根视图
	 */
	public void hideEmptyView(View rootView) {
//		View emptyLayout = rootView.findViewById(R.id.rlEmptyView);
//		if (null != emptyLayout) {
//			emptyLayout.setVisibility(View.GONE);
//		}
	}

	/**
	 * 设置加载视图
	 * 
	 * @param rootView
	 */
	public void setLoadingView(View rootView) {
//		if (rootView != null) {
//			RelativeLayout llEmptyLayout = (RelativeLayout) rootView
//					.findViewById(R.id.rlEmptyView);
//			if (null != llEmptyLayout) {
//				llEmptyLayout.setVisibility(View.VISIBLE);
//				if (llEmptyLayout.getChildCount() >= 1) {
//					llEmptyLayout.removeAllViews();
//				}
//				View emptyView = View.inflate(getActivity(),
//						R.layout.empty_text_view, null);
//				TextView emptyText = (TextView) emptyView
//						.findViewById(R.id.tvEmpty);
//				emptyText.setText("加载中，请稍后...");
//				llEmptyLayout.addView(emptyView);
//			}
//		}
	}

	/**
	 * 设置空视图，包含一个图标和一行文字
	 * 
	 * @param rootView
	 *            根视图
	 * @param strId
	 *            文字id
	 * @param topDrawId
	 *            图标id
	 */
	public void setEmptyView(View rootView, int strId, int topDrawId) {
//		RelativeLayout llEmptyLayout = (RelativeLayout) rootView
//				.findViewById(R.id.rlEmptyView);
//		if (null != llEmptyLayout) {
//			llEmptyLayout.setVisibility(View.VISIBLE);
//			if (llEmptyLayout.getChildCount() >= 1) {
//				llEmptyLayout.removeAllViews();
//			}
//			View emptyView = View.inflate(getActivity(),
//					R.layout.empty_call_log, null);
//			TextView emptyText = (TextView) emptyView
//					.findViewById(R.id.tvEmpty);
//			emptyText.setCompoundDrawablesWithIntrinsicBounds(0, topDrawId, 0,
//					0);
//			emptyText.setText(strId);
//			llEmptyLayout.addView(emptyView);
//		}
	}

	/**
	 * 设置在整个fragment上的空视图--表示没有数据的情况，可以动态添加一个自定义视图
	 * 
	 * @param rootView
	 *            -- fragment的根视图
	 * @param context
	 *            -- 上下文
	 * @param layoutId
	 *            -- 空视图的布局视图（注意会被放在全屏幕的LinearLayout中。)
	 */
	public void setEmptyView(View rootView, Context context, int layoutId) {
//		RelativeLayout rlEmptyLayout = (RelativeLayout) rootView
//				.findViewById(R.id.rlEmptyView);
//		if (null != rlEmptyLayout) {
//			if (rlEmptyLayout.getChildCount() > 1) {
//				rlEmptyLayout.removeAllViews();
//			}
//			rlEmptyLayout.addView(View.inflate(context, layoutId, null));
//		}
	}

	/**
	 * 如果使用actionbar，需要主动调用一下这个方法，把actionbar容器的的id传入
	 */
	@Override
	public void setupTkActionBar(int resId) {
		mTkActionBar = (PureActionBar) getView().findViewById(resId);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
	}

	@Override
	public void setTitle(CharSequence title, OnClickListener listener) {
		getTkActionBar();
		if (mTkActionBar != null) {
			mTkActionBar.setTitle(title, null);
		}
	}

	@Override
	public void setTitle(int strId, OnClickListener listener) {
		getTkActionBar();
		if (mTkActionBar != null) {
			mTkActionBar.setTitle(getString(strId), null);
		}
	}

	/**
	 * 给AjaxPrams对象存放一个key-value值，如果value为空，就不存放
	 * 
	 * @param key
	 * @param value
	 * @param params
	 */
	protected void putParam(String key, String value, AjaxParams params) {
		if (!TextUtils.isEmpty(value)) {
			params.put(key, value);
		}
	}

}
