package com.mygame.pure;

import java.util.ArrayList;
import java.util.List;

import com.mygame.pure.adapter.FriendListViewAdapter;
import com.mygame.pure.struct.StructFriendInfo;

import android.os.Bundle;
import android.widget.ListView;

public class FriendListActivity extends BaseActivity{
	
	ListView mListViewFriendList;
	FriendListViewAdapter mFriendListViewAdapter;
	List<StructFriendInfo> lpStructFriendInfo = new ArrayList<StructFriendInfo>(); 
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_friend_list);
		mListViewFriendList = (ListView) findViewById(R.id.ListViewFriendList);
		mFriendListViewAdapter = new FriendListViewAdapter(this, lpStructFriendInfo);
		mListViewFriendList.setAdapter(mFriendListViewAdapter);
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
}