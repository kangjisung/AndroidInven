package com.example.home7.customlistview;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.locallistview.R;

import java.util.Calendar;

import static com.example.locallistview.R.id.textViewStart;

/**
 * Created by park1 on 2016-12-27.
 */

public class DatePickerDialog extends Dialog {
    String msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date);
        CalendarView calendar = (CalendarView) findViewById(R.id.calendarView1);


        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                msg  = String.format("%d-%d-%d", year, month + 1, dayOfMonth);
                cancel();
            }
        });
    }


    public DatePickerDialog(Context context) {
        super(context);
    }

    public String getDate(){
        return msg;
    }
}
