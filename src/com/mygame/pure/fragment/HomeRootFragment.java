package com.mygame.pure.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mygame.pure.R;
import com.mygame.pure.view.VerticalViewPager;

public class HomeRootFragment extends Fragment {

	private Bundle savedInstanceState;
	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTrasaction;
	private VerticalViewPager viewPager;
	private int checkType;

	public static HomeRootFragment newInstance(int checkType) {
		HomeRootFragment f = new HomeRootFragment();
		Bundle args = new Bundle();
		args.putInt("checkType", checkType);
		f.setArguments(args);
		return f;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.savedInstanceState = savedInstanceState;
		View view = inflater.inflate(R.layout.homerootfragment, container,
				false);
		viewPager = (VerticalViewPager) view.findViewById(R.id.vertical_page);

		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		checkType = getArguments().getInt("checkType");
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	class LongPagerAdapter extends VerticalViewPager {

		public LongPagerAdapter(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		@Override
		public int getChildCount() {
			// TODO Auto-generated method stub
			return 2;
		}

	}

}
