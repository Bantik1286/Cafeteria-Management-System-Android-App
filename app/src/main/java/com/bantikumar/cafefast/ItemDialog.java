package com.bantikumar.cafefast;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputLayout;

public class ItemDialog extends DialogFragment {


    TextView itemName,itemPrice,qty;
    TextInputLayout requirement;
    Button plus,minus,addToCart,placeOrder;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_item_book,container,false);
        itemName = v.findViewById(R.id.item_dialog_item_name);
        itemPrice = v.findViewById(R.id.item_dialog_item_price);
        qty = v.findViewById(R.id.item_dialog_qty_tv);
        requirement = v.findViewById(R.id.item_dialog_item_requirement_input_layout);
        plus =(Button) v.findViewById(R.id.item_dialog_plus_button);
        minus = (Button) v.findViewById(R.id.item_dialog_neg_button);
        addToCart = v.findViewById(R.id.item_dialog_cart_btn);
        placeOrder = v.findViewById(R.id.item_dialog_place_order);


        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qty.setText(String.valueOf(Integer.parseInt(qty.getText().toString())+1));
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(qty.getText().toString())!=0){
                    qty.setText(String.valueOf(Integer.parseInt(qty.getText().toString())-1));
                }
            }
        });




        return v;
    }
}
