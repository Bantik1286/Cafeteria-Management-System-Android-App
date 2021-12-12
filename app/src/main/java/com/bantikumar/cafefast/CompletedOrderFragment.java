package com.bantikumar.cafefast;

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

public class CompletedOrderFragment extends Fragment {

    RecyclerView recyclerView;
    OrderAdapter orderAdapter;
    public static  List<OrderClass> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.completed_order_fragment, container, false);
        if (Database.orders != null) {
            recyclerView = v.findViewById(R.id.completed_order_frag_rv);
            list = new ArrayList<>(Database.orders);
            int j = 0;
            for (j = 0; j < list.size(); ) {
                if (list.get(j).getStatus() != 'C') {
                    list.remove(j);
                } else
                    j++;
            }
                orderAdapter = new OrderAdapter(getContext(), list);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(orderAdapter);
        }
        return v;
    }
}
