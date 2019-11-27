package bpc.dis.persiandatepicker.PersianCalendar;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import bpc.dis.persiandatepicker.R;
import bpc.dis.persiandatepicker.Utils.DialogSizeHelper.DialogSizeHelper;
import bpc.dis.persiandatepicker.Utils.JalaliCalendar.JalaliCalendar;
import bpc.dis.persiandatepicker.Utils.LeapYearHelper.LeapYearHelper;
import bpc.dis.persiandatepicker.Utils.SolarCalendar.SolarCalendar;
import bpc.dis.persiandatepicker.Utils.WheelPicker.WheelPicker;

public class PersianDatePicker extends DialogFragment implements WheelPicker.OnItemSelectedListener {

    private WheelPicker wpDay;
    private WheelPicker wpMonth;
    private WheelPicker wpYear;
    private PersianDatePickerListener persianDatePickerListener;
    private List<String> years = new ArrayList<>();
    private List<String> month = new ArrayList<>();
    private List<String> day = new ArrayList<>();
    private boolean isLeapYear;
    private Date date;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.persian_date_picker, container, false);
        if (getDialog() != null)
            if (getDialog().getWindow() != null) {
                getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            }

        wpDay = view.findViewById(R.id.wp_day);
        wpMonth = view.findViewById(R.id.wp_month);
        wpYear = view.findViewById(R.id.wp_year);
        AppCompatButton btnSubmit = view.findViewById(R.id.btn_submit);
        AppCompatButton btnJumpToday = view.findViewById(R.id.btn_jump_today);

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
        if (date == null) {
            date = new Date();
        }
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

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPersianDatePickerListener(PersianDatePickerListener persianDatePickerListener) {
        this.persianDatePickerListener = persianDatePickerListener;
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
        if (persianDatePickerListener == null) {
            Toast.makeText(getContext(), "Please set persianDatePickerListener", Toast.LENGTH_SHORT).show();
            return;
        }
        persianDatePickerListener.selectedDate(new JalaliCalendar().getGregorianDate(String.format("%s/%s/%s", years.get(wpYear.getCurrentItemPosition()), String.valueOf(wpMonth.getCurrentItemPosition() + 1), day.get(wpDay.getCurrentItemPosition()))));
        dismiss();
    }

}