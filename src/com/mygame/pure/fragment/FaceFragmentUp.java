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
import com.mygame.pure.activity.MainActivity;
import com.mygame.pure.activity.MoreAct;

public class FaceFragmentUp extends BaseFragment{
	
	MainActivity mact;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mact = (MainActivity) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.tab_fragment_face, container, false);
		
		mact.addBackImage(R.drawable.btn_more_bg, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mact.startActivity(new Intent(activity, MoreAct.class));
			}
		});
		return rootView;
	}
}
