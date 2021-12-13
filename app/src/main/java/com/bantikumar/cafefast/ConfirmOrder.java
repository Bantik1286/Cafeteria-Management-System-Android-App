package com.bantikumar.cafefast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ConfirmOrder extends AppCompatActivity {
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        rv = findViewById(R.id.confirm_order_rv);


        List<SelectedItem> list;

        if(Dashboard.order!=null  && Dashboard.order.getItems()!=null && Dashboard.order.getItems().size()>0){
            list = new ArrayList<>(Dashboard.order.getItems());
            ConfirmOrderAdapter ad = new ConfirmOrderAdapter(list);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ConfirmOrder.this);
            rv.setLayoutManager(linearLayoutManager);
            rv.setAdapter(ad);
        }




    }
}