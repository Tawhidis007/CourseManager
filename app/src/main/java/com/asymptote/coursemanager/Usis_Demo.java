package com.asymptote.coursemanager;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class Usis_Demo extends AppCompatActivity {

    RecyclerView usis_rv;
    Usis_Adapter usis_adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<UsisTable> usis_list;
    final static int req1 = 1;
    public String a = "0";
    EditText day_et, month_et, hour_et, min_et, year_et;
    AlarmManager am;
    PendingIntent pi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usis__demo);

        usis_rv = findViewById(R.id.usis_rv);
        usis_list = new ArrayList<>();


        if (AppDatabase3.getAppDatabase3(getApplicationContext()).usisDAO().count_courses() == 0) {
            setUpUsis();
        }
        usis_list = (ArrayList<UsisTable>) AppDatabase3.getAppDatabase3(getApplicationContext()).usisDAO().getAll();

        usis_adapter = new Usis_Adapter(Usis_Demo.this, usis_list);
        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        usis_rv.setLayoutManager(layoutManager);
        usis_rv.setAdapter(usis_adapter);

    }

    private static UsisTable addCourses(final AppDatabase3 db, UsisTable usisTable) {
        db.usisDAO().insert(usisTable);
        return usisTable;
    }

    private void setUpUsis() {
        addCourses(AppDatabase3.getAppDatabase3(getApplicationContext()),
                new UsisTable("Cse110", "Programming-I", "DIP", 1, 40, 0,
                        40, "sun/tues", "930-11", "Thu", "8-11"));
        addCourses(AppDatabase3.getAppDatabase3(getApplicationContext()),
                new UsisTable("Cse111", "Programming-II", "RAA", 1, 40, 0,
                        40, "sun/tues", "11-1220", "Thu", "8-11"));
        addCourses(AppDatabase3.getAppDatabase3(getApplicationContext()),
                new UsisTable("Cse220", "Data Structures", "SMK", 1, 40, 5,
                        35, "mon/wed", "930-11", "Wed", "2-5"));
        addCourses(AppDatabase3.getAppDatabase3(getApplicationContext()),
                new UsisTable("Cse221", "Algorithms", "MMM", 1, 40, 40,
                        0, "mon/wed", "8-920", "Thu", "11-2"));
        addCourses(AppDatabase3.getAppDatabase3(getApplicationContext()),
                new UsisTable("Cse420", "Compiler", "DSH", 1, 40, 20,
                        20, "sun/tues", "5-620", "Wed", "2-5"));
        addCourses(AppDatabase3.getAppDatabase3(getApplicationContext()),
                new UsisTable("Cse422", "Artificial Intelligence", "IFM", 1, 40, 33,
                        7, "mon/wed", "11-1220", "sat", "8-11"));
        addCourses(AppDatabase3.getAppDatabase3(getApplicationContext()),
                new UsisTable("Cse331", "Automata Theory", "RAK", 1, 40, 0,
                        40, "thu/sat", "1230-2", "Wed", "8-11"));
        addCourses(AppDatabase3.getAppDatabase3(getApplicationContext()),
                new UsisTable("Cse427", "Machine Learning", "MAM", 1, 40, 0,
                        40, "mon/wed", "330-5", "N/A", "N/A"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.alarm) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Usis_Demo.this);
            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.alarm_card, null);
            day_et = view.findViewById(R.id.day_et);
            month_et = view.findViewById(R.id.month_et);
            year_et = view.findViewById(R.id.year_et);
            hour_et = view.findViewById(R.id.hour_et);
            min_et = view.findViewById(R.id.min_et);

            builder.setView(view);
            builder.setTitle("Set Alarm for Pre Advising");
            builder.setMessage("You will be notified when you want to");

            builder.setPositiveButton("Done!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String day = day_et.getText().toString();
                    String month = month_et.getText().toString();
                    String year = year_et.getText().toString();
                    String hour = hour_et.getText().toString();
                    String min = min_et.getText().toString();
//
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(Integer.parseInt(year), (Integer.parseInt(month)-1), Integer.parseInt(day),
                            Integer.parseInt(hour), Integer.parseInt(min));
                    am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    Intent i = new Intent(Usis_Demo.this, AlarmReceiver.class);
                    pi = PendingIntent.getBroadcast(getApplicationContext(), 0, i, 0);
                    am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
                    Toast.makeText(Usis_Demo.this,"alarm ready",Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        if (item.getItemId() == R.id.my_selection) {
            Intent i = new Intent(Usis_Demo.this, My_Selection.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}
