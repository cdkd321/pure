package com.mygame.pure.fragment;

import com.mygame.pure.R;
import com.mygame.pure.R.drawable;
import com.mygame.pure.R.id;
import com.mygame.pure.R.layout;
import com.mygame.pure.activity.MainActivity;
import com.mygame.pure.utils.ToastHelper;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class HandFragmentDown extends BaseFragment implements OnClickListener{

	MainActivity mact;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mact = (MainActivity) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.tab_fragment_hand_down, container, false);
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
