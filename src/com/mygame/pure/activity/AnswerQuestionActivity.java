package com.mygame.pure.activity;

import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mygame.pure.Question;
import com.mygame.pure.R;
import com.mygame.pure.bean.Response;
import com.mygame.pure.utils.AssetUtils;
import com.mygame.pure.utils.ToastHelper;

/**
 * 回答问题页面， 都是单选题
 * @author lenovo
 */
public class AnswerQuestionActivity extends BaseActivity {

	private static final int UPDATE_ANSWER = 10;
	private List<Question> list;
	private int currentAnswerIndex = 0;
	private TextView tvQuestion; 
	private RadioGroup rgroup;
	
	private OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(rgroup.getCheckedRadioButtonId() == -1){
				ToastHelper.ToastSht("没有选择答案", v.getContext());
				return;
			}
			rgroup.clearCheck();
			
			// 向服务器 发送请求，答完题号为list.get(curentAnswerIndex).getQuestionId()的题，答案是.
			// getRequest().. 或者最后一起发送。
			currentAnswerIndex ++;
			 if(currentAnswerIndex < list.size()) {
				 mHandler.sendEmptyMessage(UPDATE_ANSWER);
			 } else {
				 AlertDialog dlg = new AlertDialog.Builder(v.getContext())
				 .setMessage("已答题完毕")
				 .setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) { }
					})
				 .create();
				 dlg.show();
			 }
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// 请求所有的答题(包括当前答题题号)
		// 显示当前的答题
		// 提交保存当前答题的题号
		
		setContentView(R.layout.question_container);
		initView();
//		addRightBtn(getString(R.string.nextQ), listener);
		requestAllQuestionInfo();
	}
	
	private void initView() {
		tvQuestion = (TextView)findViewById(R.id.tvQuestion);
		rgroup = (RadioGroup)findViewById(R.id.rgroup);
	}

	public void requestAllQuestionInfo(){
		final ProgressDialog dialog = new ProgressDialog(this);
		dialog.setMessage("获取题库中...");
		String t = AssetUtils.getDataFromAssets(this, "question.txt");
//		getFinalHttp().post(Urls.question_info, new AjaxCallBack<String>(){
//
//			@Override
//			public void onStart() {
//				super.onStart();
				dialog.show();
//			}
//
//			@Override
//			public void onSuccess(String t) {
//				super.onSuccess(t);
				parseData(t);
				dialog.dismiss();
//			}
//
//			@Override
//			public void onFailure(Throwable t, int errorNo, String strMsg) {
//				super.onFailure(t, errorNo, strMsg);
//				dialog.dismiss();
//				ToastHelper.ToastSht(strMsg, getActivity());
//			}
//			
//		});
	}

	protected void parseData(String t) { 
		Response<List<Question>> response = new Gson().fromJson(t, 
				new TypeToken<Response<List<Question>>>(){
			
		}.getType());
		
		if(response.getResultAndTip(this)){
			list = response.getResponse();
			mHandler.sendEmptyMessage(UPDATE_ANSWER);
		}
	}
	

	Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case UPDATE_ANSWER:
					updateAnswer();
					break;
				default:
					break;
			}
		}
	};

	private void updateAnswer() {
		
		Question question = list.get(currentAnswerIndex);
		tvQuestion.setText(question.getQuestion());
		rgroup.removeAllViews();
		for(int i = 0; i < question.getQuestions().size(); i++){ 
			RadioButton btn = new RadioButton(this);
//			radios[i].setVisibility(View.VISIBLE);
			btn.setText(question.getQuestions().get(i));
			btn.setTextColor(Color.BLACK);
			rgroup.addView(btn);
		}
		
//		rgroup.getCheckedRadioButtonId() == -1
	}
}
