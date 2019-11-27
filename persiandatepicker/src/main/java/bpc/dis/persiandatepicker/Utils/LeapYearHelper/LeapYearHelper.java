package bpc.dis.persiandatepicker.Utils.LeapYearHelper;

public class LeapYearHelper {

    public static boolean checkLeapYear(int year) {
        int a = 0, b = 1309;
        for (int i = 1309; i <= year - 4; i += 4) {
            b += 4;
            a += 1;
            if (a % 8 == 0)
                b++;
        }
        return year == b;
    }

}