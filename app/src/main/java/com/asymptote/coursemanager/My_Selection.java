package com.asymptote.coursemanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class My_Selection extends AppCompatActivity {

    ListView selection_listview;
    ArrayAdapter<String> selection_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__selection);
        selection_listview = findViewById(R.id.selection_lv);
        if(MainActivity.o!=null){
            selection_adapter = new ArrayAdapter<>(My_Selection.this,android.R.layout.simple_list_item_1,MainActivity.o);
            selection_listview.setAdapter(selection_adapter);
        }
        selection_listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.o.remove(position);
                selection_adapter.notifyDataSetChanged();
                return true;
            }
        });
        selection_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(My_Selection.this,"Press and Hold to remove a course.",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
