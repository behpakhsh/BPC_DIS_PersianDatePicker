package bpc.dis.persiandatepicker.PersianDatePicker;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import bpc.dis.persiandatepicker.R;
import bpc.dis.persiandatepicker.WheelPicker.WheelPicker;
import bpc.dis.utilities.DialogSizeHelper.DialogSizeHelper;
import bpc.dis.utilities.JalaliCalendar.JalaliCalendar;
import bpc.dis.utilities.LeapYearHelper.LeapYearHelper;
import bpc.dis.utilities.SolarCalendar.SolarCalendar;

public class PersianDatePicker extends DialogFragment {

    private PersianDatePickerListener persianDatePickerListener;
    private Date date;
    private int backgroundRes;
    private int boxBackgroundRes;
    private int itemSpace;
    private int itemTextColor;
    private int itemTextSize;
    private int selectedItemTextColor;
    private int jumpTodayTextColor;
    private int jumpTodayTextSize;
    private String jumpTodayText;
    private int buttonBackgroundRes;
    private int buttonTextColor;
    private int buttonTextSize;
    private String buttonText;
    private Typeface font;
    private boolean closeEnable;
    private Drawable closeDrawable;
    private int closeTintColorRes;

    private WheelPicker wpDay;
    private WheelPicker wpMonth;
    private WheelPicker wpYear;
    private AppCompatImageButton btnClose;
    private AppCompatButton btnSubmit;
    private AppCompatButton btnJumpToday;
    private ConstraintLayout clPersianDatePicker;
    private View vBoxDay;
    private View vBoxMonth;
    private View vBoxYear;

