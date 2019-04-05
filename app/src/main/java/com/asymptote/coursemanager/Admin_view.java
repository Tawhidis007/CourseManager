package com.asymptote.coursemanager;

import android.arch.persistence.db.SimpleSQLiteQuery;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Admin_view extends AppCompatActivity {

    TextView admin_tv;
    Button gen_btn;
    EditText query_et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view);

        admin_tv = findViewById(R.id.admin_tv);
        gen_btn = findViewById(R.id.gen_btn);
        query_et = findViewById(R.id.query_et);

        gen_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String queryString = query_et.getText().toString();
                SimpleSQLiteQuery query = new SimpleSQLiteQuery(queryString);
            try {
                /**for separate querying**/
//                Users user = AppDatabase2.getAppDatabase2(getApplicationContext()).userTableDAO().getUserQuery(query);
//                admin_tv.setText(user.toString());

                /**list marka kichu query'r jonno**/
                List<Users> userlist = AppDatabase2.getAppDatabase2(getApplicationContext())
                        .userTableDAO().getAllUsersQuery(query);
                String shobai = "";
                for (Users users : userlist) {
                    shobai += users.toString();
                }
                admin_tv.setText(shobai);

                /** returns 0 if deleted the row! **/
//                int n = AppDatabase2.getAppDatabase2(getApplicationContext()).userTableDAO().deleteQuery(query);
//                admin_tv.setText(Integer.toString(n));

                /**return 0 if done properly **/
//                int m = AppDatabase2.getAppDatabase2(getApplicationContext()).userTableDAO().deleteQuery(query);
//                admin_tv.setText(Integer.toString(m));
            }catch(Exception e){
                Toast.makeText(Admin_view.this,"Improper Query!",Toast.LENGTH_SHORT).show();
            }
            }
        });

    }
}
