package com.mygame.pure.http;

import org.apache.http.HttpRequest;

import android.content.Context;
import android.text.TextUtils;

import com.mygame.pure.SelfDefineApplication; 



public class MyRequestHeader extends BaseReqeustHeader{ 

    public MyRequestHeader(Context context)
    {
        super(context); 
    }

    @Override
    public String getUid() {
		return null;
    }

    public static String getToken() {
		return SelfDefineApplication.getInstance().getUser().getToken();
    }

    @Override
    public String getLongitude() {
		return null;
    }

    @Override
    public String getLatitude() {
		return null;
    }

    @Override
    public String getPushToken() {
		return null;
    }

    @Override
    public String getSex() {
		return null;
    }

    @Override
    public String getLang() {
		return null;
    }
    
	public static String getUserName() {
		return SelfDefineApplication.getInstance().getUser().getUserName();
	}
    
    public static void addHeaders(HttpRequest request, AbsReqeustHeader headers) {
		if (!TextUtils.isEmpty(getUserName())) {
			request.addHeader("p", getUserName());
		}
		if (!TextUtils.isEmpty(getToken())) {
			request.addHeader("token", getToken());
		}
    }

}
