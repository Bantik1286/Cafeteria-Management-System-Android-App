package com.bantikumar.cafefast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    Toolbar t;
    public static TextView firstname,lastname,email,password;
    LinearLayout fname,lname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        t = findViewById(R.id.tbar_profile_activity);
        t.setTitle("Profile");
        setSupportActionBar(t);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        firstname = findViewById(R.id.profle_activity_fname);
        lastname = findViewById(R.id.profle_activity_lname);
        email = findViewById(R.id.profle_activity_email);
        fname = findViewById(R.id.profle_activity_ll_fname);
        lname = findViewById(R.id.profle_activity_ll_lname);

        if (Dashboard.student != null) {

            fname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateNameDialog dialog = new updateNameDialog('f', Dashboard.student.getFirstname());
                    dialog.show(getSupportFragmentManager(), "MyDialog");
                }
            });
            lname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateNameDialog dialog = new updateNameDialog('l', Dashboard.student.getLastname());
                    dialog.show(getSupportFragmentManager(), "MyDialog");
                }
            });

            password = findViewById(R.id.profle_activity_password);
            password.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UpdatePasswordDialog dialog = new UpdatePasswordDialog();
                    dialog.show(getSupportFragmentManager(), "myDalog");
                }
            });

            if (Dashboard.student != null) {
                firstname.setText(Dashboard.student.getFirstname());
                lastname.setText(Dashboard.student.getLastname());
                email.setText(Dashboard.student.getEmail());
            }
        }
        else{
            firstname.setText("----");
            lastname.setText("----");
            email.setText("----");
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}