package com.bantikumar.cafefast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class registration_activity extends AppCompatActivity {

    TextView login_btn;
    TextInputLayout firstname,lastname,email,password,confirm_password;
    Button register_btn;
    Database db = new Database();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        firstname = findViewById(R.id.registration_firstname);
        lastname = findViewById(R.id.registration_lastname);
        email = findViewById(R.id.registration_email);
        password = findViewById(R.id.registration_password);
        confirm_password = findViewById(R.id.registration_confirm_password);
        register_btn = findViewById(R.id.registration_register_btn);
        login_btn = findViewById(R.id.registration_login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(registration_activity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (validate()) {
                        if (db.addUser(new Student(firstname.getEditText().getText().toString(), lastname.getEditText().getText().toString(), email.getEditText().getText().toString(), password.getEditText().getText().toString()))) {
                            Toast.makeText(registration_activity.this, "Successfully Added", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(registration_activity.this,Dashboard.class);
                            intent.putExtra("EMAIL",email.getEditText().getText().toString());
                            startActivity(intent);
                            finish();
                        }
                        else
                            Toast.makeText(registration_activity.this, Database.msg.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e){
                    Toast.makeText(registration_activity.this, Database.msg.toString(), Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
    private boolean validate(){
        boolean ch = true;
        if(email.getEditText().getText().toString().isEmpty()){
            ch = false;
            email.setErrorEnabled(true);
            email.setError("You can't leave this field empty");
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email.getEditText().getText().toString()).matches()){
            ch = false;
            email.setErrorEnabled(true);
            email.setError("Please enter a valid email");
        }
        else if(!email.getEditText().getText().toString().contains("@nu.edu.pk")){
            ch = false;
            email.setErrorEnabled(true);
            email.setError("Please enter nu email address only");
        }
        else
            email.setErrorEnabled(false);
        if(firstname.getEditText().getText().toString().isEmpty()){
            ch = false;
            firstname.setErrorEnabled(true);
            firstname.setError("You can't leave this field empty");
        }
        else
            firstname.setErrorEnabled(false);
        if(lastname.getEditText().getText().toString().isEmpty()) {
            ch = false;
            lastname.setErrorEnabled(true);
            lastname.setError("You can't leave this field as empty");
        }
        else
            lastname.setErrorEnabled(false);
        if(password.getEditText().getText().toString().isEmpty()){
            ch = false;
            password.setErrorEnabled(true);
            password.setError("You can't leave this field as empty");
        }
        else if(password.getEditText().getText().toString().length() >= 6){
            ch = false;
            password.setErrorEnabled(true);
            password.setError("Password should contain atleast 6 characters");
        }
        else
            password.setErrorEnabled(false);
        if(confirm_password.getEditText().getText().toString().isEmpty()){
            ch = false;
            confirm_password.setErrorEnabled(true);
            confirm_password.setError("You can't leave this field as empty");
        }
        else if(!confirm_password.getEditText().getText().toString().equals(password.getEditText().getText().toString())){
            ch = false;
            confirm_password.setErrorEnabled(true);
            confirm_password.setError("Password does not match");
        }
        else
            confirm_password.setErrorEnabled(false);
        return ch;
    }
}