package com.mygame.pure.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.soap.AbSoapListener;
import com.ab.soap.AbSoapParams;
import com.ab.soap.AbSoapUtil;
import com.mygame.pure.R;
import com.mygame.pure.core.MicroRecruitSettings;
import com.mygame.pure.utils.AbDateUtil;
import com.mygame.pure.view.ActionSheetDialog;
import com.mygame.pure.view.ActionSheetDialog.OnSheetItemClickListener;
import com.mygame.pure.view.ActionSheetDialog.SheetItemColor;
import com.mygame.pure.view.AlertDialog;
import com.squareup.picasso.Picasso;

public class PersonalCenterActivity extends FragmentActivity implements
		OnClickListener {
	private RelativeLayout cancer_Layout;

	private ImageButton back_btn;
	private TextView age_text;
	private TextView sex_text;
	private TextView cancerName;
	private SharedPreferences share;
	private Editor edit;
	public static final String IMAGE_UNSPECIFIED = "image/*";
	private ImageView personal_img;
	private final String mPageName = "PersonalCenterActivity";
	private View mAvatarView;
	/* ������ʶ�������๦�ܵ�activity */
	private static final int CAMERA_WITH_DATA = 3023;
	/* ������ʶ����gallery��activity */
	private static final int PHOTO_PICKED_WITH_DATA = 3021;
	/* ������ʶ����ü�ͼƬ���activity */
	private static final int CAMERA_CROP_DATA = 3022;
	private File mCurrentPhotoFile;
	private String mFileName;
	private File PHOTO_DIR;
	private RelativeLayout uploadmyhenad;
	private Context context;
	private RelativeLayout rel_nickname, gender_layout, ageLayout, pwdLayout;
	private int mYear;
	private int mMonth;
	private int mDay;
	int tempage;
	private TextView mDateDisplay;
	private ImageButton save_btn;
	private TextView skin;
	private EditText mynick;
	private MicroRecruitSettings settings;
	private AbSoapUtil mAbSoapUtil = null;
	private Bitmap bitmap;
	private TextView tvExit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTheme(R.style.AppBaseTheme);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.personal_center_layout);
		context = this;
		initView();
		setUpView();
		doPostMessage();
	}

	public void initView() {
		settings = new MicroRecruitSettings(context);
		mAbSoapUtil = AbSoapUtil.getInstance(this);
		mAbSoapUtil.setTimeout(10000);
		tvExit = (TextView) findViewById(R.id.tvExit);
		tvExit.setOnClickListener(this);
		mynick = (EditText) findViewById(R.id.mynick);
		sex_text = (TextView) findViewById(R.id.sex_text);
		back_btn = (ImageButton) findViewById(R.id.back_btn);
		uploadmyhenad = (RelativeLayout) findViewById(R.id.uploadmyhenad);
		rel_nickname = (RelativeLayout) findViewById(R.id.rel_nickname);
		gender_layout = (RelativeLayout) findViewById(R.id.gender_layout);
		ageLayout = (RelativeLayout) findViewById(R.id.ageLayout);
		pwdLayout = (RelativeLayout) findViewById(R.id.pwdLayout);
		mDateDisplay = (TextView) findViewById(R.id.age_text);
		save_btn = (ImageButton) findViewById(R.id.save_btn);
		skin = (TextView) findViewById(R.id.skin);
		personal_img = (ImageView) findViewById(R.id.personal_img);

		save_btn.setOnClickListener(this);
		uploadmyhenad.setOnClickListener(this);
		rel_nickname.setOnClickListener(this);
		gender_layout.setOnClickListener(this);
		ageLayout.setOnClickListener(this);
		pwdLayout.setOnClickListener(this);
		back_btn.setOnClickListener(this);
	}

	public void setUpView() {
		mYear = 1989;
		mMonth = 12;
		mDay = 1;
		// 显示当前时间
		updateDisplay();
	}

	/**
	 * ��������ȡ
	 */
	private void doPickPhotoAction() {
		String status = Environment.getExternalStorageState();
		// �ж��Ƿ���SD��,�����sd������sd����˵��û��sd��ֱ��ת��ΪͼƬ
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			doTakePhoto();
		} else {
			// AbToastUtil.showToast(PersonalCenterActivity.this, "û�п��õĴ洢��");
		}
	}

	/**
	 * ���ջ�ȡͼƬ
	 */
	protected void doTakePhoto() {
		try {
			mFileName = System.currentTimeMillis() + ".jpg";
			mCurrentPhotoFile = new File(PHOTO_DIR, mFileName);
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
			intent.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(mCurrentPhotoFile));
			startActivityForResult(intent, CAMERA_WITH_DATA);
		} catch (Exception e) {
			// AbToastUtil.showToast(PersonalCenterActivity.this,
			// "δ�ҵ�ϵͳ������");
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.uploadmyhenad:
			// 上传头像
			new ActionSheetDialog(context)
					.builder()
					.setCancelable(false)
					.setCanceledOnTouchOutside(false)
					.addSheetItem("拍照", SheetItemColor.Blue,
							new OnSheetItemClickListener() {
								@Override
								public void onClick(int which) {
									Intent intent = new Intent(
											MediaStore.ACTION_IMAGE_CAPTURE);
									// 下面这句指定调用相机拍照后的照片存储的路径
									intent.putExtra(
											MediaStore.EXTRA_OUTPUT,
											Uri.fromFile(new File(
													Environment
															.getExternalStorageDirectory(),
													"xiaoma.png")));
									startActivityForResult(intent, 2);
								}
							})
					.addSheetItem("从相册中选择", SheetItemColor.Blue,
							new OnSheetItemClickListener() {
								@Override
								public void onClick(int which) {
									Intent intent2 = new Intent(
											Intent.ACTION_PICK, null);
									intent2.setDataAndType(
											MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
											IMAGE_UNSPECIFIED);
									startActivityForResult(intent2, 1);
								}
							}).show();
			break;
		case R.id.rel_nickname:
			break;
		case R.id.gender_layout:
			new ActionSheetDialog(context)
					.builder()
					.setCancelable(false)
					.setCanceledOnTouchOutside(false)
					.addSheetItem("男", SheetItemColor.Blue,
							new OnSheetItemClickListener() {
								@Override
								public void onClick(int which) {
									sex_text.setText("男");
								}
							})
					.addSheetItem("女", SheetItemColor.Blue,
							new OnSheetItemClickListener() {
								@Override
								public void onClick(int which) {
									sex_text.setText("女");
								}
							}).show();
			break;
		case R.id.ageLayout:
			showDialog(0);
			break;
		case R.id.pwdLayout:
			new ActionSheetDialog(context)
					.builder()
					.setCancelable(false)
					.setCanceledOnTouchOutside(false)
					.addSheetItem("敏感性皮肤", SheetItemColor.Blue,
							new OnSheetItemClickListener() {
								@Override
								public void onClick(int which) {
									skin.setText("敏感性皮肤");
								}
							})
					.addSheetItem("混合皮肤", SheetItemColor.Blue,
							new OnSheetItemClickListener() {
								@Override
								public void onClick(int which) {
									skin.setText("混合皮肤");
								}
							})
					.addSheetItem("油性皮肤", SheetItemColor.Blue,
							new OnSheetItemClickListener() {
								@Override
								public void onClick(int which) {
									skin.setText("油性皮肤");
								}
							})
					.addSheetItem("干性皮肤", SheetItemColor.Blue,
							new OnSheetItemClickListener() {
								@Override
								public void onClick(int which) {
									skin.setText("干性皮肤");
								}
							})
					.addSheetItem("中性皮肤", SheetItemColor.Blue,
							new OnSheetItemClickListener() {
								@Override
								public void onClick(int which) {
									skin.setText("中性皮肤");
								}
							}).show();
			break;
		case R.id.save_btn:
			// 更改用户资料
			updateInfo();
			finish();
			break;
		case R.id.back_btn:
			finish();
			break;
		case R.id.tvExit:
			// 退出登录
			new AlertDialog(context).builder().setTitle("退出当前账号")
					.setMsg("退出登录后需要重新登录，确定退出？")
					.setPositiveButton("确认退出", new OnClickListener() {
						@Override
						public void onClick(View v) {
							settings.USER_NAME.setValue("");// 重置用户名
							Intent intent = new Intent();
							intent.setClass(PersonalCenterActivity.this,
									ActLogin.class);
							startActivity(intent);
							finish();
						}
					}).setNegativeButton("取消", new OnClickListener() {
						@Override
						public void onClick(View v) {

						}
					}).show();
			break;
		default:
			break;
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 0:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		}
		return null;
	}

	private void updateDisplay() {
		if (tempage <= 0) {
			mDateDisplay.setHint("年龄");
		} else {
			mDateDisplay.setTextColor(Color.BLACK);
			mDateDisplay.setText(mYear + "-" + mMonth + "-" + mDay);
		}
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			final Calendar c = Calendar.getInstance();
			mYear = year;
			tempage = c.get(Calendar.YEAR) - mYear;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDisplay();
		}
	};

	/**
	 * 回调
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 0) {
			return;
		}
		if (requestCode == 1) {
			startPhotoZoom(data.getData());
		}
		if (requestCode == 2) {
			/**
			 * 当选择的图片路径不为空的话,在获取到图片的路径
			 */
			File temp = new File(Environment.getExternalStorageDirectory()
					+ "/xiaoma.png");
			startPhotoZoom(Uri.fromFile(temp));
		}
		if (requestCode == 3) {
			/**
			 * 非空判断大家一定要验证，如果不验证的话， 在剪裁之后如果发现不满意，要重新裁剪，丢弃
			 * 当前功能时，会报NullException，小马只 在这个地方加下，大家可以根据不同情况在合适的 地方做判断处理类似情况
			 * 
			 */
			if (data != null) {
				setPicToView(data);
			}
		}

	}

	public void startPhotoZoom(Uri uri) {
		/*
		 */
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 320);
		intent.putExtra("outputY", 320);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 3);
	}

	/**
	 * 保存裁剪之后的图片数据
	 * 
	 * @param picdata
	 */
	public void setPicToView(Intent picdata) {
		Bundle extras = picdata.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			bitmap = extras.getParcelable("data");
			Drawable drawable = new BitmapDrawable(photo);
			personal_img.setImageBitmap(photo);
			// loader.display(headImage, uri)
			// 执行保存操作
			try {
				File f = new File(Environment.getExternalStorageDirectory()
						+ "/xiaoma.png");
				if (!f.exists()) {
					f.createNewFile();
				}
				FileOutputStream out = new FileOutputStream(f);
				Bitmap photo1 = extras.getParcelable("data");
				photo1.compress(Bitmap.CompressFormat.PNG, 100, out);
				out.flush();
				out.close();
				String imagePath = f.getAbsolutePath();
				// 上传头像
				getPostmyHead();
			} catch (Exception e) {
				Log.e("abc", "保存图片失败=" + e.toString());
				e.printStackTrace();
			}
		}
	}

	public void updateInfo() {
		int sex = 6;
		int fuzhi = 9;
		double ar = 0;
		// soap处理
		// 获取用户的相关信息
		if (sex_text.getText().toString().equals("男")) {
			sex = 1;
		} else if (sex_text.getText().toString().equals("女")) {
			sex = 2;
		}
		if (skin.getText().toString().equals("中性皮肤")) {
			fuzhi = 1;
		} else if (skin.getText().toString().equals("干性皮肤")) {
			fuzhi = 2;
		} else if (skin.getText().toString().equals("油性皮肤")) {
			fuzhi = 3;
		} else if (skin.getText().toString().equals("混合皮肤")) {
			fuzhi = 4;
		} else if (skin.getText().toString().equals("敏感性皮肤")) {
			fuzhi = 5;
		}

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date1 = df.parse(mDateDisplay.getText().toString());
			Calendar cal = Calendar.getInstance();
			cal.setTime(date1);
			long time = cal.getTimeInMillis();
			ar = time / 1000d;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String urlString = "http://miliapp.ebms.cn/webservice/member.asmx?op=Update";
		String nameSpace = "http://tempuri.org/";
		String methodName = "Update";
		AbSoapParams params = new AbSoapParams();
		params.put("user1", "APP");
		params.put("pass1", "4C85AF5AD4D0CC9349A8A468C38F292E");
		params.put("nicheng", mynick.getText().toString());
		params.put("sex", sex + "");
		params.put("birthday", ar + "");
		// ...
		//params.put("fuzhi", skin.getText().toString());
		params.put("fuzhi", fuzhi + "");
		params.put("username", settings.USER_NAME.getValue().toString());
		mAbSoapUtil.call(urlString, nameSpace, methodName, params,
				new AbSoapListener() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						if (arg1 != null) {
							String[] a = arg1.split("=");
							String[] b = a[1].split(";");
							if (b[0].equals("1")) {
								Toast.makeText(getApplicationContext(), "成功", 1)
										.show();
							} else if (b[0].equals("0")) {
								Toast.makeText(getApplicationContext(), "失败", 1)
										.show();
							}
						}
					}

					@Override
					public void onFailure(int arg0, String arg1, Throwable arg2) {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(), "请求失败" + arg1,
								1).show();
					}
				});

	}

	public void doPostMessage() {
		// 加载个人信息
		String urlString3 = "http://miliapp.ebms.cn/webservice/member.asmx?op=GetListByUserName";
		String nameSpace3 = "http://tempuri.org/";
		String methodName3 = "GetListByUserName";
		AbSoapParams params3 = new AbSoapParams();
		params3.put("user1", "APP");
		params3.put("pass1", "4C85AF5AD4D0CC9349A8A468C38F292E");
		params3.put("username", settings.USER_NAME.getValue());

		mAbSoapUtil.call(urlString3, nameSpace3, methodName3, params3,
				new AbSoapListener() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						Log.i("arg1", arg1);
						// TODO Auto-generated method stub
						if (arg1.indexOf("Nicheng=") != -1) {
							String[] a = arg1.split("Nicheng=");
							String[] b = a[1].split(";");
							mynick.setText(b[0]);
						}
						if (arg1.indexOf("Touxiang=") != -1) {
							String[] a1 = arg1.split("Touxiang=");
							String[] b1 = a1[1].split(";");
							Picasso.with(context)
									.load("http://miliapp.ebms.cn/" + b1[0])
									.placeholder(R.drawable.hcy_icon)
									.error(R.drawable.hcy_icon)
									.into(personal_img);
						}
						if (arg1.indexOf("Sex=") != -1) {
							String[] a2 = arg1.split("Sex=");
							String[] b2 = a2[1].split(";");
							if (b2[0].equals("1")) {
								sex_text.setText("男");
							} else if (b2[0].equals("2")) {
								sex_text.setText("女");
							}
						}
						if (arg1.indexOf("Birthday=") != -1) {
							String[] a3 = arg1.split("Birthday=");
							String[] b3 = a3[1].split(";");
							Double ms = Double.parseDouble(b3[0]) * 1000d;
							long ms1 = new Double(ms).longValue();

							mDateDisplay.setText(AbDateUtil.getStringByFormat(
									ms1, "yyyy-MM-dd"));
						}

						// AbDialogUtil.showAlertDialog(MoreAct.this, "���ؽ��",
						// arg1, new AbDialogOnClickListener() {
						//
						// @Override
						// public void onNegativeClick() {
						// // TODO Auto-generated method
						// // stub
						//
						// }
						//
						// @Override
						// public void onPositiveClick() {
						// // TODO Auto-generated method
						// // stub
						//
						// }
						//
						// });
					}

					@Override
					public void onFailure(int arg0, String arg1, Throwable arg2) {
						// TODO Auto-generated method stub
						// Toast.makeText(getApplicationContext(), "请求失败" +
						// arg1,
						// 1).show();
					}
				});

	}

	/**
	 * 通过Base32将Bitmap转换成Base64字符串
	 * 
	 * @param bit
	 * @return
	 */
	public String Bitmap2StrByBase64(Bitmap bit) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bit.compress(CompressFormat.JPEG, 40, bos);// 参数100表示不压缩
		byte[] bytes = bos.toByteArray();
		return Base64.encodeToString(bytes, Base64.DEFAULT);
	}

	// 上传头像

	public void getPostmyHead() {
		// 加载个人信息
		// 获取用户的相关信息
		String urlString = "http://miliapp.ebms.cn/webservice/member.asmx?op=UpdateTouxiang";
		String nameSpace = "http://tempuri.org/";
		String methodName = "UpdateTouxiang";
		AbSoapParams params = new AbSoapParams();
		params.put("user1", "APP");
		params.put("pass1", "4C85AF5AD4D0CC9349A8A468C38F292E");
		params.put("username", settings.USER_NAME.getValue().toString());
		params.put("b", Bitmap2StrByBase64(bitmap));
		mAbSoapUtil.call(urlString, nameSpace, methodName, params,
				new AbSoapListener() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						if (arg1 != null) {
							String[] a = arg1.split("=");
							String[] b = a[1].split(";");
							if (b[0].equals("1")) {
								Toast.makeText(getApplicationContext(), "成功", 1)
										.show();
							} else if (b[0].equals("0")) {
								Toast.makeText(getApplicationContext(), "失败", 1)
										.show();
							} else if (b[0].equals("-2")) {
								Toast.makeText(getApplicationContext(),
										"头像上传失败", 1).show();
							}
						}
					}

					@Override
					public void onFailure(int arg0, String arg1, Throwable arg2) {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(), "请求失败" + arg1,
								1).show();
					}
				});

	}
}
