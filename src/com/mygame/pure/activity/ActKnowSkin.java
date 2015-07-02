package com.mygame.pure.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.mygame.pure.R;

/**
 * 关于界面
 * @author tom
 */
public class ActKnowSkin extends BaseActivity implements OnClickListener {
	 
	private View layout1;
	private View layout2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_aknow_of_skills);
		addBackImage(R.drawable.back_pressed, null);
		 
		setTitle("护肤知识");
		
		layout1 = findViewById(R.id.aknow_of_water);
		layout2 = findViewById(R.id.aknow_of_skin);
		layout1.setVisibility(View.VISIBLE);
		layout2.setVisibility(View.GONE);
		
		RadioGroup rgroup = (RadioGroup)findViewById(R.id.rGroup);
		rgroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				 switch (checkedId) {
				case R.id.rbLeft:
					layout1.setVisibility(View.VISIBLE);
					layout2.setVisibility(View.GONE);
					break;
				case R.id.rbRight:
					layout1.setVisibility(View.GONE);
					layout2.setVisibility(View.VISIBLE);
					break;

				default:
					break;
				}
			}
		});
	}
	 
	@Override
	public void onClick(View v) {
		 
	}
 
}
