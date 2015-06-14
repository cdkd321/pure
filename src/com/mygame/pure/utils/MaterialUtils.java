package com.mygame.pure.utils;

import com.mygame.pure.utils.DividerDrawable.DividerOrientation;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Color;
import android.graphics.NinePatch;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.view.Gravity;

public class MaterialUtils {

	public enum Location {
        LEFT, CENTER, RIGHT;
    }

    /**
     * 创建单选按钮的背景
     * 
     * @param local
     * @param normalColor
     * @param selectColor
     * @param width
     * @param hight
     * @param isDrawStroke
     * @return
     */
    public static Drawable createTabItemCornerBg(Location local, int normalColor, int selectColor, int cornerColor,
            int width, int hight, int strokeW, boolean isDrawStroke) {
        final int roundRadius = DeviceConfiger.dp2px(4); // 4dp 圆角半径
        width = DeviceConfiger.dp2px(width);
        hight = DeviceConfiger.dp2px(hight);
        float[] cornerRadii = { 0, 0, 0, 0, 0, 0, 0, 0 };
        switch (local) {
        case LEFT:
            cornerRadii = new float[] { roundRadius, roundRadius, 0, 0, 0, 0, roundRadius, roundRadius };
            break;
        case CENTER:
            cornerRadii = new float[] { 0, 0, 0, 0, 0, 0, 0, 0 };
            break;
        case RIGHT:
            cornerRadii = new float[] { 0, 0, roundRadius, roundRadius, roundRadius, roundRadius, 0, 0 };
            break;

        }

        // 默认
        GradientDrawable normalDrawable = new GradientDrawable();
        normalDrawable.setColor(normalColor);
        normalDrawable.setCornerRadius(roundRadius);
        normalDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        normalDrawable.setCornerRadii(cornerRadii);
        if (isDrawStroke) {
            int strokeWidth = strokeW; // 3px 边框宽度
            normalDrawable.setStroke(strokeWidth, cornerColor);
        }
        normalDrawable.setSize(width, hight);

        // 选中
        GradientDrawable selectDrawable = new GradientDrawable();
        selectDrawable.setColor(selectColor);
        selectDrawable.setCornerRadii(cornerRadii);
        selectDrawable.setSize(width, hight);

        Drawable itemDrawable = addStateDrawable(new int[] { android.R.attr.state_selected,
                android.R.attr.state_checked, -1 }, new Drawable[] { selectDrawable, selectDrawable, normalDrawable });
        return itemDrawable;

    }

    /**
     * 创建选中有分割线的TAB条目背景，未选中为透明。
     * 
     * @param selectColor
     *            选中分割线的颜色。
     * @return
     */
    public static Drawable createTabItemDividerBg(int dividerColor) {
        int hight = DeviceConfiger.dp2px(5);
        // 默认
        ColorDrawable normalDrawable = new ColorDrawable(android.R.color.transparent);
        // 选中
        DividerDrawable selectDrawable = new DividerDrawable();
        selectDrawable.setDividerColor(dividerColor);
        selectDrawable.setStrokeWidth(hight);
        Drawable itemDrawable = addStateDrawable(//
                new int[] { android.R.attr.state_selected, android.R.attr.state_checked, -1 },//
                new Drawable[] { selectDrawable, selectDrawable, normalDrawable });
        return itemDrawable;
    }

    public static Drawable createTabItemDividerBg(String dividerColor) {
        return createTabItemDividerBg(Color.parseColor(dividerColor));

    }

    /**
     * 创建选中有分割线的TAB条目背景，未选中为透明。
     * 
     * @param selectColor
     *            选中分割线的颜色。
     * @return
     */
    public static Drawable createSelectItemDividerBg(int dividerColor) {
        int hight = DeviceConfiger.dp2px(5);
        // 默认
        ColorDrawable normalDrawable = new ColorDrawable(android.R.color.transparent);
        // 选中
        DividerDrawable selectDrawable = new DividerDrawable();
        selectDrawable.setDividerColor(dividerColor);
        selectDrawable.setDividerOrientaion(DividerOrientation.LEFT);
        selectDrawable.setStrokeWidth(hight);
        Drawable itemDrawable = addStateDrawable(new int[] { android.R.attr.state_selected,
                android.R.attr.state_checked, -1 }, new Drawable[] { selectDrawable, selectDrawable, normalDrawable });
        return itemDrawable;
    }
    
