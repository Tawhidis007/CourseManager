package com.asymptote.coursemanager;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Recording extends AppCompatActivity {

    TextView record_msg_tv;
    Button start, stop;
    String dir, kijinish;
    ListView recording_lv;
    ArrayList<String> recorded_audio_list;
    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;
    public static final int REQUEST_AUDIO_PERMISSION_CODE = 1;
    ArrayAdapter<String> record_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);

        record_msg_tv = findViewById(R.id.record_msg_tv);
        start = findViewById(R.id.start_record);
        stop = findViewById(R.id.stop_record);
        recording_lv = findViewById(R.id.recording_lv);

        Bundle bundle = getIntent().getExtras();
        dir = bundle.getString("dir");
        kijinish = bundle.getString("kijinish");

        createdir();

        recorded_audio_list = new ArrayList<>();
        recorded_audio_list = dumdum2();

        record_adapter = new ArrayAdapter<>(getApplicationContext(),
                R.layout.record_adapter_holder,R.id.record_adapter_holder_tv,recorded_audio_list);
        recording_lv.setAdapter(record_adapter);


        Log.d("record", dir + kijinish + "/");
        stop.setEnabled(false);

        //bug is that initially the whole path is shown!
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermissions()) {
                    mRecorder = new MediaRecorder();
                    mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    mRecorder.setOutputFile( datetime());
                    recorded_audio_list.add( datetime());

                    try {
                        mRecorder.prepare();
                    } catch (IOException e) {
                        Log.e("record_check", "prepare() failed");
                    }
                    mRecorder.start();
                    stop.setEnabled(true);
                    start.setEnabled(false);
                    record_msg_tv.setText("Recording...");
                    Toast.makeText(getApplicationContext(), "Recording Started", Toast.LENGTH_LONG).show();
                } else {
                    requestPermissions();
                }
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecorder.stop();
                stop.setEnabled(false);
                start.setEnabled(true);
                record_adapter.notifyDataSetChanged();
                record_msg_tv.setText("Tap Start to Record..");
                mRecorder.release();
                mRecorder = null;
                Toast.makeText(getApplicationContext(), "Recording Stopped", Toast.LENGTH_LONG).show();

            }
        });

        recording_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(),Bajna.class);
                i.putExtra("filename",dir + kijinish + "/"+recorded_audio_list.get(position));
                i.putExtra("nn",recorded_audio_list.get(position));
                startActivity(i);
            }
        });
    }


    public void createdir() {
        File file = new File(dir + kijinish + "/");
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public String datetime() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        String d = dir+kijinish+ "/"+strDate + ".3gp";
        return d;
    }

    public boolean checkPermissions() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(Recording.this,
                new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE}, REQUEST_AUDIO_PERMISSION_CODE);
    }

    public ArrayList<String> dumdum2() { //good heavens
        ArrayList<String> b = new ArrayList<>();
        File f = new File(dir+kijinish+"/");
        File[] files = f.listFiles();
        if (files.length == 0) {
            return new ArrayList<>();
        } else {
            for (int i = 0; i < files.length; i++) {
                b.add(files[i].getName());
                Log.d("wtf", b.get(i));
            }
        }
        return b;
    }

}
