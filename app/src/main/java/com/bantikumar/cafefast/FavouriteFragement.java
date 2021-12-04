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

public class FavouriteFragement extends Fragment {

    RecyclerView recyclerView;
    List<Item> i;
    public static ItemAdapter itemAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.favourite_frag_layout,container,false);
        recyclerView = v.findViewById(R.id.favourite_fragement_recycler_view);

        i=new ArrayList<>(Dashboard.itemList);

        for(int j=0;j<i.size();) {
            if (!i.get(j).isFavourite())
                i.remove(j);
            else
                j++;
        }

        itemAdapter = new ItemAdapter(getContext(),getActivity().getFragmentManager(),i,Dashboard.email);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(itemAdapter);
        return v;
    }
}
