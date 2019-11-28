package bpc.dis.persiandatepicker.PersianDatePicker;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

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

public class PersianDatePicker extends DialogFragment implements WheelPicker.OnItemSelectedListener {

    private WheelPicker wpDay;
    private WheelPicker wpMonth;
    private WheelPicker wpYear;
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

    private PersianDatePickerListener persianDatePickerListener = null;
    private Date date = null;

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

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initValues();

        View view = inflater.inflate(R.layout.persian_date_picker, container, false);
        if (getDialog() != null) {
            if (getDialog().getWindow() != null) {
                getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            }
        }

        wpDay = view.findViewById(R.id.wp_day);
        wpMonth = view.findViewById(R.id.wp_month);
        wpYear = view.findViewById(R.id.wp_year);
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
        setSubmitAttribute();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                PersianDatePicker.this.returnDate();
            }
        });
        btnJumpToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view12) {
                PersianDatePicker.this.findToday(Calendar.getInstance().getTime());
            }
        });
        wpYear.setOnItemSelectedListener(this);
        wpMonth.setOnItemSelectedListener(this);
        SolarCalendar solarCalendar = new SolarCalendar();
        isLeapYear = LeapYearHelper.checkLeapYear(1350);
        setYear(solarCalendar.year);
        setMonth();
        setDay(1);
        findToday(date);
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

    @Override
    public void onItemSelected(WheelPicker picker, Object data, int position) {
        if (picker.getId() == wpYear.getId()) {
            isLeapYear = LeapYearHelper.checkLeapYear(Integer.parseInt(years.get(position)));
            setDay(wpMonth.getCurrentItemPosition() + 1);
        } else if (picker.getId() == wpMonth.getId()) {
            setDay(position + 1);
        }
    }

    private void findToday(Date date) {
        SolarCalendar solarCalendar = new SolarCalendar(date);
        for (int i = 0; i < years.size(); i++)
            if (years.get(i).equals(String.valueOf(solarCalendar.year))) {
                wpYear.setSelectedItemPosition(i, false);
                break;
            }
        wpMonth.setSelectedItemPosition(solarCalendar.month - 1, false);
        wpDay.setSelectedItemPosition(solarCalendar.day - 1, false);
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

    private void setDialogAttribute() {
        clPersianDatePicker.setBackgroundResource(backgroundRes);
    }

    private void setBoxAttribute() {
        vBoxDay.setBackgroundResource(boxBackgroundRes);
        vBoxMonth.setBackgroundResource(boxBackgroundRes);
        vBoxYear.setBackgroundResource(boxBackgroundRes);
    }

    private void setWheelPickerAttribute() {
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
    }

    private void setJumpTodayAttribute() {
        btnJumpToday.setTextColor(jumpTodayTextColor);
        btnJumpToday.setTextSize(jumpTodayTextSize);
        btnJumpToday.setText(jumpTodayText);
        btnJumpToday.setTypeface(font);
    }

    private void setSubmitAttribute() {
        btnSubmit.setBackgroundResource(buttonBackgroundRes);
        btnSubmit.setTextColor(buttonTextColor);
        btnSubmit.setText(buttonText);
        btnSubmit.setTypeface(font);
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

        if (font == null) {
            font = Typeface.createFromAsset(getActivity().getAssets(), getResources().getString(R.string.itemFontPath));
        }

    }

    public void show() {
        show(getChildFragmentManager(), getClass().getName());
    }

    private void setPersianDatePickerListener(PersianDatePickerListener persianDatePickerListener) {
        this.persianDatePickerListener = persianDatePickerListener;
    }

    private void setBackgroundRes(int backgroundRes) {
        this.backgroundRes = backgroundRes;
    }

    private void setBoxBackgroundRes(int boxBackgroundRes) {
        this.boxBackgroundRes = boxBackgroundRes;
    }

    private void setItemSpace(int itemSpace) {
        this.itemSpace = itemSpace;
    }

    private void setItemTextColor(int itemTextColor) {
        this.itemTextColor = itemTextColor;
    }

    private void setItemTextSize(int itemTextSize) {
        this.itemTextSize = itemTextSize;
    }

    private void setSelectedItemTextColor(int selectedItemTextColor) {
        this.selectedItemTextColor = selectedItemTextColor;
    }

    private void setJumpTodayTextColor(int jumpTodayTextColor) {
        this.jumpTodayTextColor = jumpTodayTextColor;
    }

    private void setJumpTodayTextSize(int jumpTodayTextSize) {
        this.jumpTodayTextSize = jumpTodayTextSize;
    }

    private void setJumpTodayText(String jumpTodayText) {
        this.jumpTodayText = jumpTodayText;
    }

    private void setButtonBackgroundRes(int buttonBackgroundRes) {
        this.buttonBackgroundRes = buttonBackgroundRes;
    }

    private void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    private void setButtonTextColor(int buttonTextColor) {
        this.buttonTextColor = buttonTextColor;
    }

    private void setDate(Date date) {
        this.date = date;
    }

    private void setFont(Typeface font) {
        this.font = font;
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
        private String jumpToday = null;
        private String buttonText = null;
        private Typeface font = null;

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

        public Builder setJumpToday(String jumpToday) {
            this.jumpToday = jumpToday;
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

        public PersianDatePicker build() {
            PersianDatePicker datePickerConfig = new PersianDatePicker();
            datePickerConfig.setPersianDatePickerListener(persianDatePickerListener);
            datePickerConfig.setBackgroundRes(backgroundRes);
            datePickerConfig.setBoxBackgroundRes(boxBackgroundRes);
            datePickerConfig.setItemSpace(itemSpace);
            datePickerConfig.setFont(font);
            datePickerConfig.setItemTextColor(itemTextColor);
            datePickerConfig.setItemTextSize(itemTextSize);
            datePickerConfig.setSelectedItemTextColor(selectedItemTextColor);
            datePickerConfig.setJumpTodayTextColor(jumpTodayTextColor);
            datePickerConfig.setJumpTodayTextSize(jumpTodayTextSize);
            datePickerConfig.setJumpTodayText(jumpToday);
            datePickerConfig.setButtonBackgroundRes(buttonBackgroundRes);
            datePickerConfig.setButtonText(buttonText);
            datePickerConfig.setButtonTextColor(buttonTextColor);
            datePickerConfig.setDate(date);
            return datePickerConfig;
        }

    }

}