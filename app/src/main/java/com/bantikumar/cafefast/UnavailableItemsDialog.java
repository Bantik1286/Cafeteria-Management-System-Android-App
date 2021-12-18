package com.bantikumar.cafefast;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UnavailableItemsDialog extends DialogFragment {

    Button yes, no;
    RecyclerView rv;
    public static List<SelectedItem> unavailable = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.unavailabe_items_layout,container,false);
        if(unavailable!=null) {
            setCancelable(false);
            no = v.findViewById(R.id.unvailable_item_layout_no);
            yes = v.findViewById(R.id.unvailable_item_layout_yes);
            rv = v.findViewById(R.id.unvailable_item_layout_rv);
            ConfirmOrderAdapter ad = new ConfirmOrderAdapter(unavailable);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            rv.setLayoutManager(linearLayoutManager);
            rv.setAdapter(ad);

            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "Yes clicked", Toast.LENGTH_SHORT).show();
                }
            });
            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });

        }
        return v;
    }
    public void onResume() {
        // Sets the height and the width of the DialogFragment
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setLayout(width, height);

        super.onResume();
    }
}
