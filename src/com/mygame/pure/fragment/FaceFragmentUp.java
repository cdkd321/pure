package com.mygame.pure.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.mygame.pure.R;
import com.mygame.pure.activity.ActMain;
import com.mygame.pure.activity.MoreAct;

public class FaceFragmentUp extends BaseFragment{
	
	ActMain mact;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mact = (ActMain) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.tab_fragment_face, container, false);
		return rootView;
	}
}