    public static Drawable createItemLeftDividerBg(int dividerColor) {
        int hight = DeviceConfiger.dp2px(5);
        DividerDrawable drawable = new DividerDrawable();
        drawable.setDividerColor(dividerColor);
        drawable.setDividerOrientaion(DividerOrientation.LEFT);
        drawable.setStrokeWidth(hight);
        return drawable;
    }
    
    /**
     * 创建tab条目的文字颜色
     * 
     * @param normalColor
     * @param selectColor
     * @return
     */
    public static ColorStateList createTabItemTextColor(String normalColor, String selectColor) {
        return createTabItemTextColor(Color.parseColor(normalColor), Color.parseColor(selectColor));
    }

    /**
     * 创建tab条目的文字颜色
     * 
     * @param normalColor
     * @param selectColor
     * @return
     */
    public static ColorStateList createTabItemTextColor(int normalColor, int selectColor) {
        return createColorStateList(new int[] { android.R.attr.state_selected, android.R.attr.state_checked,
                android.R.attr.state_pressed, -1 }, new int[] { selectColor, selectColor, selectColor, normalColor });
    }

    /**
     * 创建带分割线输入框背景
     * 
     * @param dividerColor
     *            底部分割线颜色
     * @param hight
     *            线的高度 px
     * @param conrnerLeftHight
     *            左边拐角的高度
     * @param conrnerRightHight
     *            右边拐角的高度
     * @return 背景图片Drawable
     */
    public static Drawable createInputBg(int dividerColor, int hight, int conrnerLeftHight, int conrnerRightHight) {
        hight = DeviceConfiger.dp2px(hight);
        conrnerLeftHight = DeviceConfiger.dp2px(conrnerLeftHight);
        conrnerRightHight = DeviceConfiger.dp2px(conrnerRightHight);
        // 选中
        DividerDrawable drawable = new DividerDrawable();
        drawable.setDividerColor(dividerColor);
        drawable.setStrokeWidth(hight);
        drawable.setLeftCornerHight(conrnerLeftHight);
        drawable.setRightCornerHight(conrnerRightHight);
        drawable.setStrokeWidth(hight);
        return drawable;
    }
    
    public static Drawable createInputBgFocusState(int dividerColor, int hight) {
        hight = DeviceConfiger.dp2px(hight);
        // 选中
        DividerDrawable focusDrawable = new DividerDrawable();
        focusDrawable.setDividerColor(dividerColor);
        focusDrawable.setStrokeWidth(hight);

        DividerDrawable normalDrawable = new DividerDrawable();
        normalDrawable.setDividerColor(Color.parseColor("#dddddd"));
        normalDrawable.setStrokeWidth(hight);
        Drawable itemDrawable = addStateDrawable(new int[] { android.R.attr.state_focused, android.R.attr.state_selected,-1 }, //
                new Drawable[] { focusDrawable, focusDrawable,normalDrawable });
        return itemDrawable;
    }

    public static Drawable createInputBg(int dividerColor) {
        return createInputBg(dividerColor, 1, 5, 5);
    }

    public static Drawable createInputBgNoRightConrner(int dividerColor) {
        return createInputBg(dividerColor, 1, 5, 0);
    }

    public static Drawable createInputBgNoLeftConrner(int dividerColor) {
        return createInputBg(dividerColor, 1, 0, 5);
    }

