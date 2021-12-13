package com.bantikumar.cafefast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

public class order_details extends AppCompatActivity {

    TextView order_id,totalAmount,status,description;
    RecyclerView recyclerView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        toolbar = findViewById(R.id.order_details_toolbar);
        toolbar.setTitle("Order detail");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if(Order.orderDetail!=null){
            recyclerView = findViewById(R.id.order_details_rv);
            order_id = findViewById(R.id.order_details_order_id_tv);
            totalAmount = findViewById(R.id.order_details_total_amount);
            status = findViewById(R.id.order_details_status);
            description = findViewById(R.id.order_details_description);


            order_id.setText("#"+String.valueOf(Order.orderDetail.getOrderId()));
            totalAmount.setText(String.valueOf(Order.orderDetail.getTotalAmount()));
            if(Order.orderDetail.getStatus() == 'I') {
                status.setText("In progress");
            }
            else if(Order.orderDetail.getStatus() == 'R') {
                status.setText("Ready");
            }
            else if(Order.orderDetail.getStatus() == 'C') {
                status.setText("Completed");
            }
            else if(Order.orderDetail.getStatus() == 'F') {
                status.setText("Failed");
            }
            description.setText(Order.orderDetail.getRequirement());

            ConfirmOrderAdapter ad = new ConfirmOrderAdapter(Order.orderDetail.getItems());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(order_details.this);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(ad);
        }

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}