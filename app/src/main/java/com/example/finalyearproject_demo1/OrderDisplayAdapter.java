package com.example.finalyearproject_demo1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class OrderDisplayAdapter extends RecyclerView.Adapter<OrderDisplayAdapter.MyViewHolder>{


    Context context;
    ArrayList<OrderDisplayProfile> profiles;

    public OrderDisplayAdapter(Context c,ArrayList<OrderDisplayProfile> p){
        context=c;
        profiles=p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.order_display_cardview,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.name.setText(profiles.get(i).getName());
        myViewHolder.mobileno.setText(profiles.get(i).getMobileno());
        myViewHolder.order.setText(profiles.get(i).getOrder());
       if(profiles.get(i).getPermission().equals("true")){
           myViewHolder.Permission.setVisibility(View.VISIBLE);
       }
        myViewHolder.onClick(i);
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name,mobileno,order;
        TextView Permission;
        FloatingActionButton btnclose;
        public MyViewHolder(View itemView){
            super(itemView);
            name=itemView.findViewById(R.id.name);
            mobileno=itemView.findViewById(R.id.order_display_mobno);
            order=itemView.findViewById(R.id.order);
            Permission=itemView.findViewById(R.id.permission_txt);
            btnclose=itemView.findViewById(R.id.admin_btn_close_order);
        }

        public void onClick(final int position) {
            btnclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String OrderName=profiles.get(position).getName();
                    DatabaseReference orderdel= FirebaseDatabase.getInstance().getReference("orders-list").child(OrderName);
                    orderdel.removeValue();
                }
            });
        }
    }
}
