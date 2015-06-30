package com.mygame.pure.view;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;

/**
 * UIActionSheet
 * 
 * @author dujunhui
 * @date 2014-6-6 12:36:53
 */
public class ActionSheet implements OnClickListener {

	private static final int TRANSLATE_DURATION = 200;
	private static final int ALPHA_DURATION = 300;

	private boolean mDismissed = true;
	private ActionSheetListener mListener;
	private View mView;
	private LinearLayout mPanel;
	private ViewGroup mGroup;
	private View mBg;
	private boolean isCancel = true;

	public void dismiss() {
		if (mDismissed) {
			return;
		}
		mDismissed = true;
	}

	public void onCreateView(Activity context, LayoutInflater inflater) {
		act = context;
		// mView = createView();
		mGroup = (ViewGroup) context.getWindow().getDecorView();
		mGroup.addView(mView);
		mPanel.startAnimation(createAlphaInAnimation());
		mView.startAnimation(createTranslationInAnimation());
	}

	private Animation createTranslationInAnimation() {
		int type = Animation.RELATIVE_TO_SELF;
		TranslateAnimation an = new TranslateAnimation(type, 0, type, 0, type,
				1, type, 0);
		an.setDuration(TRANSLATE_DURATION);
		return an;
	}

	private Animation createAlphaInAnimation() {
		AlphaAnimation an = new AlphaAnimation(0, 1);
		an.setDuration(ALPHA_DURATION);
		return an;
	}

	private Animation createTranslationOutAnimation() {
		int type = Animation.RELATIVE_TO_SELF;
		TranslateAnimation an = new TranslateAnimation(type, 0, type, 0, type,
				0, type, 1);
		an.setDuration(TRANSLATE_DURATION);
		an.setFillAfter(true);
		return an;
	}

	private Animation createAlphaOutAnimation() {
		AlphaAnimation an = new AlphaAnimation(1, 0);
		an.setDuration(ALPHA_DURATION);
		an.setFillAfter(true);
		return an;
	}

	private Activity act;

	private GridView gv;

	private Activity getActivity() {
		return act;
	}

	/*
	 * public View createView() { mBg = View.inflate(getActivity(),
	 * R.layout.share_sns_activity, null);
	 * 
	 * mPanel = (LinearLayout) mBg.findViewById(R.id.ll_show_bottom);
	 * mBg.setBackgroundColor(Color.argb(136, 0, 0, 0));
	 * mPanel.setOnClickListener(this); TextView tvCancel = (TextView)
	 * mBg.findViewById(R.id.tv_cancel); gv = (GridView)
	 * mBg.findViewById(R.id.gv_bottom); ArrayList<ShareEntity> list = new
	 * ArrayList<ShareEntity>(); gv.setVerticalScrollBarEnabled(true);
	 * gv.setOnTouchListener(new OnTouchListener() {
	 * 
	 * @Override public boolean onTouch(View v, MotionEvent event) { return
	 * MotionEvent.ACTION_MOVE == event.getAction() ? true : false; } });
	 * gv.setAdapter(new MyAdater(getActivity(), list));
	 * tvCancel.setOnClickListener(this);
	 * 
	 * return mBg; }
	 */

	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 2) {
				getActivity().finish();
			}
		}

	};

	public LinearLayout.LayoutParams createButtonLayoutParams() {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		return params;
	}

	public void onDestroyView() {
		mPanel.startAnimation(createTranslationOutAnimation());
		mBg.startAnimation(createAlphaOutAnimation());
		mView.postDelayed(new Runnable() {
			@Override
			public void run() {
				mGroup.removeView(mView);
			}
		}, ALPHA_DURATION);
		if (mListener != null) {
			mListener.onDismiss(this, isCancel);
		}
	}

	public void setActionSheetListener(ActionSheetListener listener) {
		mListener = listener;
	}

	@Override
	public void onClick(View v) {
		dismiss();
		/*
		 * if (v.getId() == R.id.tv_cancel || v.getId() == R.id.ll_show_bottom)
		 * { if (act != null) { onDestroyView();
		 * act.overridePendingTransition(R.anim.push_bottom_in,
		 * R.anim.push_bottom_out); mHandler.sendEmptyMessageDelayed(2, 500); }
		 * isCancel = false; }
		 */
	}

	public static interface ActionSheetListener {

		void onDismiss(ActionSheet actionSheet, boolean isCancel);

		void onOtherButtonClick(ActionSheet actionSheet, int index);
	}

	public void setOnItemClickListener(OnItemClickListener listener) {
		if (gv != null) {
			gv.setOnItemClickListener(listener);
		}
	}

}