package com.bantikumar.cafefast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class OrderHistory extends AppCompatActivity {
    RecyclerView rv;
    Database db;
    List<OrderClass> list;
    Dialog progressDialog;
    boolean flag;
    OrderAdapter orderAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        toolbar = findViewById(R.id.toolbarOrderHistory);
        toolbar.setTitle("Order history");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        rv = findViewById(R.id.order_history_rv);
        db = new Database(Dashboard.email);
        try {
            AsyncTask a = new AsyncTask() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    flag = false;
                    progressDialog = new Dialog(OrderHistory.this);
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
                            list = new ArrayList<>(Database.orders);
                            orderAdapter = new OrderAdapter(OrderHistory.this, list);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OrderHistory.this);
                            rv.setLayoutManager(linearLayoutManager);
                            rv.setAdapter(orderAdapter);

                        } else
                            Toast.makeText(OrderHistory.this, Database.error, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(OrderHistory.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }.execute();
        }
        catch (Exception e){
            Toast.makeText(OrderHistory.this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }


    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}