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
import com.mygame.pure.adapter.VerticalPagerAdapter;
import com.mygame.pure.view.VerticalViewPager;

public class NeckFragment extends BaseFragment{
	
	private List<Fragment> oneListFragments;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.check_two, container, false);
		 VerticalViewPager vpager = (VerticalViewPager) rootView.findViewById(R.id.check_two);
		 
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
		NeckFragmentUp fragment = new NeckFragmentUp();
		listFragments.add(fragment);
		NeckFragmentDown fragment2 = new NeckFragmentDown();
		listFragments.add(fragment2);
		return listFragments;
	}
}
