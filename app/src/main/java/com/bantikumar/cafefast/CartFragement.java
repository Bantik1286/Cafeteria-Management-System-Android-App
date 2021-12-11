package com.bantikumar.cafefast;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CartFragement extends Fragment {

    RecyclerView recyclerView;
    public static ItemSelectedAdapter itemSelectedAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.cart_frag_layout,container,false);

        List<SelectedItem> i=new ArrayList<>();
        List<Category> a = new ArrayList<>();
        a.add(new Category(1,"Biryani"));
        a.add(new Category(2,"Rice"));
        a.add(new Category(3,"Chicken"));
        i.add(new SelectedItem(new Item(1,"Biryani","This is famous",110.0,11,a,false),3));
        List<Category> b = new ArrayList<>();
        b.add(new Category(3,"Chicken"));
        b.add(new Category(1,"White"));
        b.add(new Category(3,"Lunch"));
        i.add(new SelectedItem(new Item(1,"Korma","This is famous",60.0,11,b,false),2));

        recyclerView = v.findViewById(R.id.cart_recycler_view);
        itemSelectedAdapter = new ItemSelectedAdapter(getContext(),i);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(itemSelectedAdapter);

        return v;
    }
}
