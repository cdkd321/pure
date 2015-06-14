package com.mygame.pure.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.Gravity;

import com.mygame.pure.utils.DeviceConfiger;
import com.mygame.pure.utils.MaterialUtils;
import com.mygame.pure.utils.MaterialUtils.Location;
import com.mygame.pure.view.fonticon.IcomoonIcon;
import com.mygame.pure.view.fonticon.IconicFontDrawable;

public class AppMaterial {
    // 52b370 绿色
    // e64646 红色
    // 4fb4e9 蓝色

    // APP 色值
    public static String NUMBER_1 = "#4fb4e9";
    public static String NUMBER_2 = "#e1f2ff";
    public static String NUMBER_3 = "#ffa200";
    public static String NUMBER_4 = "#ff7e00";
    public static String NUMBER_5 = "#ff3000";
    public static String NUMBER_6 = "#555555";
    public static String NUMBER_7 = "#999999";
    public static String NUMBER_8 = "#ebebeb";
    public static String NUMBER_9 = "#fff2cb";
    public static String NUMBER_10 = "#f2f2f2";
    public static String NUMBER_11 = "#c0d4df";
    public static String NUMBER_12 = "#64cc40";
    public static String NUMBER_13 = "#d0d0d0";
    public static String NUMBER_14 = "#ff90aa";
    public static String NUMBER_15 = "#39cea2";
    public static String NUMBER_16 = "#31a5e1";
    public static String NUMBER_17 = "#fab115";
    public static String NUMBER_18 = "#bf65ee";
    public static String NUMBER_19 = "#4d6aff";

    public static int NUMBER_1_INT = Color.parseColor(NUMBER_1);
    public static int NUMBER_2_INT = Color.parseColor(NUMBER_2);
    public static int NUMBER_3_INT = Color.parseColor(NUMBER_3);
    public static int NUMBER_4_INT = Color.parseColor(NUMBER_4);
    public static int NUMBER_5_INT = Color.parseColor(NUMBER_5);
    public static int NUMBER_6_INT = Color.parseColor(NUMBER_6);
    public static int NUMBER_7_INT = Color.parseColor(NUMBER_7);
    public static int NUMBER_8_INT = Color.parseColor(NUMBER_8);
    public static int NUMBER_9_INT = Color.parseColor(NUMBER_9);
    public static int NUMBER_10_INT = Color.parseColor(NUMBER_10);
    public static int NUMBER_11_INT = Color.parseColor(NUMBER_11);
    public static int NUMBER_12_INT = Color.parseColor(NUMBER_12);
    public static int NUMBER_13_INT = Color.parseColor(NUMBER_13);
    public static int NUMBER_14_INT = Color.parseColor(NUMBER_14);
    public static int NUMBER_15_INT = Color.parseColor(NUMBER_15);
    public static int NUMBER_16_INT = Color.parseColor(NUMBER_16);
    public static int NUMBER_17_INT = Color.parseColor(NUMBER_17);
    public static int NUMBER_18_INT = Color.parseColor(NUMBER_18);
    public static int NUMBER_19_INT = Color.parseColor(NUMBER_19);

    public static Context sContext = null;

    public static void initMaterials(Context context, int color) {
        sContext = context;
//        NUMBER_1_INT = color;
    }

    // /** checkbox 选择框 图片 */
    // public static Drawable CHECK_BOX_DRAWABLE() {
    // return MaterialUtils.createCheckBoxDrawable(sContext, APP_BASE_COLOR);
    // }
    //
    // /** 分享ICON */
    // public static Drawable SHARE_ICON_DRAWABLE() {
    // int w = DeviceConfiger.dp2px(13);
    // int h = DeviceConfiger.dp2px(13);
    // IconicFontDrawable selectDrawable = new IconicFontDrawable(sContext);
    // selectDrawable.setIcon(IconfontIcon.FEN_XIANG);
    // selectDrawable.setIconColor(APP_BASE_COLOR);
    // selectDrawable.setIntrinsicWidth(w);
    // selectDrawable.setIntrinsicHeight(h);
    // return selectDrawable;
    // }
    // /** 向下箭头,选中 向上 */
    // public static Drawable DOWN_TRIANGLE_DRAWABLE() {
    // return MaterialUtils.createDownTriangleCheckedDrawable(sContext,
    // Color.BLACK, APP_BASE_COLOR);
    // }
    //
    // /** 向下箭头 */
    // public static Drawable TRIANGLE_CHECKED_DRAWABLE() {
    // return MaterialUtils.createTriangleCheckedDrawable(sContext, Color.BLACK,
    // APP_BASE_COLOR);
    // }
    //
    //
    // /** 收藏按钮 */
    // public static Drawable MARK_DRAWABLE() {
    // return MaterialUtils.createMarkDrawble(sContext, APP_BASE_COLOR);
    // }
    //
    // /** 左边带边框的图片背景 */
    // public static Drawable LEFT_DIVIDER_DRAWABLE(int dividerWidth) {
    // int hight = DeviceConfiger.dp2px(dividerWidth);
    // DividerDrawable selectDrawable = new DividerDrawable();
    // selectDrawable.setDividerColor(APP_BASE_COLOR);
    // selectDrawable.setDividerOrientaion(DividerOrientation.LEFT);
    // selectDrawable.setStrokeWidth(hight);
    // return selectDrawable;
    // }