    private List<String> years = new ArrayList<>();
    private List<String> month = new ArrayList<>();
    private List<String> day = new ArrayList<>();
    private boolean isLeapYear;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getDialog() != null) {
            if (getDialog().getWindow() != null) {
                getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            }
        }

        initValues();

        View view = inflater.inflate(R.layout.persian_date_picker, container, false);
        wpDay = view.findViewById(R.id.wp_day);
        wpMonth = view.findViewById(R.id.wp_month);
        wpYear = view.findViewById(R.id.wp_year);
        btnClose = view.findViewById(R.id.btn_close);
        btnSubmit = view.findViewById(R.id.btn_submit);
        btnJumpToday = view.findViewById(R.id.btn_jump_today);
        clPersianDatePicker = view.findViewById(R.id.cl_persian_date_picker);
        vBoxDay = view.findViewById(R.id.v_box_day);
        vBoxMonth = view.findViewById(R.id.v_box_month);
        vBoxYear = view.findViewById(R.id.v_box_year);

        setDialogAttribute();
        setBoxAttribute();
        setWheelPickerAttribute();
        setJumpTodayAttribute();
        setButtonAttribute();
        setCloseAttribute();

        loadDate();

        return view;
    }

    @Override
    public void onActivityCreated(Bundle arg0) {
        super.onActivityCreated(arg0);
        if (getDialog() != null)
            if (getDialog().getWindow() != null)
                getDialog().getWindow().getAttributes().windowAnimations = R.style.alertAnimation;
    }

    @Override
    public void onResume() {
        super.onResume();
        DialogSizeHelper.defineDialogSize(getDialog().getWindow(), 0.90, 0.35);
    }


    private void loadDate() {
        SolarCalendar solarCalendar = new SolarCalendar();
        isLeapYear = LeapYearHelper.checkLeapYear(1350);
        setYear(solarCalendar.year);
        setMonth();
        setDay(1);
        findToday(date, false);
    }

    private void findToday(Date date, boolean animate) {
        SolarCalendar solarCalendar = new SolarCalendar(date);
        for (int i = 0; i < years.size(); i++) {
            if (years.get(i).equals(String.valueOf(solarCalendar.year))) {
                wpYear.setSelectedItemPosition(i, animate);
                break;
            }
        }
        wpMonth.setSelectedItemPosition(solarCalendar.month - 1, animate);
        wpDay.setSelectedItemPosition(solarCalendar.day - 1, animate);
    }

    private void setDay(int month) {
        int end = 31;
        if (month > 6) {
            if (!isLeapYear) {
                if (month == 12)
                    end = 29;
                else
                    end = 30;
            } else {
                end = 30;
            }
        }
        try {
            day = new ArrayList<>();
            for (int i = 1; i <= end; i++)
                day.add(String.valueOf(i));
            wpDay.setData(day);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setMonth() {
        try {
            month.addAll(Arrays.asList(getResources().getStringArray(R.array.persianDatePickerMonth)).subList(0, 12));
            wpMonth.setData(month);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setYear(int end) {
        try {
            for (int i = 1350; i <= end; i++)
                years.add(String.valueOf(i));
            wpYear.setData(years);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void returnDate() {
        if (persianDatePickerListener != null) {
            persianDatePickerListener.selectedDate(new JalaliCalendar().getGregorianDate(String.format("%s/%s/%s", years.get(wpYear.getCurrentItemPosition()), String.valueOf(wpMonth.getCurrentItemPosition() + 1), day.get(wpDay.getCurrentItemPosition()))));
        }
        dismiss();
    }


    private void initValues() {

        if (backgroundRes == 0) {
            backgroundRes = R.drawable.persian_date_picker_background;
        }

        if (boxBackgroundRes == 0) {
            boxBackgroundRes = R.drawable.persian_date_picker_box_background;
        }

        if (itemSpace == 0) {
            itemSpace = (int) getResources().getDimension(R.dimen.persianDatePickerItemSpace);
        }

        if (itemTextColor == 0) {
            itemTextColor = getResources().getColor(R.color.persianDatePickerItemTextColor);
        }

        if (itemTextSize == 0) {
            itemTextSize = (int) getResources().getDimension(R.dimen.persianDatePickerItemTextSize);
        }

        if (selectedItemTextColor == 0) {
            selectedItemTextColor = getResources().getColor(R.color.persianDatePickerSelectedItemTextColor);
        }

        if (jumpTodayTextColor == 0) {
            jumpTodayTextColor = getResources().getColor(R.color.persianDatePickerJumpTodayTextColor);
        }

        if (jumpTodayTextSize == 0) {
            jumpTodayTextSize = (int) getResources().getDimension(R.dimen.persianDatePickerJumpTodayTextSize);
        }

        if (jumpTodayText == null) {
            jumpTodayText = getResources().getString(R.string.persianDatePickerJumpToday);
        }

        if (buttonBackgroundRes == 0) {
            buttonBackgroundRes = R.color.persianDatePickerButtonBackground;
        }

        if (buttonText == null) {
            buttonText = getResources().getString(R.string.persianDatePickerButtonText);
        }

        if (buttonTextColor == 0) {
            buttonTextColor = getResources().getColor(R.color.persianDatePickerButtonTextColor);
        }

        if (buttonTextSize == 0) {
            buttonTextSize = (int) getResources().getDimension(R.dimen.persianDatePickerButtonTextSize);
        }

        if (font == null) {
            if (getActivity() != null) {
                font = Typeface.createFromAsset(getActivity().getAssets(), getResources().getString(R.string.itemFontPath));
            }
        }

        if (closeDrawable == null) {
            closeDrawable = getResources().getDrawable(R.drawable.ic_close);
        }

        if (closeTintColorRes == 0) {
            closeTintColorRes = R.color.persianDatePickerCloseColor;
        }

    }

    public void show(FragmentManager fragmentManager) {
        show(fragmentManager, getClass().getName());
    }


    private void setDialogAttribute() {
        clPersianDatePicker.setBackgroundResource(backgroundRes);
    }

    private void setBoxAttribute() {
        vBoxDay.setBackgroundResource(boxBackgroundRes);
        vBoxMonth.setBackgroundResource(boxBackgroundRes);
        vBoxYear.setBackgroundResource(boxBackgroundRes);
    }

    private void setWheelPickerAttribute() {
        wpYear.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {
                isLeapYear = LeapYearHelper.checkLeapYear(Integer.parseInt(years.get(position)));
                setDay(wpMonth.getCurrentItemPosition() + 1);
            }
        });
        wpMonth.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {
                setDay(position + 1);
            }
        });
        wpDay.setItemSpace(itemSpace);
        wpMonth.setItemSpace(itemSpace);
        wpYear.setItemSpace(itemSpace);
        wpDay.setItemTextColor(itemTextColor);
        wpMonth.setItemTextColor(itemTextColor);
        wpYear.setItemTextColor(itemTextColor);
        wpDay.setItemTextSize(itemTextSize);
        wpMonth.setItemTextSize(itemTextSize);
        wpYear.setItemTextSize(itemTextSize);
        wpDay.setSelectedItemTextColor(selectedItemTextColor);
        wpMonth.setSelectedItemTextColor(selectedItemTextColor);
        wpYear.setSelectedItemTextColor(selectedItemTextColor);
        wpDay.setTypeface(font);
        wpMonth.setTypeface(font);
        wpYear.setTypeface(font);
        wpDay.setCyclic(true);
        wpMonth.setCyclic(true);
        wpYear.setCyclic(true);
    }

    private void setJumpTodayAttribute() {
        btnJumpToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view12) {
                findToday(Calendar.getInstance().getTime(), true);
            }
        });
        btnJumpToday.setTextColor(jumpTodayTextColor);
        btnJumpToday.setTextSize(TypedValue.COMPLEX_UNIT_PX, jumpTodayTextSize);
        btnJumpToday.setTextSize(jumpTodayTextSize);
        btnJumpToday.setText(jumpTodayText);
        btnJumpToday.setTypeface(font);
    }

    private void setButtonAttribute() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                returnDate();
            }
        });
        btnSubmit.setBackgroundResource(buttonBackgroundRes);
        btnSubmit.setTextSize(TypedValue.COMPLEX_UNIT_PX, buttonTextSize);
        btnSubmit.setTextColor(buttonTextColor);
        btnSubmit.setText(buttonText);
        btnSubmit.setTypeface(font);
    }

    private void setCloseAttribute() {
        if (closeEnable) {
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
            btnClose.setVisibility(View.VISIBLE);
            if (getContext() != null) {
                btnClose.setColorFilter(ContextCompat.getColor(getContext(), closeTintColorRes), android.graphics.PorterDuff.Mode.SRC_IN);
            }
            btnClose.setImageDrawable(closeDrawable);
        } else {
            btnClose.setVisibility(View.GONE);
        }

    }


    public static class Builder {

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
        private int buttonTextSize = 0;
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

        public void setButtonTextSize(int buttonTextSize) {
            this.buttonTextSize = buttonTextSize;
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
            datePickerConfig.buttonTextSize = buttonTextSize;
            datePickerConfig.date = date;
            datePickerConfig.closeEnable = closeEnable;
            datePickerConfig.closeDrawable = closeDrawable;
            datePickerConfig.closeTintColorRes = closeTintColorRes;
            return datePickerConfig;
        }

    }


}