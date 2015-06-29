package com.mygame.pure.activity;

import com.mygame.pure.R;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 关于界面
 * @author tom
 */
public class ActSpecify extends BaseActivity implements OnClickListener {
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_specify_2);
		addBackImage(R.drawable.back_pressed, null);
		
		addRightBtn(R.drawable.bg_btn_share, new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				 // 弹出分享按钮，分享到社交网络
			}
			
		});
		
		setTitle("详情");
	}

	@Override
	public void onClick(View v) {
		 
	}
 
}
