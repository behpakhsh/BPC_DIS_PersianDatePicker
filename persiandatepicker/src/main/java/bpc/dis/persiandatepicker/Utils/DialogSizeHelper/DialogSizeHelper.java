package bpc.dis.persiandatepicker.Utils.DialogSizeHelper;

import android.graphics.Point;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;

public class DialogSizeHelper {

    public static void defineDialogSize(Window window, double widthSize, double heightSize) {
        if (window != null) {
            Point point = new Point();
            Display display = window.getWindowManager().getDefaultDisplay();
            display.getSize(point);
            window.setLayout((int) (point.x * widthSize), (int) (point.y * heightSize));
            window.setGravity(Gravity.CENTER);
        }
    }

    public static void defineDialogSizeWrap(Window window, double widthSize, int height) {
        if (window != null) {
            Point point = new Point();
            Display display = window.getWindowManager().getDefaultDisplay();
            display.getSize(point);
            window.setLayout((int) (point.x * widthSize), height);
            window.setGravity(Gravity.CENTER);
        }
    }

}