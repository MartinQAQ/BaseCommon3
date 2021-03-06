package com.z_martin.mylibrary.entity;

import java.io.Serializable;

/**
 * @ describe: 错误信息实体类
 * @ author: Martin
 * @ createTime: 2018/10/9 10:43
 * @ version: 1.0
 */
public class ErrorEntity implements Serializable {
    
    /** 系统硬件商 */
    private String manufacturer;
    
    /** 手机品牌 */
    private String brand;
    
    /** 手机型号 */
    private String model;
    
    /** 手机版本号 */
    private String deviceVersion;
    
    /** 系统版本 */
    private String sysVersion;
    
    /** sdk版本 */
    private int sdkVersion;

    /** 版本名称 */
    private String versionName;

    /** 版本号 */
    private int versionCode;
    
    /** 语言 */
    private String languageType;

    /** 错误信息 */
    private String errorMsg;

    /** 发生的Action */
    private String action;

    public ErrorEntity() {
    }

    public ErrorEntity(String manufacturer, String brand, String model, String deviceVersion, String sysVersion, int sdkVersion, String versionName, int versionCode, String errorMsg, String action, String languageType) {
        this.manufacturer = manufacturer;
        this.brand = brand;
        this.model = model;
        this.deviceVersion = deviceVersion;
        this.sysVersion = sysVersion;
        this.sdkVersion = sdkVersion;
        this.versionName = versionName;
        this.versionCode = versionCode;
        this.errorMsg = errorMsg;
        this.action = action;
        this.languageType = languageType;
    }

    @Override
    public String toString() {
        return "{" +
                "'manufacturer':'" + manufacturer + '\'' +
                ", 'brand':'" + brand + '\'' +
                ", 'model':'" + model + '\'' +
                ", 'deviceVersion':'" + deviceVersion + '\'' +
                ", 'sysVersion':'" + sysVersion + '\'' +
                ", 'sdkVersion':'" + sdkVersion + '\'' +
                ", 'versionName':'" + versionName + '\'' +
                ", 'versionCode:" + versionCode +
                ", 'errorMsg':'" + errorMsg + '\'' +
                ", 'action':'" + action + '\'' +
                ", 'languageType':'" + languageType + '\'' +
                '}';
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDeviceVersion() {
        return deviceVersion;
    }

    public void setDeviceVersion(String deviceVersion) {
        this.deviceVersion = deviceVersion;
    }

    public String getSysVersion() {
        return sysVersion;
    }

    public void setSysVersion(String sysVersion) {
        this.sysVersion = sysVersion;
    }

    public int getSdkVersion() {
        return sdkVersion;
    }

    public void setSdkVersion(int sdkVersion) {
        this.sdkVersion = sdkVersion;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getLanguageType() {
        return languageType;
    }

    public void setLanguageType(String languageType) {
        this.languageType = languageType;
    }
}