    /**
     * 无状态实心圆角按钮
     * 
     * @param color
     * @return
     */
    public static Drawable createCompleteSolidCornerBg(int color) {
        int cornersRadius = DeviceConfiger.dp2px(5);
        // 默认
        GradientDrawable normalDrawable = new GradientDrawable();
        normalDrawable.setCornerRadius(cornersRadius);
        normalDrawable.setColor(color);

        // 选中 和默认一样
        GradientDrawable selectDrawable = new GradientDrawable();
        selectDrawable.setCornerRadius(cornersRadius);

        // 转换颜色到半透明
        selectDrawable.setColor(color);

        Drawable itemDrawable = addStateDrawable(//
                new int[] { android.R.attr.state_selected, android.R.attr.state_pressed, -1 }, //
                new Drawable[] { selectDrawable, selectDrawable, normalDrawable });
        return itemDrawable;
    }

    /**
     * 实心圆角按钮
     * 
     * @param color
     * @return
     */
    public static Drawable createSolidCornerBg(int color) {
        int cornersRadius = DeviceConfiger.dp2px(5);
        // 默认
        GradientDrawable normalDrawable = new GradientDrawable();
        normalDrawable.setCornerRadius(cornersRadius);
        normalDrawable.setColor(color);

        // 选中
        GradientDrawable selectDrawable = new GradientDrawable();
        selectDrawable.setCornerRadius(cornersRadius);

        // 转换颜色到半透明
        color &= 0x88FFFFFF;
        selectDrawable.setColor(color);

        Drawable itemDrawable = addStateDrawable(//
                new int[] { android.R.attr.state_selected, android.R.attr.state_pressed, -1 }, //
                new Drawable[] { selectDrawable, selectDrawable, normalDrawable });
        return itemDrawable;
    }

    /**
     * 实心带边框线的圆角按钮
     * 
     * @param color
     *            -- 线的颜色
     * @return
     */
    public static Drawable createSolidStrokeBg(int color) {
        int cornersRadius = DeviceConfiger.dp2px(5);
        int strokeWidth = DeviceConfiger.dp2px(1);
        // 默认
        GradientDrawable normalDrawable = new GradientDrawable();
        normalDrawable.setColor(Color.WHITE);
        normalDrawable.setCornerRadius(cornersRadius);
        normalDrawable.setStroke(strokeWidth, color);
        // 选中
        GradientDrawable selectDrawable = new GradientDrawable();
        selectDrawable.setColor(color);
        selectDrawable.setCornerRadius(cornersRadius);
        Drawable itemDrawable = addStateDrawable(//
                new int[] { android.R.attr.state_selected, android.R.attr.state_checked, android.R.attr.state_pressed,
                        -1 }, //
                new Drawable[] { selectDrawable, selectDrawable, selectDrawable, normalDrawable });
        return itemDrawable;
    }
    
    
    /**
     * 实心带边框线的圆角按钮
     * 
     * @param stokeColor
     *            -- 线的颜色
     * @return
     */
    public static Drawable createSolidStrokeBg(int strokeWidth, int stokeColor) {
        int cornersRadius = DeviceConfiger.dp2px(5);
        // 默认
        GradientDrawable normalDrawable = new GradientDrawable();
        normalDrawable.setColor(Color.WHITE);
        normalDrawable.setCornerRadius(cornersRadius);
        normalDrawable.setStroke(strokeWidth, stokeColor);
        // 选中
        GradientDrawable selectDrawable = new GradientDrawable();
        selectDrawable.setColor(stokeColor);
        selectDrawable.setCornerRadius(cornersRadius);
        Drawable itemDrawable = addStateDrawable(//
                new int[] { android.R.attr.state_selected, android.R.attr.state_checked, android.R.attr.state_pressed,
                        -1 }, //
                new Drawable[] { selectDrawable, selectDrawable, selectDrawable, normalDrawable });
        return itemDrawable;
    }

