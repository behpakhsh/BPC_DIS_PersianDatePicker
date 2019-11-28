package bpc.dis.persiandatepicker.PersianDatePicker;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import java.util.Date;

public class Builder {

    private Date date = new Date();
    private PersianDatePickerListener persianDatePickerListener = null;
    private int backgroundRes = 0;
    private int boxBackgroundRes = 0;
    private int itemSpace = 0;
    private int itemTextColor = 0;
    private int itemTextSize = 0;
    private int selectedItemTextColor = 0;
    private int jumpTodayTextColor = 0;
    private int jumpTodayTextSize = 0;
    private int buttonBackgroundRes = 0;
    private int buttonTextColor = 0;
    private String jumpTodayText = null;
    private String buttonText = null;
    private Typeface font = null;
    private boolean closeEnable = true;
    private boolean cancelable = true;
    private Drawable closeDrawable = null;
    private int closeTintColorRes = 0;

    public Builder setPersianDatePickerListener(PersianDatePickerListener persianDatePickerListener) {
        this.persianDatePickerListener = persianDatePickerListener;
        return this;
    }

    public Builder setBackgroundRes(int backgroundRes) {
        this.backgroundRes = backgroundRes;
        return this;
    }

    public Builder setBoxBackgroundRes(int boxBackgroundRes) {
        this.boxBackgroundRes = boxBackgroundRes;
        return this;
    }

    public Builder setItemSpace(int itemSpace) {
        this.itemSpace = itemSpace;
        return this;
    }

    public Builder setItemTextColor(int itemTextColor) {
        this.itemTextColor = itemTextColor;
        return this;
    }

    public Builder setItemTextSize(int itemTextSize) {
        this.itemTextSize = itemTextSize;
        return this;
    }

    public Builder setSelectedItemTextColor(int selectedItemTextColor) {
        this.selectedItemTextColor = selectedItemTextColor;
        return this;
    }

    public Builder setJumpTodayTextColor(int jumpTodayTextColor) {
        this.jumpTodayTextColor = jumpTodayTextColor;
        return this;
    }

    public Builder setJumpTodayTextSize(int jumpTodayTextSize) {
        this.jumpTodayTextSize = jumpTodayTextSize;
        return this;
    }

    public Builder setJumpTodayText(String jumpTodayText) {
        this.jumpTodayText = jumpTodayText;
        return this;
    }

    public Builder setButtonBackgroundRes(int buttonBackgroundRes) {
        this.buttonBackgroundRes = buttonBackgroundRes;
        return this;
    }

    public Builder setButtonText(String buttonText) {
        this.buttonText = buttonText;
        return this;
    }

    public Builder setButtonTextColor(int buttonTextColor) {
        this.buttonTextColor = buttonTextColor;
        return this;
    }

    public Builder setDate(Date date) {
        this.date = date;
        return this;
    }

    public Builder setFont(Typeface font) {
        this.font = font;
        return this;
    }

    public void setCloseEnable(boolean closeEnable) {
        this.closeEnable = closeEnable;
    }

    public void setCloseDrawable(Drawable closeDrawable) {
        this.closeDrawable = closeDrawable;
    }

    public void setCloseTintColorRes(int closeTintColorRes) {
        this.closeTintColorRes = closeTintColorRes;
    }

    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }

    public PersianDatePicker build() {
        PersianDatePicker datePickerConfig = new PersianDatePicker();
        datePickerConfig.setCancelable(cancelable);
        datePickerConfig.persianDatePickerListener = persianDatePickerListener;
        datePickerConfig.backgroundRes = backgroundRes;
        datePickerConfig.boxBackgroundRes = boxBackgroundRes;
        datePickerConfig.itemSpace = itemSpace;
        datePickerConfig.font = font;
        datePickerConfig.itemTextColor = itemTextColor;
        datePickerConfig.itemTextSize = itemTextSize;
        datePickerConfig.selectedItemTextColor = selectedItemTextColor;
        datePickerConfig.jumpTodayTextColor = jumpTodayTextColor;
        datePickerConfig.jumpTodayTextSize = jumpTodayTextSize;
        datePickerConfig.jumpTodayText = jumpTodayText;
        datePickerConfig.buttonBackgroundRes = buttonBackgroundRes;
        datePickerConfig.buttonText = buttonText;
        datePickerConfig.buttonTextColor = buttonTextColor;
        datePickerConfig.date = date;
        datePickerConfig.closeEnable = closeEnable;
        datePickerConfig.closeDrawable = closeDrawable;
        datePickerConfig.closeTintColorRes = closeTintColorRes;
        return datePickerConfig;
    }

}
