package com.asymptote.coursemanager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class LandingPage extends AppCompatActivity {

    RecyclerView landing_rv;
    RecyclerView.LayoutManager lm2;
    ArrayList<String> category;
    private static final int REQ_CODE = 1;
    String course_name = "";
    String course_path = "";
    String p = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        landing_rv = findViewById(R.id.landing_rv);
        Bundle bundle = getIntent().getExtras();
        course_name = bundle.getString("name");
        course_path = bundle.getString("path");
        Log.d("tag2", course_path);
        Log.d("tag2", course_name.toString());


        category = new ArrayList<>();
        category.add("Audio Lectures");
        category.add("Books");
        category.add("About Instructor");


        Second_Adapter second_adapter = new Second_Adapter(getApplicationContext(), category);
        lm2 = new GridLayoutManager(this,1,GridLayoutManager.VERTICAL,false);
        landing_rv.setLayoutManager(lm2);
        landing_rv.setAdapter(second_adapter);

        testdircreation();

        second_adapter.setListener2(new Second_Adapter.bridge2() {
            @Override
            public void listener2(int position) {
                if (category.get(position) == "Audio Lectures") {
                    Intent ii = new Intent(LandingPage.this,Recording.class);
                    ii.putExtra("dir",p);
                    ii.putExtra("kijinish", category.get(position));
                    startActivity(ii);
                } else if(category.get(position)=="Books") {
                    Intent i = new Intent(LandingPage.this, Pdfs.class);
                    i.putExtra("kijinish", category.get(position));
                    i.putExtra("dir", p);
                    startActivity(i);
                }

            }
        });

    }

    public void testdircreation() {
        p = Environment.getExternalStorageDirectory().
                getAbsolutePath() + "/Android/data/com.asymptote.coursemanager/" + course_name + "/";
        Log.d("file", p);
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQ_CODE);
            }
        } else {
            File file = new File(p);
            if (!file.exists()) {
                file.mkdirs();
            }

        }
    }
}
