package com.example.kangjisung.likeroom.Util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.TypedValue;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.kangjisung.likeroom.R;

public class Utility {

    public static String convertPhoneNumber(String rawPhone)
    {
        if (rawPhone.length() == 0){
            return rawPhone;
        }

        String phone = rawPhone;
        String[] strDDD = {"02" , "031", "032", "033", "041", "042", "043",
                "051", "052", "053", "054", "055", "061", "062",
                "063", "064", "010", "011", "012", "013", "015",
                "016", "017", "018", "019", "070"};

        if (phone.length() < 9) {
            return phone;
        } else if (phone.substring(0,2).equals(strDDD[0])) {
            phone = phone.substring(0,2) + '-' + phone.substring(2, phone.length()-4)
                    + '-' + phone.substring(phone.length() -4, phone.length());
        } else {
            for(int i=1; i < strDDD.length; i++) {
                if (phone.substring(0,3).equals(strDDD[i])) {
                    phone = phone.substring(0,3) + '-' + phone.substring(3, phone.length()-4)
                            + '-' + phone.substring(phone.length() -4, phone.length());
                }
            }
        }
        return phone;
    }

    public static void hideSoftKeyboard(Activity activity) {
        try{
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public static void showKeyboard(Context context) {
        ((InputMethodManager) (context).getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.SHOW_IMPLICIT);

    }

    public static int convertDpToPixels(float dp, Context context) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
        return px;
    }

    public static TextView getAlertDialogTitle(String titleName, Context context){
        TextView title = new TextView(context);
        title.setBackgroundColor(ColorTheme.getThemeColorRGB(context, R.attr.theme_color_L3));
        title.setTextColor(ColorTheme.getThemeColorRGB(context, R.attr.theme_color_D2));
        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
        title.setPadding(convertDpToPixels(10, context), // LEFT
                convertDpToPixels(15, context), // TOP
                convertDpToPixels(10, context), // RIGHT
                convertDpToPixels(15, context));// BOTTOM;
        title.setText(titleName);
        return title;
    }

    public static Bitmap blur(Context context, Bitmap sentBitmap, float radius) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

            final RenderScript rs = RenderScript.create(context);
            final Allocation input = Allocation.createFromBitmap(rs, sentBitmap, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
            final Allocation output = Allocation.createTyped(rs, input.getType());
            final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            script.setRadius(radius); //0.0f ~ 25.0f
            script.setInput(input);
            script.forEach(output);
            output.copyTo(bitmap);
            return bitmap;
        }
        return sentBitmap;
    }
}
