package com.example.kangjisung.likeroom.Util;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.CalendarView;

import com.example.kangjisung.likeroom.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class CalendarDialogBuilder extends AlertDialog.Builder {

    private Context context;
    private Calendar nowDate;
    private Calendar selectedDate;
    private CalendarView calendarView;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 M월 d일", Locale.KOREA);

    public CalendarDialogBuilder(Context context, String strDate) {
        super(context);
        this.context = context;

        View rootView = View.inflate(context, R.layout.layout_date, null);
        CalendarView calendarView = (CalendarView)rootView.findViewById(R.id.calendarView);

        try {
            nowDate.setTime(simpleDateFormat.parse(strDate));
            selectedDate = nowDate;

            long nowDateToLong = selectedDate.getTimeInMillis();
            calendarView.setDate(nowDateToLong, true, true);
            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                    selectedDate = new GregorianCalendar( year, month, dayOfMonth );
                }
            });
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}