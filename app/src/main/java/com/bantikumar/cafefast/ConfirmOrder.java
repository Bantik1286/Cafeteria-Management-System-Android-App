package com.bantikumar.cafefast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class ConfirmOrder extends AppCompatActivity {
    RecyclerView rv;
    Dialog progressDialog;
    Database db;
    TextView tv;
    TextInputLayout req;
    boolean flag;
    public static List<SelectedItem> unavailable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        rv = findViewById(R.id.confirm_order_rv);
        tv = findViewById(R.id.confirm_order_total_price);
        req = findViewById(R.id.activity_confirm_order_requirement);

        if (Build.VERSION.SDK_INT > 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        List<SelectedItem> list;

        if(Dashboard.order!=null  && Dashboard.order.getItems()!=null && Dashboard.order.getItems().size()>0){
            list = new ArrayList<>(Dashboard.order.getItems());

            double total =0 ;
            for(SelectedItem item : list)
                total+= item.getTotalPrice();
            tv.setText("Total amount : Rs. " + String.valueOf(total));
            ConfirmOrderAdapter ad = new ConfirmOrderAdapter(list);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ConfirmOrder.this);
            rv.setLayoutManager(linearLayoutManager);
            rv.setAdapter(ad);

            findViewById(R.id.confirm_order_confirm_tv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AsyncTask asyncTask = new AsyncTask() {
                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            if(req.getEditText().getText().toString().isEmpty())
                                Dashboard.order.setRequirement("No requirements");
                            else
                                Dashboard.order.setRequirement(req.getEditText().getText().toString());
                            db = new Database(Dashboard.email);
                            progressDialog = new Dialog(ConfirmOrder.this);
                            progressDialog.setContentView(R.layout.loading_dialog);
                            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            progressDialog.setCancelable(false);
                            progressDialog.show();
                        }

                        @Override
                        protected Object doInBackground(Object[] objects) {
                            unavailable = db.placeOrderTransaction(Dashboard.order);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Object o) {
                            super.onPostExecute(o);
                            progressDialog.dismiss();
                            if (unavailable == null) {
                                Toast.makeText(getApplication(), "Order placed Successfully", Toast.LENGTH_SHORT).show();
                                if(CartFragement.cartOrder){
                                    CartFragement.cartOrder = false;
                                    CartFragement.list = null;
                                    CartFragement.itemSelectedAdapter.notifyDataSetChanged();
                                }
                                Intent intent  = new Intent(ConfirmOrder.this,Dashboard.class);
                                intent.putExtra("EMAIL",Dashboard.email);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                                startActivity(intent);
                            }
                            else{
                                if(unavailable.size()==0)
                                Toast.makeText(getApplication(), db.error, Toast.LENGTH_SHORT).show();
                                else if(unavailable.size()==Dashboard.order.getItems().size()){
                                    Toast.makeText(ConfirmOrder.this,"Unavailable item(s) in stock",Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else{
                                    UnavailableItemsDialog i = new UnavailableItemsDialog();
                                    i.show(getSupportFragmentManager(), "Tag");
                                }
                            }
                        }
                    }.execute();
                }
            });
            findViewById(R.id.confirm_order_cancel_tv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplication(), "Order cancelled", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

        }


    }
}