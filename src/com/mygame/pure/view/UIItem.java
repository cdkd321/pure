package com.mygame.pure.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mygame.pure.R;
import com.mygame.pure.utils.DeviceConfiger;

/**
 * 用于个人资料item的显示 将几个系统原生的控件组合到一起，这样创建出的控件就被称为组合控件
 * 
 * @author fly
 */
public class UIItem extends RelativeLayout {
    private View line;
    private ImageView leftIcon;
    private TextView tvLeft1, tvRight;

    public UIItem(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.item_layout, this);
        line = findViewById(R.id.item_line);
        leftIcon = (ImageView) findViewById(R.id.item_icon);
        tvLeft1 = (TextView) findViewById(R.id.item_tvLeft);
        tvRight = (TextView) findViewById(R.id.item_tvRight);
        if (attrs != null) {
            // AttributeSet是节点的属性集合
            // TypedArray 属性的容器
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.UIItem);
            leftIcon.setImageDrawable(a.getDrawable(R.styleable.UIItem_leftIcon));
            switch (a.getInt(R.styleable.UIItem_leftIcon_visible, 0)) {
            case -1:
                leftIcon.setVisibility(View.GONE);
                break;
            case 0:
                leftIcon.setVisibility(View.VISIBLE);
                break;
            case 1:
                leftIcon.setVisibility(View.INVISIBLE);
                break;
            }
            tvLeft1.setText(a.getString(R.styleable.UIItem_leftText1));
            tvRight.setText(a.getString(R.styleable.UIItem_rightText));
        }
    }

    public void setValue(int iconId, int leftStrId1, String leftStr) {
        leftIcon.setImageResource(iconId);
        tvLeft1.setText(leftStrId1);
    }

    public void setRightText(String rightStr) {
        tvRight.setText(rightStr);
    }

}
