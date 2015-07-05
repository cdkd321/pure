package com.mygame.pure.dialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.mygame.pure.R;
import com.mygame.pure.activity.BaseActivity;
import com.mygame.pure.view.ActionSheetDialog;
import com.mygame.pure.view.ActionSheetDialog.OnSheetItemClickListener;
import com.mygame.pure.view.ActionSheetDialog.SheetItemColor;
import com.mygame.pure.view.AlertDialog;

public class DialogStyle extends BaseActivity implements OnClickListener {
	private Button btn_testdialog, btn_testdialog1, btn_testdialog2,
			btn_testdialog3, btn_testdialog4;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog);
		context = this;
		btn_testdialog = (Button) findViewById(R.id.btn_testdialog);
		btn_testdialog.setOnClickListener(this);
		btn_testdialog1 = (Button) findViewById(R.id.btn_testdialog1);
		btn_testdialog1.setOnClickListener(this);
		btn_testdialog2 = (Button) findViewById(R.id.btn_testdialog2);
		btn_testdialog2.setOnClickListener(this);
		btn_testdialog3 = (Button) findViewById(R.id.btn_testdialog3);
		btn_testdialog3.setOnClickListener(this);
		btn_testdialog4 = (Button) findViewById(R.id.btn_testdialog4);
		btn_testdialog4.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_testdialog:
			new ActionSheetDialog(getActivity())
					.builder()
					.setTitle("清空消息列表后，聊天记录依然保留，确定要清空消息列表？")
					.setCancelable(false)
					.setCanceledOnTouchOutside(false)
					.addSheetItem("清空消息列表", SheetItemColor.Red,
							new OnSheetItemClickListener() {
								@Override
								public void onClick(int which) {
									Toast.makeText(getActivity(), "清空消息列表",
											1000).show();
								}
							}).show();
			break;
		case R.id.btn_testdialog1:
			new ActionSheetDialog(getActivity())
					.builder()
					.setCancelable(false)
					.setCanceledOnTouchOutside(false)
					.addSheetItem("发送给好友", SheetItemColor.Blue,
							new OnSheetItemClickListener() {
								@Override
								public void onClick(int which) {

								}
							})
					.addSheetItem("转载到空间相册", SheetItemColor.Blue,
							new OnSheetItemClickListener() {
								@Override
								public void onClick(int which) {

								}
							})
					.addSheetItem("上传到群相册", SheetItemColor.Blue,
							new OnSheetItemClickListener() {
								@Override
								public void onClick(int which) {

								}
							})
					.addSheetItem("保存到手机", SheetItemColor.Blue,
							new OnSheetItemClickListener() {
								@Override
								public void onClick(int which) {

								}
							})
					.addSheetItem("收藏", SheetItemColor.Blue,
							new OnSheetItemClickListener() {
								@Override
								public void onClick(int which) {

								}
							})
					.addSheetItem("查看聊天图片", SheetItemColor.Blue,
							new OnSheetItemClickListener() {
								@Override
								public void onClick(int which) {

								}
							}).show();
			break;
		case R.id.btn_testdialog2:
			new ActionSheetDialog(getActivity())
					.builder()
					.setCancelable(false)
					.setCanceledOnTouchOutside(false)
					.addSheetItem("条目一", SheetItemColor.Blue,
							new OnSheetItemClickListener() {
								@Override
								public void onClick(int which) {
									Toast.makeText(getActivity(),
											"item" + which, Toast.LENGTH_SHORT)
											.show();
								}
							})
					.addSheetItem("条目二", SheetItemColor.Blue,
							new OnSheetItemClickListener() {
								@Override
								public void onClick(int which) {
									Toast.makeText(getActivity(),
											"item" + which, Toast.LENGTH_SHORT)
											.show();
								}
							})
					.addSheetItem("条目三", SheetItemColor.Blue,
							new OnSheetItemClickListener() {
								@Override
								public void onClick(int which) {
									Toast.makeText(getActivity(),
											"item" + which, Toast.LENGTH_SHORT)
											.show();
								}
							})
					.addSheetItem("条目四", SheetItemColor.Blue,
							new OnSheetItemClickListener() {
								@Override
								public void onClick(int which) {
									Toast.makeText(getActivity(),
											"item" + which, Toast.LENGTH_SHORT)
											.show();
								}
							})
					.addSheetItem("条目五", SheetItemColor.Blue,
							new OnSheetItemClickListener() {
								@Override
								public void onClick(int which) {
									Toast.makeText(getActivity(),
											"item" + which, Toast.LENGTH_SHORT)
											.show();
								}
							})
					.addSheetItem("条目六", SheetItemColor.Blue,
							new OnSheetItemClickListener() {
								@Override
								public void onClick(int which) {
									Toast.makeText(getActivity(),
											"item" + which, Toast.LENGTH_SHORT)
											.show();
								}
							})
					.addSheetItem("条目七", SheetItemColor.Blue,
							new OnSheetItemClickListener() {
								@Override
								public void onClick(int which) {
									Toast.makeText(getActivity(),
											"item" + which, Toast.LENGTH_SHORT)
											.show();
								}
							})
					.addSheetItem("条目八", SheetItemColor.Blue,
							new OnSheetItemClickListener() {
								@Override
								public void onClick(int which) {
									Toast.makeText(getActivity(),
											"item" + which, Toast.LENGTH_SHORT)
											.show();
								}
							})
					.addSheetItem("条目九", SheetItemColor.Blue,
							new OnSheetItemClickListener() {
								@Override
								public void onClick(int which) {
									Toast.makeText(getActivity(),
											"item" + which, Toast.LENGTH_SHORT)
											.show();
								}
							})
					.addSheetItem("条目十", SheetItemColor.Blue,
							new OnSheetItemClickListener() {
								@Override
								public void onClick(int which) {
									Toast.makeText(getActivity(),
											"item" + which, Toast.LENGTH_SHORT)
											.show();
								}
							}).show();
			break;
		case R.id.btn_testdialog3:
			new AlertDialog(context).builder().setTitle("退出当前账号")
					.setMsg("再连续登陆15天，就可变身为QQ达人。退出QQ可能会使你现有记录归零，确定退出？")
					.setPositiveButton("确认退出", new OnClickListener() {
						@Override
						public void onClick(View v) {

						}
					}).setNegativeButton("取消", new OnClickListener() {
						@Override
						public void onClick(View v) {

						}
					}).show();
			break;
		case R.id.btn_testdialog4:
			new AlertDialog(getActivity()).builder()
					.setMsg("你现在无法接收到新消息提醒。请到系统-设置-通知中开启消息提醒")
					.setNegativeButton("确定", new OnClickListener() {
						@Override
						public void onClick(View v) {

						}
					}).show();
			break;
		default:
			break;
		}
	}
}
