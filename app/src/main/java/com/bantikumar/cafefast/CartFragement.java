package com.bantikumar.cafefast;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CartFragement extends Fragment {

    Button placeOrder;
    RecyclerView recyclerView;
    public static ItemSelectedAdapter itemSelectedAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.cart_frag_layout,container,false);

        List<SelectedItem> list;

        if(Database.cartItems!=null && Database.cartItems.size()>0){
            list = new ArrayList<>(Database.cartItems);
            recyclerView = v.findViewById(R.id.cart_recycler_view);
            itemSelectedAdapter = new ItemSelectedAdapter(getContext(),list);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(itemSelectedAdapter);
            placeOrder = v.findViewById(R.id.cart_frag_place_order_btn);
            placeOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getContext(),ConfirmOrder.class));
                }
            });
        }

        return v;
    }
}
