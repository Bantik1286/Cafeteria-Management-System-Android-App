package com.bantikumar.cafefast;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> implements Filterable {


    private Context context;
    private List<Item> items;
    private List<Item> itemsComplete;

    public ItemAdapter(Context context,List<Item> items){
        this.context = context;
        this.items = items;
        this.itemsComplete = new ArrayList<>(items);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view,parent,false);
        return new ItemViewHolder(mView);

    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.title.setText(items.get(position).getItemName());
        holder.description.setText(items.get(position).getItemDescription());
        holder.price.setText("Rs. "+String.valueOf(items.get(position).getPrice()));

        if(items.get(position).getAvailableQuantity()==0){
            holder.layout.setBackgroundColor(Color.parseColor("#8b0000"));
            holder.available_qty.setText("N/A");
        }
        else{
            holder.layout.setBackgroundColor(Color.parseColor("#228B22"));
            holder.available_qty.setText("Available : "+String.valueOf(items.get(position).getAvailableQuantity()));
        }

        if(items.get(position).isFavourite)
            holder.favouriteIcon.setColorFilter(Color.parseColor("#Ab0000"));
        else
            holder.favouriteIcon.setColorFilter(Color.parseColor("#ECEDEF"));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, items.get(holder.getAdapterPosition()).getItemName() + " selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public Filter getFilter() {
        return FilterItems;
    }

    private  Filter FilterItems=new Filter(){

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Item> filterItems = new ArrayList<>();
            if(charSequence==null || charSequence.length()==0)
                filterItems.addAll(itemsComplete);
            else{
                String searched = charSequence.toString().toLowerCase().trim();
                for(Item item : itemsComplete){
                    if(item.getItemName().toLowerCase().contains(searched) ) // TODO : Category search
                        filterItems.add(item);
                }
            }
            FilterResults result = new FilterResults();
            result.values = filterItems;

            return result;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            items.clear();
            items.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };

}
class ItemViewHolder extends RecyclerView.ViewHolder {

    TextView title,description,price,available_qty;
    CardView cardView;
    LinearLayout layout;
    ImageView favouriteIcon;

    public ItemViewHolder(View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.item_title_tv);
        description = itemView.findViewById(R.id.item_description_tv);
        price = itemView.findViewById(R.id.item_price_tv);
        available_qty = itemView.findViewById(R.id.quantity_availalbe_tv);
        cardView = itemView.findViewById(R.id.card_view_item);
        layout=itemView.findViewById(R.id.background_layout_item_recyclter);
        favouriteIcon = itemView.findViewById(R.id.favourite_icon);
    }
}