    /** 输入框背景 */
    public static Drawable INPUT_BG() {
        return MaterialUtils.createInputBg(NUMBER_1_INT);
    }
    
    /** 输入框背景 */
    public static Drawable CREATE_INPUT_BG_FOCUSSTATE() {
        return MaterialUtils.createInputBgFocusState(NUMBER_1_INT,1);
    }

    /** 输入框背景没右边竖线 */
    public static Drawable INPUT_BG_NO_RIGHT_CONRNER() {
        return MaterialUtils.createInputBgNoRightConrner(NUMBER_1_INT);
    }

    /** 输入框背景没左边竖线 */
    public static Drawable INPUT_BG_NO_LEFT_CONRNER() {
        return MaterialUtils.createInputBgNoLeftConrner(NUMBER_1_INT);
    }

    /** 输入框背景没左边竖线 */
    public static Drawable INPUT_BG_STATE() {
        return MaterialUtils.createInputBgNoLeftConrner(NUMBER_1_INT);
    }

    /** 选中的Item背景 */
    public static Drawable SELECT_ITEM_BG() {
        return MaterialUtils.createSelectItemDividerBg(NUMBER_1_INT);
    }
    /** 左边分割线*/
    public static Drawable LEFT_DIVIDER_ITEM_BG() {
        return MaterialUtils.createItemLeftDividerBg(NUMBER_1_INT);
    }

    /** TABBAR背景 */
    public static Drawable TAB_BAR_BG() {
        return MaterialUtils.createTabItemDividerBg(NUMBER_1_INT);
    }

    /** 左边圆角空心 切换按钮 */
    public static Drawable TAB_BAR_CORNER_STORKE_BG_LEFT(int normalColor, int selectColor, int cornorColor, int strokeW) {
        return MaterialUtils.createTabItemCornerBg(Location.LEFT, normalColor, selectColor, cornorColor, 40, 20, strokeW, true);
    }

    /** 右边边圆角空心 切换按钮 */
    public static Drawable TAB_BAR_CORNER_STORKE_BG_RIGHT(int normalColor, int selectColor, int cornorColor, int strokeW) {
        return MaterialUtils.createTabItemCornerBg(Location.RIGHT, normalColor, selectColor, cornorColor, 40, 20, strokeW, true);
    }

    /** 左边圆角实心 切换按钮 */
    public static Drawable TAB_BAR_CORNER_BG_LEFT(int cornorColor) {
        return MaterialUtils.createTabItemCornerBg(Location.LEFT, Color.parseColor("#EBEBEB"), NUMBER_1_INT, cornorColor, 40, 20, 0,
                false);
    }

    /** 中间圆角实心 切换按钮 */
    public static Drawable TAB_BAR_CORNER_BG_CENTER(int cornorColor) {
        return MaterialUtils.createTabItemCornerBg(Location.CENTER, Color.parseColor("#EBEBEB"), NUMBER_1_INT, cornorColor, 40, 20, 0, 
                false);
    }

    /** 右边边圆角实心 切换按钮 */
    public static Drawable TAB_BAR_CORNER_BG_RIGHT(int cornorColor) {
        return MaterialUtils.createTabItemCornerBg(Location.RIGHT, Color.parseColor("#EBEBEB"), NUMBER_1_INT, cornorColor, 40, 20, 0, 
                false);
    }

    /** 弧形淡灰色背景 */
    public static Drawable GRAY_SHAP_DRAWABLE() {
        return MaterialUtils.createCornerBg(Color.parseColor("#F5F5F5"), NUMBER_1_INT, 5);
    }

    /** 透明的Drawable */
    public static Drawable TRANSPARENT_DRAWABLE() {
        return new ColorDrawable(Color.TRANSPARENT);
    }

