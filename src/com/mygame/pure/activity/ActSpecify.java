package com.mygame.pure.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.ab.view.level.AbLevelChartFactory;
import com.ab.view.level.AbLevelSeriesDataset;
import com.ab.view.level.AbLevelSeriesRenderer;
import com.ab.view.level.AbLevelView;
import com.mygame.pure.R;

/**
 * 关于界面
 * 
 * @author tom
 */
public class ActSpecify extends BaseActivity implements OnClickListener {
	private Float progress;
	private String checkState;
	int textlevelIndex;
	private int checkType;
	private String textDesc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_specify_2);
		addBackImage(R.drawable.back_pressed, null);
		progress = getIntent().getFloatExtra("progress", 20);
		checkType = getIntent().getIntExtra("checkType", 0);
		if (checkType == 0) {
			if (progress < 30) {
				textlevelIndex = 0;
				// 当前值描述
				textDesc = "干燥";
			} else if (progress > 38) {
				textlevelIndex = 2;
				// 当前值描述
				textDesc = "湿润";
			} else {
				textlevelIndex = 1;
				// 当前值描述
				textDesc = "正常";
			}
		}
		addRightImage(R.drawable.bg_btn_share, new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// 弹出分享按钮，分享到社交网络
				showShare();
			}
		});
		// 等级图形宽
		int width = 300;
		// 等级图形高
		int height = 200;
		// 各等级段的颜色
		int[] color = new int[] { Color.rgb(223, 117, 8),
				Color.rgb(35, 196, 125), Color.rgb(55, 162, 236) };
		// 假如 每段的百分比 2 3 2 1 1 1
		float[] part = new float[] { 3.75f, 2.5f, 3.75f };
		// 各等级段的值
		float[] partValue = new float[] { 20.0f, 35.0f, 45.0f };

		// 当前值的等级

		// 当前值文字大小
		int textLevelSize = 30;
		// 当前值文字与顶部的距离
		int marginTop = 30;
		// 指示三角形的宽度
		int arrowWidth = 20;
		// 指示三角形的高度
		int arrowHeight = 10;
		// 等级条的高度
		int levelHeight = 20;
		// 指示三角形与其他间距
		int arrowMarginTop = 10;
		// 等级坐标文字大小
		int partTextSize = 15;
		// 等级说明文字大小
		int textDescSize = 22;
		// 当前值
		String textValue = progress + "%";
		// 要显示图形的View
		LinearLayout chartLayout = (LinearLayout) findViewById(R.id.chartLayout);

		AbLevelSeriesRenderer renderer = new AbLevelSeriesRenderer();

		renderer.setWidth(width);
		renderer.setHeight(height);
		renderer.setColor(color);
		renderer.setPart(part);
		renderer.setPartValue(partValue);
		renderer.setTextValue(textValue);
		renderer.setTextDesc(textDesc);
		renderer.setTextlevelIndex(textlevelIndex);
		renderer.setTextLevelSize(textLevelSize);
		renderer.setMarginTop(marginTop);
		renderer.setArrowWidth(arrowWidth);
		renderer.setArrowHeight(arrowHeight);
		renderer.setArrowMarginTop(arrowMarginTop);
		renderer.setLevelHeight(levelHeight);
		renderer.setPartTextSize(partTextSize);
		renderer.setTextDescSize(textDescSize);
		renderer.setTextRectWidth(120);
		renderer.setTextRectHeight(50);

		AbLevelSeriesDataset mDataset = new AbLevelSeriesDataset();
		AbLevelView mAbLevelView = AbLevelChartFactory.getLevelChartView(this,
				mDataset, renderer);
		setTitle("详情");
		chartLayout.addView(mAbLevelView, new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	}

	private void showShare() {
		ShareSDK.initSDK(this);
		OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();

		// 分享时Notification的图标和文字 2.5.9以后的版本不调用此方法
		// oks.setNotification(R.drawable.ic_launcher,
		// getString(R.string.app_name));
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle(getString(R.string.share));
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl("http://sharesdk.cn");
		// text是分享文本，所有平台都需要这个字段
		oks.setText("我是分享文本");
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		oks.setImagePath("/sdcard/test.jpg");// 确保SDcard下面存在此张图片
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl("http://sharesdk.cn");
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		oks.setComment("我是测试评论文本");
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite(getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl("http://sharesdk.cn");

		// 启动分享GUI
		oks.show(this);
	}

	@Override
	public void onClick(View v) {

	}

}
