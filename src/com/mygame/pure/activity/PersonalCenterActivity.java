package com.mygame.pure.activity;

import java.io.File;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mygame.pure.R;
import com.mygame.pure.view.CircleImageView;

public class PersonalCenterActivity extends FragmentActivity {
	private RelativeLayout gender_layout;
	private RelativeLayout cancer_Layout;
	private RelativeLayout ageLayout;
	private RelativeLayout pwdLayout;
	private ImageButton back_btn;
	private TextView age_text;
	private TextView sex_text;
	private TextView cancerName;
	private SharedPreferences share;
	private Editor edit;

	private CircleImageView personal_img;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_center_layout);

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

}
