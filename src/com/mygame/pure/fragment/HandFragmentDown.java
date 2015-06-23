package com.mygame.pure.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.mygame.pure.R;
import com.mygame.pure.activity.ActMain;
import com.mygame.pure.activity.ActSpecify;
import com.mygame.pure.activity.MoreAct;
import com.mygame.pure.adapter.HistoryAdapter;
import com.mygame.pure.utils.ToastHelper;
import com.mygame.pure.view.CircleProgressBarBlue;

public class HandFragmentDown extends BaseFragment implements OnClickListener{

	ActMain mact;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mact = (ActMain) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.tab_fragment_hand_down, container, false);
		initView();
		mact.addBackImage(R.drawable.btn_more_bg, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(v.getContext(), MoreAct.class));
			}
		});
		
		mact.addRightImage(R.drawable.btn_news_bg, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(v.getContext(), ActSpecify.class));
			}
		});
		return rootView;
	}
	
	private void initView() {
		 ViewPager vpager = (ViewPager)rootView.findViewById(R.id.vPager);
		 List<View> viewList = new ArrayList<View>();
		 for(int i = 0; i < 3; i++){
			 View view = View.inflate(activity, R.layout.date_record, null);
			 viewList.add(view);
		 }
		 HistoryAdapter adapter = new HistoryAdapter(viewList);
		 CircleProgressBarBlue pb = (CircleProgressBarBlue) rootView.findViewById(R.id.cpb);
		 pb.setPoint(50);
		 vpager.setAdapter(adapter);
	}

	// 点击每天显示水平
	public void clickPageLeft(View v){
		
	}
	
	public void clickPageRight(View v){
		
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
