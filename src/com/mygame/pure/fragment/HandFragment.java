package com.mygame.pure.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.mygame.pure.R;
import com.mygame.pure.activity.ActMain;
import com.mygame.pure.adapter.VerticalPagerAdapter;
import com.mygame.pure.utils.ToastHelper;
import com.mygame.pure.view.VerticalViewPager;

public class HandFragment extends BaseFragment implements OnClickListener {

	private ActMain main;
	private int checkType;
	private List<Fragment> oneListFragments;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		main = (ActMain) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.check_one, container,
				false);
		 VerticalViewPager vpager = (VerticalViewPager) rootView.findViewById(R.id.check_one);
		 
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
		HandFragmentUp fragment = HandFragmentUp.newInstance(type);
		listFragments.add(fragment);
		HandFragmentDown fragment2 = HandFragmentDown.newInstance(type);
		listFragments.add(fragment2);
		return listFragments;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy(); 
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		/*
		 * if(SelfDefineApplication.getInstance().mService!=null){
		 * if(SelfDefineApplication
		 * .getInstance().mService.mConnectionState==BleService
		 * .STATE_CONNECTED){ tvBlueTouth.setText("已连接");
		 * tvBlueTouth.setVisibility(View.VISIBLE); try { Thread.sleep(1000);
		 * tvBlueTouth.setVisibility(View.GONE); } catch (InterruptedException
		 * e) { // TODO Auto-generated catch block e.printStackTrace(); } }
		 * if(SelfDefineApplication
		 * .getInstance().mService.mConnectionState==BleService
		 * .STATE_DISCONNECTED
		 * ||SelfDefineApplication.getInstance().mService.mConnectionState
		 * ==BleService.STATE_CONNECTING){ tvBlueTouth.setText("断开连接");
		 * tvBlueTouth.setVisibility(View.VISIBLE); try { Thread.sleep(1000);
		 * tvBlueTouth.setVisibility(View.GONE); } catch (InterruptedException
		 * e) { // TODO Auto-generated catch block e.printStackTrace(); } }
		 * 
		 * }
		 */
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
