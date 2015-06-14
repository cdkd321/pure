package com.mygame.pure.view.fonticon;

import static android.text.Html.fromHtml;
import static android.text.Html.toHtml;
import android.text.Spanned;
import android.widget.TextView;
import com.mygame.pure.view.fonticon.TypefaceManager.IconicTypeface;

public class IconifyUtils {
	
    /**
     * Transform the given TextViews replacing {icon_xxx} texts with icons.
     */
    public static final void addIcons(IconicTypeface typeface, TextView... textViews) {
        for (TextView textView : textViews) {
            textView.setTypeface(typeface.getTypeface(textView.getContext()));
            textView.setText(compute(typeface, textView.getText()));
        }
    }

    public static CharSequence compute(IconicTypeface typeface, CharSequence charSequence) {
        if (charSequence instanceof Spanned) {
            String text = toHtml((Spanned) charSequence);
            return fromHtml(replaceIcons(typeface, new StringBuilder((text))).toString());
        }
        String text = charSequence.toString();
        return replaceIcons(typeface, new StringBuilder(text));
    }

    public static StringBuilder replaceIcons(IconicTypeface typeface, StringBuilder text) {
        int startIndex = text.indexOf("{ICON");
        if (startIndex == -1) {
            return text;
        }
        
        int endIndex = text.substring(startIndex).indexOf("}") + startIndex + 1;
        
        String iconString = text.substring(startIndex + 1, endIndex - 1);
        
        iconString = iconString.replaceAll("-", "_");
        
        try {
            if (typeface == IconicTypeface.ICOMOON) {
                IcomoonIcon value = IcomoonIcon.valueOf(iconString);
                char[] utfChars = Character.toChars(value.getIconUtfValue());
                String iconValue = String.valueOf(utfChars);
                text = text.replace(startIndex, endIndex, iconValue);
                return replaceIcons(typeface, text);
            }
            return text;
        } catch (IllegalArgumentException e) {
            return text;
        }
    }
}
