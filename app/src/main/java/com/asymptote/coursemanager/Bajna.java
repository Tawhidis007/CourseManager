package com.asymptote.coursemanager;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class Bajna extends AppCompatActivity {

    Button btn_play, btn_pause;
    TextView label_tv;
    private MediaPlayer mPlayer;
    String filename,nn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bajna);

        btn_pause = findViewById(R.id.btn_pause);
        btn_play = findViewById(R.id.btn_play);
        label_tv = findViewById(R.id.label_tv);

        Bundle bundle = getIntent().getExtras();
        filename = bundle.getString("filename");
        nn = bundle.getString("nn");
        Log.d("hriri", filename);
        btn_pause.setEnabled(false);
        label_tv.setText(nn);

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer = new MediaPlayer();
                try {
                    btn_play.setEnabled(false);
                    btn_pause.setEnabled(true);
                    mPlayer.setDataSource(filename);
                    mPlayer.prepare();
                    mPlayer.start();
                    label_tv.setText("Playing : "+nn);
                    Toast.makeText(Bajna.this, "Recording Started Playing", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    Log.e("record_check", "prepare() failed");
                }
            }
        });

        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_pause.setEnabled(false);
                mPlayer.release();
                label_tv.setText("Stopped : "+nn);
                mPlayer = null;
                btn_play.setEnabled(true);
            }
        });
    }
}
