package com.mygame.pure;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;

import com.mygame.pure.adapter.HistoryAdapter;
import com.mygame.pure.adapter.VerticalPagerAdapter;
import com.mygame.pure.view.VerticalViewPager;

public class MainActivity extends BaseActivity implements OnClickListener {
	
	private List<View> baseList;
	private ViewPager viewPager;
	private View llTab1, llTab2, llTab3, llTab4; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_main);
		initTab();
	}
	
	
	public List<View> getList() {
		List<View> mList;
		int[] layoutId = new int[]{R.layout.check_one, R.layout.check_two,
				R.layout.check_three, R.layout.check_four,
		};
		mList = new ArrayList<View>();
		for(int i = 0; i < 4; i++){
			View view1 = View.inflate(this, layoutId[i], null);
			mList.add(view1);
		}
		return mList;
	}
	
	public List<Fragment> getFragmentList(int type) {
		List<Fragment> listFragments = new ArrayList<Fragment>();

		switch (type) {
		case 0:
			listFragments = getOneFragments();
			break;
		case 1:
			listFragments = getTwoFragments();
			break;
		case 2:
			listFragments = getThreeFragments();
			break;
		case 3:
			listFragments = getFourFragments();
			break;
		default:
			break;
		}
		
		return listFragments;
	}
	
	
	private List<Fragment> getOneFragments() {
		List<Fragment> listFragments = new ArrayList<Fragment>();
		HandFragment fragment = new HandFragment();
		listFragments.add(fragment);
		return listFragments;
	}


	public List<Fragment> getTwoFragments(){
		List<Fragment> listFragments = new ArrayList<Fragment>();
		FaceFragment fragment = new FaceFragment();
		listFragments.add(fragment); 
		return listFragments;
		
	}
	public List<Fragment> getThreeFragments(){
		List<Fragment> listFragments = new ArrayList<Fragment>();
		EyesFragment fragment = new EyesFragment();
		listFragments.add(fragment); 
		return listFragments;
	}
	
	private List<Fragment> getFourFragments() {
		List<Fragment> listFragments = new ArrayList<Fragment>();
		NeckFragment fragment = new NeckFragment();
		listFragments.add(fragment);  
		return listFragments;
	}
	

	private void initTab() {
		llTab1 = findViewById(R.id.llTab1);
		llTab2 = findViewById(R.id.llTab2);
		llTab3 = findViewById(R.id.llTab3);
		llTab4 = findViewById(R.id.llTab4);
		
		llTab1.setOnClickListener(this);
		llTab2.setOnClickListener(this);
		llTab3.setOnClickListener(this);
		llTab4.setOnClickListener(this);
		
		
		viewPager = (ViewPager) findViewById(R.id.check_list);
		baseList = getList();
		
		HistoryAdapter adapter = new HistoryAdapter(baseList);
		int[] viewPageId = new int[] {
				R.id.check_one,
				R.id.check_two,
				R.id.check_three,
				R.id.check_four
		};
		for(int i = 0; i < 4; i++){
			List<Fragment> fragments0 = getFragmentList(0);
			VerticalPagerAdapter fragmentAdapter = new VerticalPagerAdapter(
					getSupportFragmentManager(), fragments0);
			VerticalViewPager page = (VerticalViewPager) baseList.get(i).findViewById(viewPageId[i]);
			page.setOnPageChangeListener(new VerticalViewPager.OnPageChangeListener() {
				
				@Override
				public void onPageSelected(int position) {
					 
				}
				
				@Override
				public void onPageScrolled(int position, float positionOffset,
						int positionOffsetPixels) {
				}
				
				@Override
				public void onPageScrollStateChanged(int state) {
					
				}
			});
			page.setAdapter(fragmentAdapter);
		}
		viewPager.setAdapter(adapter);
		llTab1.setSelected(true);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				setTabSelected(arg0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) { }
			
			@Override
			public void onPageScrollStateChanged(int arg0) { }
		});
	}
	
	private void setTabSelected(int i){
			llTab1.setSelected(i == 0);
			llTab2.setSelected(i == 1);
			llTab3.setSelected(i == 2);
			llTab4.setSelected(i == 3);
	}


	@Override
	public void onClick(View v) {
		 switch (v.getId()) {
		case R.id.llTab1:
			viewPager.setCurrentItem(0);
			setTabSelected(0);
			break;
		case R.id.llTab2:
			viewPager.setCurrentItem(1);
			setTabSelected(1);
			break;
		case R.id.llTab3:
			viewPager.setCurrentItem(2);
			setTabSelected(2);
			break;
		case R.id.llTab4:
			viewPager.setCurrentItem(3);
			setTabSelected(3);
			break;
		default:
			break;
		}
	}
	
	/**
	 * 给Tab按钮设置图标和文字
	 */
//	private View getTabItemView(int index) {
//		View view = layoutInflater.inflate(R.layout.tab_item_view, null);
//		ImageView ivTab = (ImageView)view.findViewById(R.id.ivTab);
//		Button btnTab = (Button) view.findViewById(R.id.btnTab);
//		btnTab.setText(mTextviewArray[index]);
//		ivTab.setImageResource(mImageViewArray[index]);
//		return view;
//	}
}
