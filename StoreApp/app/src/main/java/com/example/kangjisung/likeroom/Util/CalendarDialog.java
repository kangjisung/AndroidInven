package com.example.kangjisung.likeroom.Util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.CalendarView;

import com.example.kangjisung.likeroom.R;

public class CalendarDialog extends Dialog {
    String msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_date);
        CalendarView calendar = (CalendarView) findViewById(R.id.calendarView);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                msg  = String.format("%d-%d-%d", year, month + 1, dayOfMonth);
                cancel();
            }
        });
    }


    public CalendarDialog(Context context) {
        super(context);
    }

    public String getDate(){
        return msg;
    }
}
