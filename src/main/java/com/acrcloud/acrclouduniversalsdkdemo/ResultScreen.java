package com.acrcloud.acrclouduniversalsdkdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_screen);
        TextView result = (TextView) findViewById(R.id.result);
        result.setText(MainActivity.Result);
    }

    public void Profile(View view) {
        startActivity(new Intent(ResultScreen.this, Maintenance.class));
    }
}