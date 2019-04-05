package com.asymptote.coursemanager;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton fab;
    EditText course_adder_et,beauty_et;
    ArrayList<Courses> a;
    First_Adapter first_adapter;
    static ArrayList<String> o = new ArrayList<>(); //has to be static and eita hochhe jegula ami select korsi


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);

        fab = findViewById(R.id.fab);

        a = new ArrayList<>();
        //must find out a better way to do this but bleh
        a = (ArrayList<Courses>) AppDatabase.getAppDatabase(getApplicationContext()).motherTableDAO().getAll();
        final String DIR = getFilesDir().toString();
        Log.d("tag", DIR + "/");


        first_adapter = new First_Adapter(getApplicationContext(), a);
        RecyclerView.LayoutManager lm = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(first_adapter);

        first_adapter.setlistener(new First_Adapter.bridge() {
            @Override
            public void listener(int position) {
                Intent i = new Intent(getApplicationContext(), LandingPage.class);
                i.putExtra("name", a.get(position).course_name);
                i.putExtra("path", a.get(position).directory);
                startActivity(i);
            }

        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.course_add_dialog, null);
                course_adder_et = view.findViewById(R.id.course_add_et);
                beauty_et = view.findViewById(R.id.beauty_et);

                builder.setView(view);
                builder.setTitle("Course Addition");
                builder.setMessage("Enter the name of the course you want to add.");

                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!ase(a, course_adder_et.getText().toString())) {
                            a.add(new Courses(course_adder_et.getText().toString(), DIR + "/"
                                    + course_adder_et.getText().toString(),beauty_et.getText().toString()));
                            first_adapter.notifyDataSetChanged();

                            if (AppDatabase.getAppDatabase(getApplicationContext()).motherTableDAO().
                                    findByCourse(course_adder_et.getText().toString())
                                    == null) {
                                addCourse(AppDatabase.getAppDatabase(getApplicationContext()),
                                        new Courses(course_adder_et.getText().toString(),
                                        DIR + "/" + course_adder_et.getText().toString(),beauty_et.getText().toString()));
                                Log.d("tag", "course added successfully");
                            } else {
                                Log.d("tag", "course already in db");
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "You already have this course added!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
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
        });
    }

    private static Courses addCourse(final AppDatabase db, Courses course) {
        db.motherTableDAO().insert(course);
        return course;
    }

    private boolean ase(ArrayList<Courses> check, String value) {
        for (Courses c : check) {
            if (value.equals(c.course_name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView sv = (SearchView) menuItem.getActionView();
        sv.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //when result is only one remaining, bug is that it shows empty stuff inside the course.
                String inp = newText.toLowerCase();
                ArrayList<Courses> newList = new ArrayList<>();
                for (Courses courses : a) {
                    if (courses.getCourse_name().toLowerCase().contains(inp)) {
                        newList.add(courses);
                    }
                }
                first_adapter.updateList(newList);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.wishlist) {
            Intent i = new Intent(MainActivity.this, Usis_Demo.class);
            startActivity(i);
        }
        if (item.getItemId() == R.id.logout) {
            Intent i = new Intent(MainActivity.this, LoginReg.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
    }
}
