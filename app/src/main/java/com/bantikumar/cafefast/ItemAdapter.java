package com.bantikumar.cafefast;

import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
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
    FragmentManager fragmentManager;
    Database db;
    String email;
    boolean flag;
    public ItemAdapter(Context context,FragmentManager fragmentManager,List<Item> items,String email){
        this.context = context;
        this.items = items;
        this.itemsComplete = new ArrayList<>(items);
        this.fragmentManager = fragmentManager;
        db = new Database();
        this.email = email;
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
        int i=position;
        holder.price.setText("Rs. "+String.valueOf(items.get(position).getPrice()));
        if(items.get(position).getAvailableQuantity()>0) {
            holder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle arg = new Bundle();
                    arg.putInt("ITEM_ID",items.get(i).getItemId());
                    arg.putString("NAME", items.get(i).getItemName());
                    arg.putDouble("PRICE", items.get(i).getPrice());
                    arg.putString("EMAIL",email);
                    ItemDialog i = new ItemDialog();
                    i.setArguments(arg);
                    i.show(fragmentManager, "Tag");
                }
            });
            holder.description_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle arg = new Bundle();
                    arg.putInt("ITEM_ID",items.get(i).getItemId());
                    arg.putString("NAME", items.get(i).getItemName());
                    arg.putDouble("PRICE", items.get(i).getPrice());
                    arg.putString("EMAIL",email);
                    ItemDialog i = new ItemDialog();
                    i.setArguments(arg);
                    i.show(fragmentManager, "Tag");
                }
            });
        }
        if(items.get(position).getAvailableQuantity()==0){
            holder.layout.setBackgroundColor(Color.parseColor("#eb1b0c"));
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
        holder.favouriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (items.get(i).isFavourite) {
                    AsyncTask a = new AsyncTask() {
                        @Override
                        protected void onPreExecute() {
                            Toast.makeText(context, "Please wait we are completeing your operation", Toast.LENGTH_SHORT).show();
                            flag = false;
                            super.onPreExecute();
                        }

                        @Override
                        protected Object doInBackground(Object[] objects) {
                            flag = db.deleteFavourite(email,items.get(i).getItemId());
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Object o) {
                            if(flag) {
                                Toast.makeText(context, "Successfully deleted from faourite list", Toast.LENGTH_SHORT).show();
                                items.get(i).setFavourite(false);
                                notifyDataSetChanged();
                            }
                            else
                                Toast.makeText(context, db.error, Toast.LENGTH_SHORT).show();
                            super.onPostExecute(o);
                        }
                    }.execute();
                }
                else{
                    AsyncTask a = new AsyncTask() {
                        @Override
                        protected void onPreExecute() {
                            Toast.makeText(context, "Please wait we are completeing your operation", Toast.LENGTH_SHORT).show();
                            flag = false;
                            super.onPreExecute();
                        }

                        @Override
                        protected Object doInBackground(Object[] objects) {
                            flag = db.addFavouriteItem(email,items.get(i).getItemId());
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Object o) {
                            if(flag) {
                                Toast.makeText(context, "Successfully added in faourite list", Toast.LENGTH_SHORT).show();
                                items.get(i).setFavourite(true);
                                notifyDataSetChanged();
                            }
                            else
                                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                            super.onPostExecute(o);
                        }
                    }.execute();
                }
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
                    boolean containtCat = false;
                    if(item.getCategoryList()!=null) {
                        for (Category c : item.getCategoryList()) {
                            if (c.getCategoryName().toLowerCase().trim().contains(searched)) {
                                containtCat = true;
                                break;
                            }
                        }
                    }
                    if(containtCat || item.getItemName().toLowerCase().contains(searched))
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
    LinearLayout description_layout;

    public ItemViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.item_title_tv);
        description = itemView.findViewById(R.id.item_description_tv);
        price = itemView.findViewById(R.id.item_price_tv);
        available_qty = itemView.findViewById(R.id.quantity_availalbe_tv);
        cardView = itemView.findViewById(R.id.card_view_item);
        layout=itemView.findViewById(R.id.background_layout_item_recyclter);
        favouriteIcon = itemView.findViewById(R.id.favourite_icon);
        description_layout = (LinearLayout)itemView.findViewById(R.id.desciption_layout_item_rv);
    }
}