    public static Drawable createCornerBg(int normalColor, int paressColor, int cornerRadiusDp) {
        int cornersRadiusPx = DeviceConfiger.dp2px(cornerRadiusDp);
        // 默认
        GradientDrawable normalDrawable = new GradientDrawable();
        normalDrawable.setColor(normalColor);
        normalDrawable.setCornerRadius(cornersRadiusPx);
        // 选中
        GradientDrawable selectDrawable = new GradientDrawable();
        selectDrawable.setColor(paressColor);
        selectDrawable.setCornerRadius(cornersRadiusPx);
        Drawable itemDrawable = addStateDrawable(//
                new int[] { android.R.attr.state_selected, android.R.attr.state_checked, android.R.attr.state_pressed,
                        -1 }, //
                new Drawable[] { selectDrawable, selectDrawable, selectDrawable, normalDrawable });
        return itemDrawable;
    }

    // //////////////////////////////////////////////////////////////////////////////////
    /**
     * 创建StateListDrawablw对象。
     * 
     * @param states
     * @param drawables
     * @return StateListDrawable 返回类型
     */
    public static StateListDrawable addStateDrawable(int[] states, Drawable[] drawables) {
        StateListDrawable sd = new StateListDrawable();
        Drawable normalDrawable = null;
        // 注意该处的顺序，只要有一个状态与之相配，背景就会被换掉
        // 所以不要把大范围放在前面了，如果sd.addState(new[]{},normal)放在第一个的话，就没有什么效果了
        for (int i = 0; i < states.length; i++) {
            if (states[i] != -1) {
                sd.addState(new int[] { android.R.attr.state_enabled, states[i] }, drawables[i]);
                sd.addState(new int[] { states[i] }, drawables[i]);
            } else {
                normalDrawable = drawables[i];
            }
        }
        sd.addState(new int[] { android.R.attr.state_enabled }, normalDrawable);
        sd.addState(new int[] {}, normalDrawable);
        return sd;
    }

    /**
     * 创建StateListDrawablw对象。
     * 
     * @param states
     * @param drawables
     * @return StateListDrawable 返回类型
     */
    public static StateListDrawable addStateDrawable(Resources res, int[] states, Bitmap[] bitmaps) {
        StateListDrawable sd = new StateListDrawable();
        Drawable normalDrawable = null;
        // 注意该处的顺序，只要有一个状态与之相配，背景就会被换掉
        // 所以不要把大范围放在前面了，如果sd.addState(new[]{},normal)放在第一个的话，就没有什么效果了
        for (int i = 0; i < states.length; i++) {
            if (bitmaps[i] != null) {
                Drawable drawable = createDrawable(res, bitmaps[i]);
                if (states[i] != -1) {
                    sd.addState(new int[] { android.R.attr.state_enabled, states[i] }, drawable);
                    sd.addState(new int[] { states[i] }, drawable);
                } else {
                    normalDrawable = drawable;
                }
            }
        }
        sd.addState(new int[] { android.R.attr.state_enabled }, normalDrawable);
        sd.addState(new int[] {}, normalDrawable);
        return sd;
    }

    /** 对TextView设置不同状态时其文字颜色。 */
    public static ColorStateList createColorStateList(int[] states, int[] colors) {
        int[] tempColors = colors;
        int[][] tempStates = new int[colors.length][];

        for (int i = 0; i < tempColors.length; i++) {
            if (states[i] == -1) {
                tempStates[i] = new int[] { android.R.attr.state_enabled };
            } else {
                tempStates[i] = new int[] { android.R.attr.state_enabled, states[i] };
            }
        }
        ColorStateList colorList = new ColorStateList(tempStates, tempColors);
        return colorList;
    }

    public static Drawable createDrawable(Resources res, Bitmap bitmap) {
        byte[] chunk = bitmap.getNinePatchChunk();
        boolean result = NinePatch.isNinePatchChunk(chunk);
        Drawable drawable = null;
        if (result) {
            drawable = new NinePatchDrawable(res, bitmap, chunk, new Rect(), null);
        } else {
            drawable = new BitmapDrawable(res, bitmap);
        }
        return drawable;
    }

