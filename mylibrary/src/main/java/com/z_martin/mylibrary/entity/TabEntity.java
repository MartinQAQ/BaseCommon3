package com.z_martin.mylibrary.entity;

import com.flyco.tablayout.listener.CustomTabEntity;

/**
 * 底部tabBar导航实体类
 * 和CommonTabLayout组合使用
 * @ describe: 
 * @ author: Martin
 * @ createTime: 2018/9/1 23:53
 * @ version: 1.0
 */
public class TabEntity implements CustomTabEntity {
    /** 标题 */
    private String title;
    /** 选择的图标 */
    private int selectedIcon;
    /** 未选择的图标 */
    private int unSelectedIcon;

    public TabEntity(String title, int selectedIcon, int unSelectedIcon) {
        this.title = title;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectedIcon;
    }
}
