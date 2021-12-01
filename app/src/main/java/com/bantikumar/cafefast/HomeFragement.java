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

public class HomeFragement extends Fragment {

    RecyclerView recyclerView;
    public static ItemAdapter itemAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.home_frag_layout,container,false);

        recyclerView=v.findViewById(R.id.item_recycler_view);
        Item item = new Item(1,"Biryani","This is famous",110.0,11,2,false);
        List<Item> i=new ArrayList<>();
        i.add(item);
        item = new Item(1,"Chicken","This is famous",110.0,0,2,false);
        i.add(item);
        item = new Item(1,"Korma","This is famous",110.0,11,2,true);
        i.add(item);

        for(int j=0;j<i.size();) {
            if (i.get(j).getAvailableQuantity()==0)
                i.remove(j);
            else
                j++;
        }

         itemAdapter = new ItemAdapter(getContext(),i);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(itemAdapter);
        return v;
    }
}