    /** 实心圆角按钮背景 */
    public static Drawable BUTTON_BG_SOLID_CORNER() {
        return MaterialUtils.createSolidCornerBg(NUMBER_1_INT);
    }

    /** 空心圆角按钮背景 */
    public static Drawable BUTTON_BG_SOLID_STROKE() {
        return MaterialUtils.createSolidStrokeBg(NUMBER_1_INT);
    }

    /** 空心圆角按钮背景 */
    public static Drawable BUTTON_BG_SOLID_STROKE(int color) {
        return MaterialUtils.createSolidStrokeBg(color);
    }

    /** 带状态的文字颜色 */
    public static ColorStateList BASE_COLOR_STATE() {
        return MaterialUtils.createTabItemTextColor(Color.BLACK, NUMBER_1_INT);
    }

    /** 带状态的文字颜色.. 反色 */
    public static ColorStateList BASE_COLOR_STATE_INVERSE() {
        return MaterialUtils.createTabItemTextColor(NUMBER_1_INT, Color.WHITE);
    }

    /** 状态文字颜色 */
    public static ColorStateList COLOR_STATE(int nomal, int select) {
        return MaterialUtils.createTabItemTextColor(nomal, select);
    }

    /** RatingBar 星星 */
    public static Drawable RATINGBAR_PROGERSS_DRAWABLE() {
        Drawable[] layers = new Drawable[3];
        Drawable darwable1 = getDrawable(IcomoonIcon.ICON_IC_STAR_NORMAL, NUMBER_3_INT,10,10);
        Drawable darwable2 = getDrawable(IcomoonIcon.ICON_IC_STAR_NORMAL, NUMBER_3_INT,10,10);
        Drawable darwable3 = getDrawable(IcomoonIcon.ICON_IC_STAR_SOLID, NUMBER_3_INT,10,10);
        
        
        layers[0] = darwable1;
        layers[1] = new ClipDrawable(darwable2, Gravity.LEFT, ClipDrawable.HORIZONTAL);
        layers[2] = new ClipDrawable(darwable3, Gravity.LEFT, ClipDrawable.HORIZONTAL);
        LayerDrawable drawable = new LayerDrawable(layers);
        drawable.setId(0, android.R.id.background);
        drawable.setId(1, android.R.id.secondaryProgress);
        drawable.setId(2, android.R.id.progress);
        return drawable;
    }

    /** 获取图标 */
    public static Drawable getDrawable(IcomoonIcon icon, int color, int w, int h) {
        w = DeviceConfiger.dp2px(w);
        h = DeviceConfiger.dp2px(h);
        IconicFontDrawable drawable = new IconicFontDrawable(sContext);
        drawable.setIcon(icon);
        drawable.setIconColor(color);
        drawable.setIntrinsicWidth(w);
        drawable.setIntrinsicHeight(h);
        drawable.setBounds(0, 0, w, h);
        return drawable;
    }

    /** 获取图标 */
    public static Drawable getDrawable(IcomoonIcon icon) {
        return getDrawable(icon, NUMBER_1_INT, 16, 16);
    }

    /** 获取图标 */
    public static Drawable getDrawable(IcomoonIcon icon, int color) {
        return getDrawable(icon, color, 16, 16);
    }

    /** 获取带状态的图标 */
    public static Drawable getStateDrawable(IcomoonIcon nomalIcon, int nomalColor, IcomoonIcon selectIcon,
            int selectColor, int w, int h) {
        Drawable nomalDrawable = getDrawable(nomalIcon, nomalColor, w, h);
        Drawable selectDrawable = getDrawable(selectIcon, selectColor, w, h);
        StateListDrawable stateDrawable = MaterialUtils.addStateDrawable(//
                new int[] { android.R.attr.state_selected, android.R.attr.state_checked, android.R.attr.state_pressed,
                        -1 },//
                new Drawable[] { selectDrawable, selectDrawable, selectDrawable, nomalDrawable });
        stateDrawable.setBounds(0, 0, w, h);
        return stateDrawable;
    }

    /***/
    public static Drawable getStateDrawable(IcomoonIcon nomalIcon, IcomoonIcon selectIcon) {
        return getStateDrawable(nomalIcon, NUMBER_1_INT, selectIcon, NUMBER_1_INT, 16, 16);
    }
    
    /***/
    public static Drawable getStateDrawable(IcomoonIcon nomalIcon, IcomoonIcon selectIcon,int w,int h) {
        return getStateDrawable(nomalIcon, NUMBER_1_INT, selectIcon, NUMBER_1_INT, w, h);
    }

}
