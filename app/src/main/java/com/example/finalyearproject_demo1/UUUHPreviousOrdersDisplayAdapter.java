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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UUUHPreviousOrdersDisplayAdapter extends RecyclerView.Adapter<UUUHPreviousOrdersDisplayAdapter.MyViewHolder>{


    Context context;
    ArrayList<OrderDisplayProfile> profiles;

    public UUUHPreviousOrdersDisplayAdapter(Context c,ArrayList<OrderDisplayProfile> p){
        context=c;
        profiles=p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_uuuhcardview,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.order.setText(profiles.get(i).getOrder());
        myViewHolder.onClick(i);

    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView order;
        FloatingActionButton delHis;
        public MyViewHolder(View itemView){
            super(itemView);
            order=itemView.findViewById(R.id.uh_order);
            delHis=itemView.findViewById(R.id.uh_btn_del_history);
        }

        public void onClick(final int position) {
            delHis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String OrderName=profiles.get(position).getName();
                    DatabaseReference orderdel= FirebaseDatabase.getInstance().getReference("history-list").child(OrderName);
                    orderdel.removeValue();
                }
            });
        }
    }
}
