package bpc.dis.persiandatepicker.Utils.SolarCalendar;

import java.util.Date;

public class SolarCalendar {

    public String strWeekDay = "";
    public String strMonth = "";
    public int day;
    public int month;
    public int year;

    public SolarCalendar() {
        Date GeorgianDate = new Date();
        calcSolarCalendar(GeorgianDate);
    }

    public SolarCalendar(Date GeorgianDate) {
        calcSolarCalendar(GeorgianDate);
    }

    private void calcSolarCalendar(Date GeorgianDate) {
        int ld;
        int GeorgianYear = GeorgianDate.getYear() + 1900;
        int GeorgianMonth = GeorgianDate.getMonth() + 1;
        int GeorgianDay = GeorgianDate.getDate();
        int WeekDay = GeorgianDate.getDay();
        int[] buf1 = new int[12];
        int[] buf2 = new int[12];
        buf1[0] = 0;
        buf1[1] = 31;
        buf1[2] = 59;
        buf1[3] = 90;
        buf1[4] = 120;
        buf1[5] = 151;
        buf1[6] = 181;
        buf1[7] = 212;
        buf1[8] = 243;
        buf1[9] = 273;
        buf1[10] = 304;
        buf1[11] = 334;
        buf2[0] = 0;
        buf2[1] = 31;
        buf2[2] = 60;
        buf2[3] = 91;
        buf2[4] = 121;
        buf2[5] = 152;
        buf2[6] = 182;
        buf2[7] = 213;
        buf2[8] = 244;
        buf2[9] = 274;
        buf2[10] = 305;
        buf2[11] = 335;
        if ((GeorgianYear % 4) != 0) {
            day = buf1[GeorgianMonth - 1] + GeorgianDay;
            if (day > 79) {
                day = day - 79;
                if (day <= 186) {
                    if (day % 31 == 0) {
                        month = day / 31;
                        day = 31;
                    } else {
                        month = (day / 31) + 1;
                        day = (day % 31);
                    }
                    year = GeorgianYear - 621;
                } else {
                    day = day - 186;

                    if (day % 30 == 0) {
                        month = (day / 30) + 6;
                        day = 30;
                    } else {
                        month = (day / 30) + 7;
                        day = (day % 30);
                    }
                    year = GeorgianYear - 621;
                }
            } else {
                if ((GeorgianYear > 1996) && (GeorgianYear % 4) == 1) {
                    ld = 11;
                } else {
                    ld = 10;
                }
                day = day + ld;

                if (day % 30 == 0) {
                    month = (day / 30) + 9;
                    day = 30;
                } else {
                    month = (day / 30) + 10;
                    day = (day % 30);
                }
                year = GeorgianYear - 622;
            }
        } else {
            day = buf2[GeorgianMonth - 1] + GeorgianDay;

            if (GeorgianYear >= 1996) {
                ld = 79;
            } else {
                ld = 80;
            }
            if (day > ld) {
                day = day - ld;

                if (day <= 186) {
                    if (day % 31 == 0) {
                        month = (day / 31);
                        day = 31;
                    } else {
                        month = (day / 31) + 1;
                        day = (day % 31);
                    }
                    year = GeorgianYear - 621;
                } else {
                    day = day - 186;

                    if (day % 30 == 0) {
                        month = (day / 30) + 6;
                        day = 30;
                    } else {
                        month = (day / 30) + 7;
                        day = (day % 30);
                    }
                    year = GeorgianYear - 621;
                }
            } else {
                day = day + 10;

                if (day % 30 == 0) {
                    month = (day / 30) + 9;
                    day = 30;
                } else {
                    month = (day / 30) + 10;
                    day = (day % 30);
                }
                year = GeorgianYear - 622;
            }
        }
        switch (month) {
            case 1:
                strMonth = "فروردين";
                break;
            case 2:
                strMonth = "ارديبهشت";
                break;
            case 3:
                strMonth = "خرداد";
                break;
            case 4:
                strMonth = "تير";
                break;
            case 5:
                strMonth = "مرداد";
                break;
            case 6:
                strMonth = "شهريور";
                break;
            case 7:
                strMonth = "مهر";
                break;
            case 8:
                strMonth = "آبان";
                break;
            case 9:
                strMonth = "آذر";
                break;
            case 10:
                strMonth = "دي";
                break;
            case 11:
                strMonth = "بهمن";
                break;
            case 12:
                strMonth = "اسفند";
                break;
        }
        switch (WeekDay) {
            case 0:
                strWeekDay = "يکشنبه";
                break;
            case 1:
                strWeekDay = "دوشنبه";
                break;
            case 2:
                strWeekDay = "سه شنبه";
                break;
            case 3:
                strWeekDay = "چهارشنبه";
                break;
            case 4:
                strWeekDay = "پنج شنبه";
                break;
            case 5:
                strWeekDay = "جمعه";
                break;
            case 6:
                strWeekDay = "شنبه";
                break;
        }
    }

}