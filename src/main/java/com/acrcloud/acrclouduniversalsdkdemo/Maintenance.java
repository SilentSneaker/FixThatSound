package com.acrcloud.acrclouduniversalsdkdemo;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class Maintenance extends AppCompatActivity {


    static int changeOil, changeAirFilter,changeTransmission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);
        TextView oil = findViewById(R.id.OilTextDate);
        TextView transmission = findViewById(R.id.transmission);
        TextView airFilter = findViewById(R.id.AirFilter);
        TextView makeModel = findViewById(R.id.MakeModel);
        ImageView home = findViewById(R.id.home);
        ImageView profile = findViewById(R.id.Profile);
        // On open days till change
        {
            if(Car.oilYear != 0)
            {
                Date a = new Date(Car.oilYear, Car.oilMonth, Car.oilDay);
                Date b = new Date(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
                Long c = ((b.getTime() - a.getTime()) / 1000 / 60 / 60 / 24) - 31;
                if (changeOil < 90)
                    oil.setText("Last oil change was " + c + " Day(s) ago.");
                else
                    oil.setText("Last oil change was " + c + " Day(s) ago and needs to be changed");
            }
            if(Car.transmissionYear != 0)
            {
                Date a = new Date(Car.transmissionYear, Car.transmissionMonth, Car.transmissionDay);
                Date b = new Date(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
                Long c = ((b.getTime() - a.getTime()) / 1000 / 60 / 60 / 24) - 31;
                if (changeTransmission < 1460)
                    transmission.setText("Last Transmission Fluid change was " + c + " Day(s) ago.");
                else
                    transmission.setText("Last Transmission Fluid change was " + c + " Day(s) ago and needs to be changed");
            }
            if(Car.airFilterYear != 0)
            {
                Date a = new Date(Car.airFilterYear, Car.airFilterMonth, Car.airFilterDay);
                Date b = new Date(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
                Long c = ((b.getTime() - a.getTime()) / 1000 / 60 / 60 / 24) - 31;
                if (changeAirFilter < 180)
                    airFilter.setText("Last Air Filter change was " + c + " Day(s) ago.");
                else
                    airFilter.setText("Last Air Filter change was " + c + " Day(s) ago and needs to be changed");
            }
        }


        makeModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Maintenance.this);
                builder.setTitle("Enter Make of your Vehicle");
                final EditText make = new EditText(Maintenance.this);
                builder.setView(make);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Car.make = make.getText().toString();
                        makeModel.setText(Car.make + Car.model);
                        AlertDialog.Builder modelBuilder = new AlertDialog.Builder(Maintenance.this);
                        modelBuilder.setTitle("Enter Model of your Vehicle");
                        final EditText model = new EditText(Maintenance.this);
                        modelBuilder.setView(model);
                        modelBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface Dialog, int which) {
                                Car.model = model.getText().toString();
                                makeModel.setText(Car.make+ " " + Car.model);
                            }
                        });
                        modelBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        modelBuilder.show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        oil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                Car.oilDay = calendar.get(Calendar.DATE);
                Car.oilMonth = calendar.get(Calendar.MONTH);
                Car.oilYear = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Maintenance.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Date a = new Date(year, month, day);
                        Date b = new Date(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
                        Long c = ((b.getTime()-a.getTime())/1000/60/60/24) - 31;
                        changeOil = Math.toIntExact(c);
                        Car.oilYear = year;
                        Car.oilDay = day;
                        Car.oilMonth = month;
                        if (changeOil < 90)
                            oil.setText("Last oil change was "+ c + " Day(s) ago.");
                        else
                            oil.setText("Last oil change was "+ c + " Day(s) ago and needs to be changed");

                    }
                }, Car.oilYear, Car.oilMonth, Car.oilDay );
                datePickerDialog.show();
            }
        });
        transmission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                Car.transmissionDay = calendar.get(Calendar.DATE);
                Car.transmissionMonth = calendar.get(Calendar.MONTH);
                Car.transmissionYear = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Maintenance.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Date a = new Date(year, month, day);
                        Date b = new Date(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
                        Long c = ((b.getTime()-a.getTime())/1000/60/60/24) - 31;
                        changeTransmission = Math.toIntExact(c);
                        Car.transmissionYear = year;
                        Car.transmissionDay = day;
                        Car.transmissionMonth = month;
                        if (changeTransmission < 1460)
                            transmission.setText("Last Transmission Fluid change was "+ c + " Day(s) ago.");
                        else
                            transmission.setText("Last Transmission Fluid change was "+ c + " Day(s) ago and needs to be changed");

                    }
                }, Car.transmissionYear, Car.transmissionMonth, Car.transmissionDay );
                datePickerDialog.show();
            }
        });
        airFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                Car.airFilterDay = calendar.get(Calendar.DATE);
                Car.airFilterMonth = calendar.get(Calendar.MONTH);
                Car.airFilterYear = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Maintenance.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Date a = new Date(year, month, day);
                        Date b = new Date(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
                        Long c = ((b.getTime()-a.getTime())/1000/60/60/24) - 31;
                        changeAirFilter = Math.toIntExact(c);
                        Car.airFilterYear = year;
                        Car.airFilterDay = day;
                        Car.airFilterMonth = month;
                        if (changeAirFilter<180)
                        airFilter.setText("Last Air Filter change was "+ c + " Day(s) ago.");
                        else
                            airFilter.setText("Last Air Filter change was "+ c + " Day(s) ago and needs to be changed");


                    }
                }, Car.airFilterYear, Car.airFilterMonth, Car.airFilterDay);
                datePickerDialog.show();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Maintenance.this, MainActivity.class));
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


}
