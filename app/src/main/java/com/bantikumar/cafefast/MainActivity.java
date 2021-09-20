package com.bantikumar.cafefast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    TextInputLayout email,password;
    Button login_btn;
    TextView register_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);
        login_btn = findViewById(R.id.login_btn);
        register_btn = findViewById(R.id.login_registerBtn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    Toast.makeText(MainActivity.this,"Login Succeeded",Toast.LENGTH_SHORT).show();
                }
            }
        });
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,registration_activity.class);
                startActivity(intent);
            }
        });
    }
    private boolean validate(){
        boolean ch = true;
        if(!email.getEditText().getText().toString().isEmpty()){
            email.setErrorEnabled(false);
        }
        else{
            email.setErrorEnabled(true);
            email.setError("You can't leave this as blank");
            ch = false;
        }
        if(!password.getEditText().getText().toString().isEmpty()){
            password.setErrorEnabled(false);
        }
        else{
            password.setErrorEnabled(true);
            password.setError("You can't leave this as blank");
            ch = false;
        }
        return ch;
    }
}