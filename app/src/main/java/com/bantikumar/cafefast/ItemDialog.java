package com.bantikumar.cafefast;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ItemDialog extends DialogFragment {


    TextView itemName,itemPrice,qty;
    TextInputLayout requirement;
    Button plus,minus,addToCart,placeOrder;
    ImageView crossImg;
    SelectedItem selectedItem;
    Database db;
    List<SelectedItem> list;
    boolean flag;
    boolean inCart = false;
    int index = -1;
    Bundle arg;


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
        crossImg = v.findViewById(R.id.cross_img_item_dialog);
        inCart = false;
        db = new Database();
        arg = getArguments();
        int id = arg.getInt("ITEM_ID");
        inCart=false;
        index = 0;
        if(Database.cartItems!=null && Database.cartItems.size()>0) {
            for (SelectedItem item : Database.cartItems) {
                if (item.getItem().getItemId() == id){
                    inCart = true;
                    qty.setText(String.valueOf(item.getQuantity()));
                    break;
                }
                index++;
            }
        }
        if(inCart){
            addToCart.setText("Update in cart");
        }
        else{
            qty.setText(String.valueOf(0));
            addToCart.setText("Add to cart");
        }
        itemPrice.setText("Rs."+String.valueOf(arg.getDouble("PRICE",0)));
        itemName.setText(arg.getString("NAME","default name"));
        setCancelable(false);
        crossImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

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

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(qty.getText().toString().equals("0")){
                    // TODO: Use toast here
                    Toast.makeText(getActivity(), "Please complete required data", Toast.LENGTH_SHORT).show();
                }
                else{
                    selectedItem = new SelectedItem(new Item(arg.getInt("ITEM_ID"), arg.getDouble("PRICE"),arg.getString("NAME","default name")), Integer.parseInt(qty.getText().toString()));
                    list = new ArrayList<>();
                    list.add(selectedItem);
                    Date date = new Date();
                    Dashboard.order = new OrderClass(list, arg.getString("EMAIL"), "I need ASAP", date);
                    startActivity(new Intent(getActivity(),ConfirmOrder.class));
                    dismiss();
                }
            }
        });
        if(inCart){
            flag = false;
            addToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (qty.getText().toString().equals("0")) {
                        try {
                            new AsyncTask() {
                                @Override
                                protected void onPreExecute() {
                                    addToCart.setBackgroundColor(Color.parseColor("#B4A484"));
                                    addToCart.setClickable(false);
                                    super.onPreExecute();
                                    flag = false;
                                }

                                @Override
                                protected Object doInBackground(Object[] objects) {
                                    flag = db.deleteItemFromCart(arg.getInt("ITEM_ID"));
                                    //flag = db.addToCart(arg.getString("EMAIL"), new SelectedItem(new Item(arg.getInt("ITEM_ID")), Integer.parseInt(qty.getText().toString())));
                                    return null;
                                }

                                @Override
                                protected void onPostExecute(Object o) {
                                    super.onPostExecute(o);
                                    addToCart.setBackgroundColor(Color.parseColor("#C4A484"));
                                    addToCart.setClickable(true);

                                    // Database.cartItems.add(new SelectedItem(new Item(arg.getInt("ITEM_ID"), arg.getDouble("PRICE", 0), itemName.getText().toString()), Integer.parseInt(qty.getText().toString())));
                                    if (flag) {
                                        Toast.makeText(getActivity(), "Item successfully removed from cart", Toast.LENGTH_SHORT).show();
                                        Database.cartItems.remove(index);
                                    }
                                    else
                                        Toast.makeText(getActivity(), db.error, Toast.LENGTH_SHORT).show();
                                    //CartFragement.itemSelectedAdapter.notifyDataSetChanged();
                                    dismiss();
                                }
                            }.execute();
                        } catch (Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        // TODO: Delete item from cart here
                    } else {
                        try {
                            new AsyncTask() {
                                @Override
                                protected void onPreExecute() {
                                    addToCart.setBackgroundColor(Color.parseColor("#B4A484"));
                                    addToCart.setClickable(false);
                                    super.onPreExecute();
                                    flag = false;
                                }

                                @Override
                                protected Object doInBackground(Object[] objects) {
                                    flag = db.updateCartItem(arg.getInt("ITEM_ID"),Integer.parseInt(qty.getText().toString()));
                                    //flag = db.addToCart(arg.getString("EMAIL"), new SelectedItem(new Item(arg.getInt("ITEM_ID")), Integer.parseInt(qty.getText().toString())));
                                    return null;
                                }

                                @Override
                                protected void onPostExecute(Object o) {
                                    super.onPostExecute(o);
                                    addToCart.setBackgroundColor(Color.parseColor("#C4A484"));
                                    addToCart.setClickable(true);


                                    // Database.cartItems.add(new SelectedItem(new Item(arg.getInt("ITEM_ID"), arg.getDouble("PRICE", 0), itemName.getText().toString()), Integer.parseInt(qty.getText().toString())));
                                    if (flag){
                                        Database.cartItems.get(index).setQuantity(Integer.parseInt(qty.getText().toString()));
                                        Toast.makeText(getActivity(), "Item successfully updated in cart", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                        Toast.makeText(getActivity(), db.error, Toast.LENGTH_SHORT).show();
                                    //CartFragement.itemSelectedAdapter.notifyDataSetChanged();
                                    dismiss();
                                }
                            }.execute();
                        } catch (Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });


        }
        else {
            addToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (qty.getText().toString().equals("0")) {
                        // TODO: Use toast here
                        Toast.makeText(getActivity(), "Please select quantity of item", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            new AsyncTask() {
                                @Override
                                protected void onPreExecute() {
                                    addToCart.setBackgroundColor(Color.parseColor("#B4A484"));
                                    addToCart.setClickable(false);
                                    super.onPreExecute();
                                    flag = false;
                                }

                                @Override
                                protected Object doInBackground(Object[] objects) {
                                    flag = db.addToCart(arg.getString("EMAIL"), new SelectedItem(new Item(arg.getInt("ITEM_ID")), Integer.parseInt(qty.getText().toString())));
                                    return null;
                                }

                                @Override
                                protected void onPostExecute(Object o) {
                                    super.onPostExecute(o);
                                    addToCart.setBackgroundColor(Color.parseColor("#C4A484"));
                                    addToCart.setClickable(true);
                                    if (flag){
                                        Database.cartItems.add(new SelectedItem(new Item(arg.getInt("ITEM_ID"), arg.getDouble("PRICE", 0), itemName.getText().toString()), Integer.parseInt(qty.getText().toString())));
                                        Toast.makeText(getActivity(), "Item successfully added in cart", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                        Toast.makeText(getActivity(), db.error, Toast.LENGTH_SHORT).show();
                                    //CartFragement.itemSelectedAdapter.notifyDataSetChanged();
                                    dismiss();
                                }
                            }.execute();
                        } catch (Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
        return v;
    }
    @Override
    public void onResume() {
        // Sets the height and the width of the DialogFragment
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setLayout(width, height);

        super.onResume();
    }
}
