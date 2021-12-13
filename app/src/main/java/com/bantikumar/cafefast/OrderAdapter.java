package com.bantikumar.cafefast;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder>{

    Context context;
    List<OrderClass> orders;
    public OrderAdapter(Context context, List<OrderClass> orders) {
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
        int i = position;
        holder.order_id.setText(String.valueOf(orders.get(position).getOrderId()));
        if(orders.get(position).getStatus()=='C'){
            holder.status.setText("Completed");
            holder.status.setTextColor(Color.parseColor("#228B22"));
        }
        else if(orders.get(position).getStatus()=='I'){
            holder.status.setText("In Progress");
            holder.status.setTextColor(Color.parseColor("#FFA500"));
        }
        else if(orders.get(position).getStatus()=='F'){
            holder.status.setText("Cancelled");
            holder.status.setTextColor(Color.parseColor("#FF0000"));
        }
        else if(orders.get(position).getStatus()=='R'){
            holder.status.setText("Ready");
            holder.status.setTextColor(Color.parseColor("#90EE90"));
        }
        holder.total_amount.setText(String.valueOf(orders.get(position).getTotalAmount()));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(orders.get(i).getItems()!=null){
                    Order.orderDetail = orders.get(i);
                    context.startActivity(new Intent(context,order_details.class));
                }
                else
                    Toast.makeText(context, "No items found for this order", Toast.LENGTH_SHORT).show();
            }
        });

        // TODO: Date waaro
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
}
class OrderViewHolder extends RecyclerView.ViewHolder {

    TextView order_id,total_amount,status,dateAndTime;
    CardView cardView;

    public OrderViewHolder(View itemView) {
        super(itemView);

        order_id = itemView.findViewById(R.id.order_rv_order_no);
        total_amount = itemView.findViewById(R.id.order_rv_total_amount);
        status=itemView.findViewById(R.id.order_rv_status);
        dateAndTime = itemView.findViewById(R.id.order_rv_data_and_time);
        cardView = itemView.findViewById(R.id.order_rv_cv);

    }
}