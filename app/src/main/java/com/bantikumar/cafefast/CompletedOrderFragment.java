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

public class CompletedOrderFragment extends Fragment {

    RecyclerView recyclerView;
    OrderAdapter orderAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.completed_order_fragment,container,false);
        recyclerView = v.findViewById(R.id.completed_order_frag_rv);
        List<OrderHelperClass> i = new ArrayList<>();
        i.add(new OrderHelperClass(342,'C',150.04));
        i.add(new OrderHelperClass(325,'C',1320.2));
        i.add(new OrderHelperClass(183,'C',1130));
        orderAdapter = new OrderAdapter(getContext(),i);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(orderAdapter);
        return v;
    }
}