    public static Drawable[] createDrawablesByImages(Resources res, Bitmap[] bitmaps) {
        Drawable[] drawables = new Drawable[bitmaps.length];
        for (int i = 0; i < bitmaps.length; i++) {
            byte[] chunk = bitmaps[i].getNinePatchChunk();
            boolean result = NinePatch.isNinePatchChunk(chunk);
            if (result) {
                drawables[i] = new NinePatchDrawable(res, bitmaps[i], chunk, new Rect(), null);
            } else {
                drawables[i] = new BitmapDrawable(res, bitmaps[i]);
            }
        }
        return drawables;
    }

    // /////////////////////圆形tab背景/////////////////////////////////////////////////
    public static Drawable createColorFilletButton(String fillColorStr) {
        int roundRadius = DeviceConfiger.dp2px(8); // 8dp 圆角半径
        int fillColor = Color.parseColor(fillColorStr);// 内部填充颜色
        GradientDrawable normalDrawable = new GradientDrawable();// 创建drawable
        normalDrawable.setColor(fillColor);
        normalDrawable.setCornerRadius(roundRadius);
        return normalDrawable;
    }

    /**
     * 创建tabbar 背景图片
     * 
     * @param strokeColor
     *            边框颜色
     * @return
     */
    public static Drawable createTabbarBg(String strokeColor) {
        int roundRadius = 1000; // 8dp 圆角半径
        int strokeWidth = DeviceConfiger.dp2px(1); // 3dp 边框宽度
        GradientDrawable drawable = new GradientDrawable();// 创建drawable
        drawable.setAlpha(255);
        // 背景透明
        drawable.setColor(Color.parseColor("#00000000"));
        // 设置边框
        drawable.setCornerRadius(roundRadius);
        int width = DeviceConfiger.sWidth - DeviceConfiger.dp2px(23);
        int hight = DeviceConfiger.dp2px(40);
        drawable.setSize(width, hight);
        // #8ccdfe
        drawable.setStroke(strokeWidth, Color.parseColor(strokeColor));
        return drawable;
    }

    /**
     * 创建tab条目背景
     * 
     * @param normalColor
     *            未选中颜色
     * @param selectColor
     *            选中颜色
     * @return
     */
    public static Drawable createTabItemBg(String normalColor, String selectColor) {
        final int roundRadius = 1000; // 8dp 圆角半径
        int width = DeviceConfiger.dp2px(80);
        int hight = DeviceConfiger.dp2px(40);
        // 默认
        GradientDrawable normalDrawable = new GradientDrawable();
        normalDrawable.setColor(Color.parseColor(normalColor));
        normalDrawable.setCornerRadius(roundRadius);
        normalDrawable.setSize(width, hight);
        // 选中
        GradientDrawable selectDrawable = new GradientDrawable();
        selectDrawable.setColor(Color.parseColor(selectColor));// 186
        selectDrawable.setCornerRadius(roundRadius);
        selectDrawable.setSize(width, hight);
        Drawable itemDrawable = addStateDrawable(new int[] { android.R.attr.state_selected,
                android.R.attr.state_checked, -1 }, new Drawable[] { selectDrawable, selectDrawable, normalDrawable });
        return itemDrawable;

    }

    private Drawable buildRatingBarDrawables(Bitmap[] images) {
        final int[] requiredIds = { android.R.id.background, android.R.id.secondaryProgress, android.R.id.progress };
        final float[] roundedCorners = new float[] { 5, 5, 5, 5, 5, 5, 5, 5 };
        Drawable[] pieces = new Drawable[3];
        for (int i = 0; i < 3; i++) {

            ShapeDrawable sd = new ShapeDrawable(new RoundRectShape(roundedCorners, null, null));

            BitmapShader bitmapShader = new BitmapShader(images[i], Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
            sd.getPaint().setShader(bitmapShader);

            ClipDrawable cd = new ClipDrawable(sd, Gravity.LEFT, ClipDrawable.HORIZONTAL);
            if (i == 0) {
                pieces[i] = sd;
            } else {
                pieces[i] = cd;
            }
        }
        LayerDrawable ld = new LayerDrawable(pieces);
        for (int i = 0; i < 3; i++) {
            ld.setId(i, requiredIds[i]);
        }
        return ld;
    }
}
