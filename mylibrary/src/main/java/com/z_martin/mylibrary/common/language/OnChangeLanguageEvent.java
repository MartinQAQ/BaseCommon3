package com.z_martin.mylibrary.common.language;

/**
 * @ describe:
 * @ author: Martin
 * @ createTime: 2018/9/27 14:15
 * @ version: 1.0
 */
public class OnChangeLanguageEvent {
    public String languageType;

    public OnChangeLanguageEvent(String languageType) {
        this.languageType = languageType;
    }
}
