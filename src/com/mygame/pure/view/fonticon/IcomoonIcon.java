/*
 * Copyright (C) 2013 Artur Termenji
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mygame.pure.view.fonticon;


import com.mygame.pure.view.fonticon.TypefaceManager.IconicTypeface;

public enum IcomoonIcon implements Icon {

    ICON_NAVIGATIONIC_WORK(0XE600),
    ICON_NAVIGATIONIC_NOTIFICATION(0XE601),
    ICON_NAVIGATIONIC_USER(0XE602),
    ICON_IC_MAIL(0XE603),
    ICON_IC_RESUME(0XE604),
    ICON_IC_SUBSCRIBE(0XE605),
    ICON_IC_MORE(0XE606),
    ICON_ARROW_DOWN(0XE607),
    ICON_IC_SEARCH(0XE608),
    ICON_IC_REFRESH(0XE609),
    ICON_ARROW_LEFT(0XE60A),
    ICON_ARROW_RIGHT(0XE60B),
    ICON_ARROW_UP(0XE60C),
    ICON_FEEDIC_COLLECTION(0XE60D),
    ICON_FEEDIC_LIKE(0XE60E),
    ICON_FEEDIC_NOTICE(0XE60F),
    ICON_FEEDIC_RECORD(0XE610),
    ICON_FEEDIC_RESUME(0XE611),
    ICON_FEEDIC_SUBSCRIBE(0XE612),
    ICON_IC_ADD(0XE613),
    ICON_IC_CALL(0XE614),
    ICON_IC_MAIL2(0XE615),
    ICON_IC_CHECK_BOX(0XE616),
    ICON_IC_CHECK_BOX_SELECTE(0XE617),
    ICON_IC_CHECK_BOX2(0XE618),
    ICON_IC_CHECK_BOX_SELECTED(0XE619),
    ICON_IC_CLOSE(0XE61A),
    ICON_USERIC_LOGIN(0XE61B),
    ICON_USERIC_REGISTRATION(0XE61C),
    ICON_USERIC_PASSWORD(0XE61D),
    ICON_USERIC_LOCK(0XE61E),
    ICON_USERIC_SHARE(0XE61F),
    ICON_USERIC_PRAISE(0XE620),
    ICON_USERIC_SETTING(0XE621),
    ICON_IC_EDIT(0XE622),
    ICON_IC_EDIT2(0XE623),
    ICON_IC_HEART(0XE624),
    ICON_IC_HEART_BROKEN(0XE625),
    ICON_IC_IMAGE(0XE626),
    ICON_IC_LOACTION(0XE627),
    ICON_IC_NEWS(0XE628),
    ICON_IC_PART_TIMEJOB(0XE629),
    ICON_IC_PRIVATE(0XE62A),
    ICON_IC_PUBLIC(0XE62B),
    ICON_IC_REFRESH2(0XE62C),
    ICON_IC_SHARE(0XE62D),
    ICON_IC_STAR_NORMAL(0XE62E),
    ICON_IC_STAR_SOLID(0XE62F),
    ICON_IC_TIME(0XE630),
    ICON_IC_TRASH(0XE631),
    ICON_IC_VIP(0XE632),
    ICON_TAG_08(0XE633),
    ICON_IC_WORK(0XE634),
    ICON_TAG_01(0XE635),
    ICON_TAG_02(0XE636),
    ICON_TAG_03(0XE637),
    ICON_TAG_04(0XE638),
    ICON_TAG_05(0XE639),
    ICON_TAG_06(0XE63A),
    ICON_TAG_07(0XE63B),
    ICON_IC_MICROPHONE(0XE63C),
    ICON_IMG_ARROW(0XE63D),
    ICON_LOGO(0XE63E),
    ICON_TAG_09(0XE63F),
    ICON_IC_I(0XE640),
    ICON_IC_TIE(0XE641),
    ICON_MICROPHONE(0XE642);
    
    private final int mIconUtfValue;

    private IcomoonIcon(int iconUtfValue)
    {
        mIconUtfValue = iconUtfValue;
    }

    @Override
    public IconicTypeface getIconicTypeface() {
        return IconicTypeface.ICOMOON;
    }

    @Override
    public int getIconUtfValue() {
        return mIconUtfValue;
    }
}
