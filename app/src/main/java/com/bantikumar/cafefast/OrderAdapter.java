package com.bantikumar.cafefast;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder>{

    Context context;
    List<OrderHelperClass> orders;
    public OrderAdapter(Context context, List<OrderHelperClass> orders) {
        this.context = context;
        this.orders = orders;
    }
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_recycler_view,parent,false);
        return new OrderViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.order_id.setText(String.valueOf(orders.get(position).getOrder_id()));
        if(orders.get(position).getStatus()=='C'){
            holder.status.setText("Completed");
            holder.status.setTextColor(Color.parseColor("#228B22"));
        }
        else if(orders.get(position).getStatus()=='P'){
            holder.status.setText("In Progress");
            holder.status.setTextColor(Color.parseColor("#FFA500"));
        }
        else if(orders.get(position).getStatus()=='F'){
            holder.status.setText("Cancelled");
            holder.status.setTextColor(Color.parseColor("#FF0000"));
        }
        holder.total_amount.setText(String.valueOf(orders.get(position).getTotal_amount()));


        // TODO: Date waaro
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
}
class OrderViewHolder extends RecyclerView.ViewHolder {

    TextView order_id,total_amount,status,dateAndTime;

    public OrderViewHolder(View itemView) {
        super(itemView);

        order_id = itemView.findViewById(R.id.order_rv_order_no);
        total_amount = itemView.findViewById(R.id.order_rv_total_amount);
        status=itemView.findViewById(R.id.order_rv_status);
        dateAndTime = itemView.findViewById(R.id.order_rv_data_and_time);

    }
}