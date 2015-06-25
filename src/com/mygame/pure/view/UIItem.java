package com.mygame.pure.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mygame.pure.R;

/**
 * 用于个人资料item的显示 将几个系统原生的控件组合到一起，这样创建出的控件就被称为组合控件
 * 
 * @author fly
 */
public class UIItem extends RelativeLayout {
    private View line;
    private ImageView leftIcon, item_ivRight, item_ivRight2;
    private TextView tvLeft1;

    public UIItem(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.item_layout, this);
        line = findViewById(R.id.item_line);
        leftIcon = (ImageView) findViewById(R.id.item_icon);
        tvLeft1 = (TextView) findViewById(R.id.item_tvLeft);
        item_ivRight = (ImageView) findViewById(R.id.item_ivRight);
        item_ivRight2 = (ImageView) findViewById(R.id.item_ivRight2);
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
        }
    }

    /**
     * 给组件设置值
     * @param iconId -1表示隐藏左边图标，否则设置指定drawable
     * @param leftStrId1 为-1，表示使用默认图标，否则设置指定string
     */
    public void setValue(int iconId, int leftStrId1, int rDid1, int rDid2) {
    	if(iconId == -1){
    		leftIcon.setVisibility(View.GONE);
    	} else {
    		leftIcon.setVisibility(View.VISIBLE);
    		leftIcon.setImageResource(iconId);
    	}
    	
    	if(rDid1 == -1){
    		item_ivRight.setVisibility(View.GONE);
    	} else {
    		item_ivRight.setVisibility(View.VISIBLE);
    		item_ivRight.setImageResource(rDid1);
    	}
    	
    	if(rDid2 == -1){
    		item_ivRight2.setVisibility(View.GONE);
    	} else {
    		item_ivRight2.setVisibility(View.VISIBLE);
    		item_ivRight2.setImageResource(rDid2);
    	}
    	
    	if(leftStrId1 != -1) {
    		tvLeft1.setText(leftStrId1);
    	}
    }

}
