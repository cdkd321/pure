package com.mygame.pure.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mygame.pure.R;
import com.mygame.pure.activity.ActMain;
import com.mygame.pure.adapter.VerticalPagerAdapter;
import com.mygame.pure.view.VerticalViewPager;

public class FaceFragment extends BaseFragment{
	
	ActMain mact;
	private List<Fragment> oneListFragments;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mact = (ActMain) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.check_three, container, false);
		
		VerticalViewPager vpager = (VerticalViewPager) rootView.findViewById(R.id.check_three);
		oneListFragments = getFragmentList();
		VerticalPagerAdapter adapter = new VerticalPagerAdapter(getChildFragmentManager(), oneListFragments);
		vpager.setAdapter(adapter);
		return rootView;
	}
	
	private List<Fragment> getFragmentList() {
		return getOneFragments(0);
	}
	
	private List<Fragment> getOneFragments(int type) {
		List<Fragment> listFragments = new ArrayList<Fragment>();
		FaceFragmentUp fragment = new FaceFragmentUp();
		listFragments.add(fragment);
		FaceFragmentDown fragment2 = new FaceFragmentDown();
		listFragments.add(fragment2);
		return listFragments;
	}
}
