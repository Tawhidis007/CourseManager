package com.asymptote.coursemanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Registration extends AppCompatActivity {

    EditText info_et_name, info_et_email, info_et_password, info_et_university;
    CheckBox agreement_id;
    Button term_btn, proceed_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        info_et_name = findViewById(R.id.info_et_name);
        info_et_email = findViewById(R.id.info_et_email);
        info_et_password = findViewById(R.id.info_id_password);
        info_et_university = findViewById(R.id.info_id_university);
        agreement_id = findViewById(R.id.agreement_id);
        term_btn = findViewById(R.id.terms_btn);
        proceed_btn = findViewById(R.id.btn_proceed);

        proceed_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!info_et_name.getText().toString().equals("") && !info_et_password.getText().toString().equals("")
                        && !info_et_email.getText().toString().equals("") && !info_et_university.getText().toString().equals("")) {
                    if (info_et_password.getText().toString().length() >= 6) {
                        if (AppDatabase2.getAppDatabase2(getApplicationContext()).userTableDAO().
                                findByEmail(info_et_email.getText().toString())
                                == null) {
                            addUser(AppDatabase2.getAppDatabase2(getApplicationContext()), new Users(info_et_name.getText().toString(),
                                    info_et_email.getText().toString(), info_et_password.getText().toString(), info_et_university.getText().toString()));
                            Log.d("userdb", "user added successfully");
                            Intent i = new Intent(Registration.this, LoginReg.class);
                            startActivity(i);
                        } else {
                            Log.d("userdb", "user already in db");
                        }
                    } else {
                        Toast.makeText(Registration.this, "Put a proper password.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Registration.this, "Fill up all the forms.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private static Users addUser(final AppDatabase2 db, Users user) {
        db.userTableDAO().insert(user);
        return user;
    }
}
