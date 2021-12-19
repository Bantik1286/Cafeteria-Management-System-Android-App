package com.bantikumar.cafefast;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UnavailableItemsDialog extends DialogFragment {

    Button yes, no;
    RecyclerView rv;
    Dialog progressDialog;
    Database db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.unavailabe_items_layout,container,false);
        if(ConfirmOrder.unavailable!=null) {
            setCancelable(false);
            no = v.findViewById(R.id.unvailable_item_layout_no);
            yes = v.findViewById(R.id.unvailable_item_layout_yes);
            rv = v.findViewById(R.id.unvailable_item_layout_rv);
            ConfirmOrderAdapter ad = new ConfirmOrderAdapter(ConfirmOrder.unavailable);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            rv.setLayoutManager(linearLayoutManager);
            rv.setAdapter(ad);

            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for(int i=0;i<ConfirmOrder.unavailable.size();i++){
                        for(int j=0;j<Dashboard.order.getItems().size();){
                            if(Dashboard.order.getItems().get(j).getItem().getItemId()==ConfirmOrder.unavailable.get(i).getItem().getItemId())
                                Dashboard.order.getItems().remove(j);
                            else
                                j++;
                        }
                    }

                    AsyncTask asyncTask = new AsyncTask() {
                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            db = new Database(Dashboard.email);
                            progressDialog = new Dialog(getContext());
                            progressDialog.setContentView(R.layout.loading_dialog);
                            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            progressDialog.setCancelable(false);
                            progressDialog.show();
                        }

                        @Override
                        protected Object doInBackground(Object[] objects) {
                            ConfirmOrder.unavailable = db.placeOrderTransaction(Dashboard.order);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Object o) {
                            super.onPostExecute(o);
                            progressDialog.dismiss();
                            if (ConfirmOrder.unavailable == null) {
                                Toast.makeText(getContext(), "Order placed Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent  = new Intent(getActivity(),Dashboard.class);
                                intent.putExtra("EMAIL",Dashboard.email);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                                startActivity(intent);
                            }
                            else{
                                if(ConfirmOrder.unavailable.size()==0)
                                    Toast.makeText(getContext(), db.error, Toast.LENGTH_SHORT).show();
                                else{

                                    UnavailableItemsDialog i = new UnavailableItemsDialog();
                                    i.show(getParentFragmentManager(), "Tag");
                                    dismiss();
                                    //Toast.makeText(getApplication(), String.valueOf(unavailable.size()), Toast.LENGTH_SHORT).show();
                                }
                            }
                            dismiss();
                        }
                    }.execute();
                }
            });
            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });

        }
        return v;
    }
    public void onResume() {
        // Sets the height and the width of the DialogFragment
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setLayout(width, height);

        super.onResume();
    }
}
