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

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdminMyAdapter extends RecyclerView.Adapter<AdminMyAdapter.MyViewHolder>{


    Context context;
    ArrayList<AdminProfile> profiles;

    public AdminMyAdapter(Context c,ArrayList<AdminProfile> p){
        context=c;
        profiles=p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.admin_item_data,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.id.setText(profiles.get(i).getId());
        myViewHolder.name.setText(profiles.get(i).getName());
        myViewHolder.amount.setText(profiles.get(i).getAmount());
        myViewHolder.quantity.setText(profiles.get(i).getQuantity());
        myViewHolder.onClick(i);
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView id,name,amount,quantity;
        FloatingActionButton btn_delete;
        public MyViewHolder(View itemView){
            super(itemView);
            id=(TextView)itemView.findViewById(R.id.product_id);
            name=(TextView)itemView.findViewById(R.id.product_name);
            amount=(TextView)itemView.findViewById(R.id.product_amt);
            quantity=(TextView)itemView.findViewById(R.id.product_quantity);
            btn_delete=itemView.findViewById(R.id.admin_delete_product);
        }

        public void onClick(final int position)
        {
            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id=profiles.get(position).getId();
                    DatabaseReference idref= FirebaseDatabase.getInstance().getReference("persons-list").child(id);
                    idref.removeValue();
                }
            });
        }
    }
}
