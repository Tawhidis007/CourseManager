package com.asymptote.coursemanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginReg extends AppCompatActivity {

    Button sign_in_btn, create_acc_btn, admin_btn;
    EditText login_password, login_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_reg);

        sign_in_btn = findViewById(R.id.sign_in_btn);
        create_acc_btn = findViewById(R.id.create_acc_btn);
        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);
        admin_btn = findViewById(R.id.admin_btn);


        create_acc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginReg.this, Registration.class);
                startActivity(i);
            }
        });
        sign_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if (login_email.getText().toString().equals("") || login_password.getText().toString().equals("")) {
                        Toast.makeText(LoginReg.this, "Fill in the fields properly.", Toast.LENGTH_SHORT).show();
                    } else if (!login_email.getText().toString().equals("") && !login_password.getText().toString().equals("")) {
                        if (AppDatabase2.getAppDatabase2(getApplicationContext()).userTableDAO()
                                .findByEmail(login_email.getText().toString()).getEmail() == null) {
                            Toast.makeText(LoginReg.this, "Register first!", Toast.LENGTH_SHORT).show();
                        }
                        if (login_email.getText().toString().equals(AppDatabase2.getAppDatabase2(getApplicationContext()).userTableDAO()
                                .findByEmail(login_email.getText().toString()).getEmail()) &&
                                login_password.getText().toString().equals(AppDatabase2.getAppDatabase2(getApplicationContext()).userTableDAO()
                                        .findByEmail(login_email.getText().toString()).getPassword())) {
                            Intent ii = new Intent(LoginReg.this, MainActivity.class);
                            startActivity(ii);
                        } else {
                            Toast.makeText(LoginReg.this, "Incorrect Credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch(Exception e){
                    Toast.makeText(LoginReg.this,"Have you Registered yet?",Toast.LENGTH_SHORT).show();
                }
            }
        });

        admin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(LoginReg.this, Admin_view.class);
                startActivity(a);
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}
