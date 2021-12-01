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

public class ProgessOrderFragement extends Fragment {

    RecyclerView recyclerView;
    OrderAdapter orderAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=  inflater.inflate(R.layout.progress_order_fragment,container,false);
        recyclerView = v.findViewById(R.id.progress_order_frag_rv);
        List<OrderHelperClass> i = new ArrayList<>();
        i.add(new OrderHelperClass(123,'P',1250.04));
        i.add(new OrderHelperClass(130,'P',130.2));
        i.add(new OrderHelperClass(123,'P',1330));
        orderAdapter = new OrderAdapter(getContext(),i);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(orderAdapter);
        return v;
    }
}
