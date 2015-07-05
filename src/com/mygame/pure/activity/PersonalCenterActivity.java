package com.mygame.pure.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
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

import com.mygame.pure.R;
import com.mygame.pure.view.ActionSheetDialog;
import com.mygame.pure.view.ActionSheetDialog.OnSheetItemClickListener;
import com.mygame.pure.view.ActionSheetDialog.SheetItemColor;
import com.mygame.pure.view.CircleImageView;

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
	}

	public void initView() {
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
			finish();
			break;
		case R.id.back_btn:
			finish();
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
			mDateDisplay.setText(new StringBuilder().append(tempage)
					.append("岁"));
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
			} catch (Exception e) {
				Log.e("abc", "保存图片失败=" + e.toString());
				e.printStackTrace();
			}
		}
	}
}
