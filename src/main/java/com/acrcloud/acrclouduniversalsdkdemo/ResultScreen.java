package com.acrcloud.acrclouduniversalsdkdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_screen);
        TextView result = (TextView) findViewById(R.id.result);
        result.setText(MainActivity.Result);
    }
}