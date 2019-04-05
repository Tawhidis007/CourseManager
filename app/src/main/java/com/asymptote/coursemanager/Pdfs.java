package com.asymptote.coursemanager;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class Pdfs extends AppCompatActivity {

    String fname;
    ListView pdf_list;
    FloatingActionButton file_add_btn;
    private String kijinish;
    private String dir = "";
    final static int ACTIVITY_CHOOSE_FILE = 0;
    ArrayList<String> fname_list;
    Custom_Adapter custom_adapter;
    TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfs);
        Bundle bundle = getIntent().getExtras();
        kijinish = (String) bundle.get("kijinish");
        dir = (String) bundle.get("dir");
        tinyDB = new TinyDB(getApplicationContext());

        pdf_list = findViewById(R.id.pdf_list);
        file_add_btn = findViewById(R.id.file_add_btn);

        createdir();

        fname_list = new ArrayList<>();
        if (fname_list != null) {
            fname_list = dumdum();
        }
        custom_adapter = new Custom_Adapter(this, fname_list);
        pdf_list.setAdapter(custom_adapter);


        file_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean check = isExternalStorageWritable();
                Log.d("hridoy", String.valueOf(check));
                Log.d("hridoy", kijinish);
                Log.d("hridoy", dir + kijinish + "/");


                Intent chooseFile;
                Intent intent;
                chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
                chooseFile.setType("*/*");
                intent = Intent.createChooser(chooseFile, "Choose a file");
                startActivityForResult(intent, ACTIVITY_CHOOSE_FILE);
            }
        });

        pdf_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //must use fileprovider bullshit to open files from api24+
                File file = new File(dir + kijinish + "/" + fname_list.get(position));
                Uri uri = FileProvider.getUriForFile(getApplicationContext(),
                        BuildConfig.APPLICATION_ID + ".fileprovider", file);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(intent);
            }
        });

    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        String path = "";
        if (requestCode == ACTIVITY_CHOOSE_FILE) {
            Uri uri = data.getData();
            Log.d("uri", uri.getPath());
            fname = queryName(getContentResolver(), uri);
            Log.d("nuz", fname);
            fname_list.add(fname);
            custom_adapter.notifyDataSetChanged();
            InputStream in = null;
            try {
                in = getContentResolver().openInputStream(uri);
                OutputStream out = new FileOutputStream(new File(dir + kijinish + "/", fname)); // works only for pdf and images now
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.close();
                in.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private String queryName(ContentResolver resolver, Uri uri) {
        Cursor returnCursor =
                resolver.query(uri, null, null, null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        returnCursor.close();
        return name;
    }

    public ArrayList<String> dumdum() { //good heavens
        ArrayList<String> b = new ArrayList<>();
        File f = new File(dir + kijinish + "/");
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

    public void createdir() {
        File file = new File(dir + kijinish + "/");
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    //                //makes a text file in the specified directory yay
//                File f = new File(dir,"po.txt");
//                if(!f.exists()) {
//                    try {
//                        FileWriter fileWriter = new FileWriter(f);
//                        fileWriter.append("hridoy's first file here.");
//                        fileWriter.flush();
//                        fileWriter.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }


}