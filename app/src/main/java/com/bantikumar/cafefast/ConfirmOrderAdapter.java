package com.bantikumar.cafefast;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ConfirmOrderAdapter extends RecyclerView.Adapter<ConfirmOrderViewHolder> {

    List<SelectedItem> list;
    public ConfirmOrderAdapter(List<SelectedItem> list){
        this.list =  list;
    }


    @NonNull
    @Override
    public ConfirmOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.confrm_order_recycler_view,parent,false);
        return new ConfirmOrderViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ConfirmOrderViewHolder holder, int position) {
        holder.title.setText(list.get(position).getItem().getItemName());
        holder.price.setText("Rs. "+String.valueOf(list.get(position).getItem().getPrice()));
        holder.qty.setText(String.valueOf(list.get(position).getQuantity()));
        holder.total_price.setText("Rs. "+String.valueOf(list.get(position).getTotalPrice()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class ConfirmOrderViewHolder extends RecyclerView.ViewHolder{

    TextView title,qty,price,total_price;

    public ConfirmOrderViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.confirm_order_rv_item_title);
        qty = itemView.findViewById(R.id.confirm_order_rv_item_qty);
        price = itemView.findViewById(R.id.confirm_order_rv_item_price);
        total_price = itemView.findViewById(R.id.confirm_order_rv_item_total_price);

    }
}
