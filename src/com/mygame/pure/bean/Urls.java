package com.mygame.pure.bean;

public class Urls {

	public static final String SERVER_IP = "http://82.192.18.22";

	public static String question_info = SERVER_IP + "/quesitons/";
	public static String emailAndyanzhen = SERVER_IP
			+ "/webservice/emailAndyanzhen.asmx";// 获取邮箱验证码
	public static String Login_info = SERVER_IP + "/webservice/statistics.asmx";// 登录
	public static String checkEmail_info = SERVER_IP + "/XXXXXXXX";// 判断是否可以使用该email
	public static String checkEmail_istrue = SERVER_IP + "/XXXXXXXX";// 判断验证码是否正确
	public static String registered = SERVER_IP + "/XXXXXXXX";// 注册操作

}
