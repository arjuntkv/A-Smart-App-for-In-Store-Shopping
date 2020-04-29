package com.example.finalyearproject_demo1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class BillDisplayMyAdapter extends RecyclerView.Adapter<BillDisplayMyAdapter.MyViewHolder> {

    Context context;
    public static ArrayList<Profile> profiles;

    int totalAmount;


    public BillDisplayMyAdapter(Context c, ArrayList<Profile> p){
        context=c;
        profiles=p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.bill_display_cardview,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.name.setText(profiles.get(i).getName());
        myViewHolder.amount.setText(profiles.get(i).getAmount());
        myViewHolder.quan.setText(profiles.get(i).getQuan());
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView amount;
        TextView quan;

        public MyViewHolder(View itemView){
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.bill_display_card_name);
            amount=(TextView)itemView.findViewById(R.id.bill_display_card_amount);
            quan=(TextView) itemView.findViewById(R.id.bill_display_card_quantity);
        }

    }
}
