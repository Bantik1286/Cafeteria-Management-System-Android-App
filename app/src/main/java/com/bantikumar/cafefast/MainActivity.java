package com.bantikumar.cafefast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    TextInputLayout email,password;
    Button login_btn;
    TextView register_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        String EMAIL = pref.getString("EMAIL",null);
        if(EMAIL!=null)
        {
            Intent intent = new Intent(MainActivity.this, Dashboard.class);
            intent.putExtra("EMAIL", EMAIL.toString());
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_main);
            email = findViewById(R.id.login_username);
            password = findViewById(R.id.login_password);
            login_btn = findViewById(R.id.login_btn);
            register_btn = findViewById(R.id.login_registerBtn);
            TextView t = findViewById(R.id.data);
            login_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (validate()) {
                        Database db = new Database();
                        if (db.Login(email.getEditText().getText().toString(), password.getEditText().getText().toString())) {
                            Intent intent = new Intent(MainActivity.this, Dashboard.class);
                            intent.putExtra("EMAIL", email.getEditText().getText().toString());
                            Toast.makeText(MainActivity.this, "Succesfully Login", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            finish();
                        } else
                            Toast.makeText(MainActivity.this, Database.msg, Toast.LENGTH_SHORT).show();
                    }
                }
            });
            register_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, registration_activity.class);
                    startActivity(intent);
                    finish();
                }
            });
    }
    private boolean validate() {
        boolean ch = true;
        if (!email.getEditText().getText().toString().isEmpty()) {
            email.setErrorEnabled(false);
        } else {
            email.setErrorEnabled(true);
            email.setError("You can't leave this as blank");
            ch = false;
        }
        if (!password.getEditText().getText().toString().isEmpty()) {
            password.setErrorEnabled(false);
        } else {
            password.setErrorEnabled(true);
            password.setError("You can't leave this as blank");
            ch = false;
        }
        return ch;
    }
}