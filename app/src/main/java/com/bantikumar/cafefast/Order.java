package com.bantikumar.cafefast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Order extends AppCompatActivity {
    Toolbar t ;
    RadioGroup r;
    Fragment f;
    Database db;
    Bundle args;
    String email;
    Dialog progressDialog;
    boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        t=findViewById(R.id.order_act_tbar);
        t.setTitle("Orders activity");
        setSupportActionBar(t);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        r=findViewById(R.id.radio_group_order_activity);
        args = getIntent().getExtras();
        email = args.getString("EMAIL");
        Toast.makeText(Order.this, email, Toast.LENGTH_SHORT).show();
        db = new Database(args.getString("EMAIL"));
            AsyncTask a = new AsyncTask() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    flag = false;
                    progressDialog = new Dialog(Order.this);
                    progressDialog.setContentView(R.layout.loading_dialog);
                    progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                }

                @Override
                protected Object doInBackground(Object[] objects) {
                    flag = db.getAllOrders();
                    return null;
                }

                @Override
                protected void onPostExecute(Object o) {
                        super.onPostExecute(o);
                        progressDialog.dismiss();
                        try {
                            if (flag) {
                                f = new ProgessOrderFragement();
                                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_order_activity, f).commit();
                            } else
                                Toast.makeText(Order.this, Database.error, Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(Order.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                }
            }.execute();

        r.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (radioGroup.getCheckedRadioButtonId())
                {
                    case R.id.in_progress_btn:
                        f=new ProgessOrderFragement();
                        break;
                    case R.id.completed_btn:
                        f=new CompletedOrderFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_order_activity,f).commit();
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}