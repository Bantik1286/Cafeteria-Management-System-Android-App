package com.bantikumar.cafefast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ItemSelectedAdapter extends RecyclerView.Adapter<ItemSelectedViewHolder> {
    Context context;
    List<SelectedItem> items;
    List<SelectedItem> itemsComplete;

    public ItemSelectedAdapter(Context context, List<SelectedItem> items){
        this.context = context;
        this.items = items;
        this.itemsComplete = new ArrayList<>(items);
    }


    @Override
    public ItemSelectedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_recycler_view,parent,false);
        return new ItemSelectedViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemSelectedViewHolder holder, int position) {
        holder.price.setText("Rs."+String.valueOf(items.get(position).getItem().getPrice()));
        holder.title.setText(items.get(position).getItem().getItemName());
        holder.qty.setText(String.valueOf(items.get(position).getQuantity()));
        holder.total.setText("Rs."+String.valueOf(items.get(position).getQuantity()*items.get(position).getItem().getPrice()));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

class ItemSelectedViewHolder extends RecyclerView.ViewHolder{

    TextView title;
    TextView qty;
    TextView price;
    TextView total;
    public ItemSelectedViewHolder( View itemView) {
        super(itemView);

        qty = itemView.findViewById(R.id.selecte_item_qty);
        title = itemView.findViewById(R.id.selected_item_title);
        price = itemView.findViewById(R.id.selected_item_price);
        total = itemView.findViewById(R.id.selecte_item_total_price);

    }
}