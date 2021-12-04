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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragement extends Fragment {

    Database db;
    RecyclerView recyclerView;
    public static List<Item> i;
    public static ItemAdapter itemAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.home_frag_layout,container,false);
        db=new Database();
        i=new ArrayList<>(Dashboard.itemList);
        recyclerView=v.findViewById(R.id.item_recycler_view);
        if (i != null) {
            for(int j=0;j<i.size();) {
                if (i.get(j).getAvailableQuantity()==0)
                    i.remove(j);
                else
                    j++;
            }
            itemAdapter = new ItemAdapter(getContext(), getActivity().getFragmentManager(), i,Dashboard.email);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(itemAdapter);
        } else
            Toast.makeText(getContext(),"Item not available", Toast.LENGTH_SHORT).show();
        return v;
    }
}
