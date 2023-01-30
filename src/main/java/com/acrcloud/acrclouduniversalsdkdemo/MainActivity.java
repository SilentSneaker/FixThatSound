package com.acrcloud.acrclouduniversalsdkdemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.acrcloud.rec.*;
import com.acrcloud.rec.utils.ACRCloudLogger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IACRCloudListener, IACRCloudRadioMetadataListener {
    int change;
    private final static String TAG = "MainActivity";
    private TextView mVolume, mResult, tv_time;

    public static Car car;
    public static String  Result;
    public static String Fix;
    private boolean mProcessing = false;
    private boolean mAutoRecognizing = false;
    private boolean initState = false;

    private MediaPlayer mediaPlayer = new MediaPlayer();
    private boolean isPlaying = false;

    private String path = "";

    private long startTime = 0;
    private long stopTime = 0;

    private final int PRINT_MSG = 1001;

    private ACRCloudConfig mConfig = null;
    private ACRCloudClient mClient = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        path = Environment.getExternalStorageDirectory().toString()
                + "/acrcloud";
        Log.e(TAG, path);

        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }

        mVolume = (TextView) findViewById(R.id.volume);
        mResult = (TextView) findViewById(R.id.result);
        tv_time = (TextView) findViewById(R.id.time);
        ImageView home = findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Result = (String) mResult.getText();
                startActivity(new Intent(MainActivity.this, ResultScreen.class));
            }
        });

        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                start();
            }
        });

        findViewById(R.id.cancel).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        cancel();

                    }
                });

        findViewById(R.id.request_radio_meta).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestRadioMetadata();
            }
        });
        Switch sb = findViewById(R.id.auto_switch);
        sb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    openAutoRecognize();
                } else {
                    closeAutoRecognize();
                }
            }
        });
        verifyPermissions();

        this.mConfig = new ACRCloudConfig();

        this.mConfig.acrcloudListener = this;
        this.mConfig.context = this;

        // Please create project in "http://console.acrcloud.cn/service/avr".
        this.mConfig.host = "identify-eu-west-1.acrcloud.com";
        this.mConfig.accessKey = "7750fa0e9c09a9af33e903cb3cf7b099";
        this.mConfig.accessSecret = "FksbMnOLxerJvnm49Dot9TFU6kZzeUUElRB9oYWp";

        // auto recognize access key
        this.mConfig.hostAuto = "identify-eu-west-1.acrcloud.com";
        this.mConfig.accessKeyAuto = "7750fa0e9c09a9af33e903cb3cf7b099";
        this.mConfig.accessSecretAuto = "FksbMnOLxerJvnm49Dot9TFU6kZzeUUElRB9oYWp";

        this.mConfig.recorderConfig.rate = 8000;
        this.mConfig.recorderConfig.channels = 1;

        this.mConfig.acrcloudPartnerDeviceInfo = new IACRCloudPartnerDeviceInfo() {
            @Override
            public String getGPS() {
                return null;
            }

            @Override
            public String getRadioFrequency() {
                return null;
            }

            @Override
            public String getDeviceId() {
                return "";
            }

            @Override
            public String getDeviceModel() {
                return null;
            }
        };

        // If you do not need volume callback, you set it false.
        this.mConfig.recorderConfig.isVolumeCallback = true;

        this.mClient = new ACRCloudClient();
        ACRCloudLogger.setLog(true);

        this.initState = this.mClient.initWithConfig(this.mConfig);
    }

    public void start() {
        if (!this.initState) {
            Toast.makeText(this, "init error", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!mProcessing) {
            mProcessing = true;
            mVolume.setText("");
            mResult.setText("Listening Please wait.");
            if (this.mClient == null || !this.mClient.startRecognize()) {
                mProcessing = false;
                mResult.setText("start error!");
            }
            ImageButton button = findViewById(R.id.start);

            startTime = System.currentTimeMillis();
        }
    }

    public void cancel() {
        if (mProcessing && this.mClient != null) {
            this.mClient.cancel();
        }

        this.reset();
    }

    public void openAutoRecognize() {
        String str = this.getString(R.string.auto_switch_open);
        if (!mAutoRecognizing) {
            mAutoRecognizing = true;
            if (this.mClient == null || !this.mClient.runAutoRecognize()) {
                mAutoRecognizing = true;
                str = this.getString(R.string.error);
            }
        }
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    public void closeAutoRecognize() {
        String str = this.getString(R.string.auto_switch_close);
        if (mAutoRecognizing) {
            mAutoRecognizing = false;
            this.mClient.cancelAutoRecognize();
            str = this.getString(R.string.error);
        }
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    // callback IACRCloudRadioMetadataListener
    public void requestRadioMetadata() {
        String lat = "39.98";
        String lng = "116.29";
        List<String> freq = new ArrayList<>();
        freq.add("88.7");
        if (!this.mClient.requestRadioMetadataAsyn(lat, lng, freq,
                ACRCloudConfig.RadioType.FM, this)) {
            String str = this.getString(R.string.error);
            Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        }
    }

    public void reset() {
        tv_time.setText("Time: ");
        mResult.setText("Problem: ");
        mProcessing = false;
    }

    @Override
    public void onResult(ACRCloudResult results) {
        this.reset();

	// If you want to save the record audio data, you can refer to the following codes.
	/*
	byte[] recordPcm = results.getRecordDataPCM();
        if (recordPcm != null) {
            byte[] recordWav = ACRCloudUtils.pcm2Wav(recordPcm, this.mConfig.recorderConfig.rate, this.mConfig.recorderConfig.channels);
            ACRCloudUtils.createFileWithByte(recordWav, path + "/" + "record.wav");
        }
	*/

        String result = results.getResult();

        String tres = "\n";

        try {
            JSONObject j = new JSONObject(result);
            JSONObject j1 = j.getJSONObject("status");
            int j2 = j1.getInt("code");
            if(j2 == 0){
                JSONObject metadata = j.getJSONObject("metadata");
                //
                if (metadata.has("music")) {
                    JSONArray musics = metadata.getJSONArray("music");
                    for(int i=0; i<musics.length(); i++) {
                        JSONObject tt = (JSONObject) musics.get(i);
                        String title = tt.getString("title");
                        JSONArray artistt = tt.getJSONArray("artists");
                        JSONObject art = (JSONObject) artistt.get(0);
                        String artist = art.getString("name");
                        tres = /*tres + (i+1) + ".  Error: " + */title/* + "    Artist: " + artist + "\n"*/;
                        Fix = car.make+ " "+ car.model+ " " +"https://www.google.com/search?q="+title.replaceAll(" ", "+") + "+repair";
                    }
                }

                tres = tres + "\n\n" + result;
            }else{
                tres = "No Result Found";
            }
        } catch (JSONException e) {
            tres = result;
            e.printStackTrace();
        }
        if(tres == "No Result Found") {
            mResult.setClickable(false);
            mResult.setText(tres);
        }
        else {
            mResult.setText(result);
            mResult.setClickable(true);
        }

        startTime = System.currentTimeMillis();
    }

    @Override
    public void onVolumeChanged(double volume) {
        long time = (System.currentTimeMillis() - startTime) / 1000;
        mVolume.setText(getResources().getString(R.string.volume) + volume + "\n\nTime: " + time + " s");
        tv_time.setText("Time: "+time);

    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS = {
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.RECORD_AUDIO
    };
    public void verifyPermissions() {
        for (int i=0; i<PERMISSIONS.length; i++) {
            int permission = ActivityCompat.checkSelfPermission(this, PERMISSIONS[i]);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS,
                        REQUEST_EXTERNAL_STORAGE);
                break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.e("MainActivity", "release");
        if (this.mClient != null) {
            this.mClient.release();
            this.initState = false;
            this.mClient = null;
        }
    }

    @Override
    public void onRadioMetadataResult(String s) {
        mResult.setText(s);
        mResult.setClickable(true);
    }

    public void Profile(View view) {
        startActivity(new Intent(MainActivity.this, Maintenance.class));
    }
}
