package com.acrcloud.acrclouduniversalsdkdemo;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class Maintenance extends AppCompatActivity {


    static int changeOil, changeAirFilter,changeTransmission;
    private int oilDay, oilMonth, oilYear;
    private int transmissionDay,transmissionMonth,transmissionYear;
    private int airFilterDay, airFilterMonth, airFilterYear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);
        TextView oil = findViewById(R.id.OilTextDate);
        TextView transmission = findViewById(R.id.transmission);
        TextView airFilter = findViewById(R.id.battery);

        oil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                oilDay = calendar.get(Calendar.DATE);
                oilMonth = calendar.get(Calendar.MONTH);
                oilYear = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Maintenance.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SuspiciousIndentation")
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Date a = new Date(year, month, day);
                        Date b = new Date(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
                        Long c = ((b.getTime()-a.getTime())/1000/60/60/24) - 31;
                        changeOil = Math.toIntExact(c);
                        oilYear = year;
                        oilDay = day;
                        oilMonth = month;
                        if (changeOil < 90)
                        oil.setText("Last oil change was "+ c + " Day(s) ago.");
                        else
                        oil.setText("Last oil change was "+ c + " Day(s) ago and needs to be changed");

                    }
                }, oilYear, oilMonth, oilDay );
                datePickerDialog.show();
            }
        });
        transmission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                transmissionDay = calendar.get(Calendar.DATE);
                transmissionMonth = calendar.get(Calendar.MONTH);
                transmissionYear = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Maintenance.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Date a = new Date(year, month, day);
                        Date b = new Date(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
                        Long c = ((b.getTime()-a.getTime())/1000/60/60/24) - 31;
                        changeTransmission = Math.toIntExact(c);
                        transmissionYear = year;
                        transmissionDay = day;
                        transmissionMonth = month;
                        if (changeTransmission < 1460)
                            transmission.setText("Last Transmission Fluid change was "+ c + " Day(s) ago.");
                        else
                            transmission.setText("Last Transmission Fluid change was "+ c + " Day(s) ago and needs to be changed");

                    }
                }, transmissionYear, transmissionMonth, transmissionDay );
                datePickerDialog.show();
            }
        });
        airFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                airFilterDay = calendar.get(Calendar.DATE);
                airFilterMonth = calendar.get(Calendar.MONTH);
                airFilterYear = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Maintenance.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Date a = new Date(year, month, day);
                        Date b = new Date(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
                        Long c = ((b.getTime()-a.getTime())/1000/60/60/24) - 31;
                        changeAirFilter = Math.toIntExact(c);
                        airFilterYear = year;
                        airFilterDay = day;
                        airFilterMonth = month;
                        if (changeAirFilter<180)
                        airFilter.setText("Last Air Filter change was "+ c + " Day(s) ago.");
                        else
                            airFilter.setText("Last Air Filter change was "+ c + " Day(s) ago and needs to be changed");


                    }
                }, airFilterYear, airFilterMonth, airFilterDay);
                datePickerDialog.show();
            }
        });

    }


}
