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

public class ProgessOrderFragement extends Fragment {

    RecyclerView recyclerView;
    OrderAdapter orderAdapter;
    public static List<OrderClass> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=  inflater.inflate(R.layout.progress_order_fragment,container,false);
        try {
            recyclerView = v.findViewById(R.id.progress_order_frag_rv);
            if (Database.orders != null) {
                list = new ArrayList<>(Database.orders);
            int j = 0;
            for (j=0; j<list.size();) {
                if ((list.get(j).getStatus() != 'I' && list.get(j).getStatus()!='R')) {
                    list.remove(j);
                }
                else
                    j++;
            }
                orderAdapter = new OrderAdapter(getContext(), list);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(orderAdapter);
            } else
                Toast.makeText(getActivity(), Database.error, Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return v;
    }
}
