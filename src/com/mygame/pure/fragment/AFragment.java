package com.mygame.pure.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.fragment.AbAlertDialogFragment.AbDialogOnClickListener;
import com.ab.soap.AbSoapListener;
import com.ab.soap.AbSoapParams;
import com.ab.soap.AbSoapUtil;
import com.ab.util.AbDialogUtil;
import com.mygame.pure.R;
import com.mygame.pure.activity.MoreAct;

@SuppressLint("NewApi")
public class AFragment extends BaseFragment {

	private TextView tv_a;
	private Context context;
	private AbSoapUtil mAbSoapUtil = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View root = inflater.inflate(R.layout.a, container, false);
		dopost();
		return root;
	}

	public void dopost() {
		mAbSoapUtil = AbSoapUtil.getInstance(activity);
		mAbSoapUtil.setTimeout(10000);
		// getInfo
		// getClickUp();
		// getAddCommon();
		// getnewContent();
		// GetNewInfo();
	}

	// 通过id获得到所选项中所有的信息
	public void getInfo() {
		String urlString3 = "http://miliapp.ebms.cn/webservice/news.asmx?op=GetListByType";
		String nameSpace3 = "http://tempuri.org/";
		String methodName3 = "GetListByType";
		AbSoapParams params3 = new AbSoapParams();
		params3.put("user1", "APP");
		params3.put("pass1", "4C85AF5AD4D0CC9349A8A468C38F292E");
		params3.put("appid", "3");
		params3.put("typeid", "1");// id,分别为1,2,3,4
		mAbSoapUtil.call(urlString3, nameSpace3, methodName3, params3,
				new AbSoapListener() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						@SuppressWarnings("unused")
						String arString = arg1;
						AbDialogUtil.showAlertDialog(getActivity(), "���ؽ��",
								arg1, new AbDialogOnClickListener() {

									@Override
									public void onNegativeClick() {
										// TODO Auto-generated method
										// stub

									}

									@Override
									public void onPositiveClick() {
										// TODO Auto-generated method
										// stub

									}
								});
					}

					@Override
					public void onFailure(int arg0, String arg1, Throwable arg2) {
						Toast.makeText(getActivity(), arg1, 1).show();
					}
				});
	}

	// 点赞(返回:adddianzhannumresult=1成功)
	public void getClickUp() {
		String urlString3 = "http://miliapp.ebms.cn/webservice/news.asmx?op=AddDianzhanNum";
		String nameSpace3 = "http://tempuri.org/";
		String methodName3 = "AddDianzhanNum";
		AbSoapParams params3 = new AbSoapParams();
		params3.put("user1", "APP");
		params3.put("pass1", "4C85AF5AD4D0CC9349A8A468C38F292E");
		params3.put("id", "39");
		mAbSoapUtil.call(urlString3, nameSpace3, methodName3, params3,
				new AbSoapListener() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						@SuppressWarnings("unused")
						String arString = arg1;
						AbDialogUtil.showAlertDialog(getActivity(), "���ؽ��",
								arg1, new AbDialogOnClickListener() {

									@Override
									public void onNegativeClick() {
										// TODO Auto-generated method
										// stub

									}

									@Override
									public void onPositiveClick() {
										// TODO Auto-generated method
										// stub

									}
								});
					}

					@Override
					public void onFailure(int arg0, String arg1, Throwable arg2) {
						Toast.makeText(getActivity(), arg1, 1).show();
					}
				});
	}

	// 浏览次数+1(返回:=1成功)
	public void getAddCommon() {
		String urlString3 = "http://miliapp.ebms.cn/webservice/news.asmx?op=AddLiulanNum";
		String nameSpace3 = "http://tempuri.org/";
		String methodName3 = "AddLiulanNum";
		AbSoapParams params3 = new AbSoapParams();
		params3.put("user1", "APP");
		params3.put("pass1", "4C85AF5AD4D0CC9349A8A468C38F292E");
		params3.put("id", "39");
		mAbSoapUtil.call(urlString3, nameSpace3, methodName3, params3,
				new AbSoapListener() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						@SuppressWarnings("unused")
						String arString = arg1;
						AbDialogUtil.showAlertDialog(getActivity(), "���ؽ��",
								arg1, new AbDialogOnClickListener() {

									@Override
									public void onNegativeClick() {
										// TODO Auto-generated method
										// stub

									}

									@Override
									public void onPositiveClick() {
										// TODO Auto-generated method
										// stub

									}
								});
					}

					@Override
					public void onFailure(int arg0, String arg1, Throwable arg2) {
						Toast.makeText(getActivity(), arg1, 1).show();
					}
				});
	}

	// 通过ID获得对象,返回DataSet
	public void getnewContent() {
		String urlString3 = "http://miliapp.ebms.cn/webservice/news.asmx?op=GetNewContent";
		String nameSpace3 = "http://tempuri.org/";
		String methodName3 = "GetNewContent";
		AbSoapParams params3 = new AbSoapParams();
		params3.put("user1", "APP");
		params3.put("pass1", "4C85AF5AD4D0CC9349A8A468C38F292E");
		params3.put("id", "39");
		mAbSoapUtil.call(urlString3, nameSpace3, methodName3, params3,
				new AbSoapListener() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						@SuppressWarnings("unused")
						String arString = arg1;
						AbDialogUtil.showAlertDialog(getActivity(), "���ؽ��",
								arg1, new AbDialogOnClickListener() {

									@Override
									public void onNegativeClick() {
										// TODO Auto-generated method
										// stub

									}

									@Override
									public void onPositiveClick() {
										// TODO Auto-generated method
										// stub

									}
								});
					}

					@Override
					public void onFailure(int arg0, String arg1, Throwable arg2) {
						Toast.makeText(getActivity(), arg1, 1).show();
					}
				});
	}

	// 通过ID获得单个的具体信息,包含图片,点赞数,浏览数等等
	public void GetNewInfo() {
		String urlString3 = "http://miliapp.ebms.cn/webservice/news.asmx?op=GetNewInfo";
		String nameSpace3 = "http://tempuri.org/";
		String methodName3 = "GetNewInfo";
		AbSoapParams params3 = new AbSoapParams();
		params3.put("user1", "APP");
		params3.put("pass1", "4C85AF5AD4D0CC9349A8A468C38F292E");
		params3.put("id", "39");
		mAbSoapUtil.call(urlString3, nameSpace3, methodName3, params3,
				new AbSoapListener() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						@SuppressWarnings("unused")
						String arString = arg1;
						AbDialogUtil.showAlertDialog(getActivity(), "���ؽ��",
								arg1, new AbDialogOnClickListener() {

									@Override
									public void onNegativeClick() {
										// TODO Auto-generated method
										// stub

									}

									@Override
									public void onPositiveClick() {
										// TODO Auto-generated method
										// stub
									}
								});
					}

					@Override
					public void onFailure(int arg0, String arg1, Throwable arg2) {
						Toast.makeText(getActivity(), arg1, 1).show();
					}
				});
	}
}
