package com.mygame.pure;

import com.mygame.pure.utils.ToastHelper;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class HandFragment extends BaseFragment implements OnClickListener{

	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		rootView = View.inflate(activity, R.layout.tab_fragment_hand, null);
		setTitle("检测中心");
		addBackImage(R.drawable.btn_more_bg, this);
		addRightImage(R.drawable.btn_news_bg, this);
		return rootView;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
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

	
	
}
