package com.acrcloud.acrclouduniversalsdkdemo;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Maintenance extends AppCompatActivity {


    TextView oil = findViewById(R.id.OilTextDate);
    private int oilDay, oilMonth, oilYear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);
        oil.setText("Last oil change was: "+ LocalDate.now().minusDays(oilDay).minusMonths(oilMonth).minusYears(oilYear));


    }

    void pullUpCalendar(View view) {
        final Calendar calendar = Calendar.getInstance();
        oilDay = calendar.get(Calendar.DATE);
        oilMonth = calendar.get(Calendar.MONTH);
        oilYear = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(Maintenance.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                oil.setText("Last oil change was: "+ LocalDate.now().minusDays(oilDay).minusMonths(oilMonth).minusYears(oilYear));
                oilYear = year;
                oilDay = day;
                oilMonth = month;
            }
        }, oilYear, oilMonth, oilDay );
        datePickerDialog.show();
    }


}
