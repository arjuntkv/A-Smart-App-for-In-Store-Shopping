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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    public static ArrayList<Profile> profiles;
    String quantity_name;

    int totalAmount;


    public MyAdapter(Context c, ArrayList<Profile> p){
        context=c;
        profiles=p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.name.setText(profiles.get(i).getName());
        myViewHolder.amount.setText(profiles.get(i).getAmount());

        myViewHolder.enterQuantity(i);
        myViewHolder.quan.setText(profiles.get(i).getQuan());

        myViewHolder.onClick(i);
        myViewHolder.calculateTotalAmount(i);

    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView amount;
        EditText quan;
        FloatingActionButton btn;
        Button okbtn;

        public MyViewHolder(View itemView){
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.card_name);
            amount=(TextView)itemView.findViewById(R.id.card_amount);
            quan=(EditText) itemView.findViewById(R.id.card_quantity);
            btn=(FloatingActionButton)itemView.findViewById(R.id.btn_close_profile);
            okbtn=(Button)itemView.findViewById(R.id.btn_ok_quantity);
        }


        public void enterQuantity(final int position){

            okbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(quan.getText().toString().trim().length() != 0){
                        quantity_name=quan.getText().toString();
                        int q=Integer.parseInt(quantity_name);
                        int totalquantity=Integer.parseInt(profiles.get(position).getQuantity());

                        if(q>0 && q<=100){

                            if(q<=totalquantity){
                                profiles.get(position).setQuan(quantity_name);
                                calculateTotalAmount(position);
                            }
                            else{
                                quan.setError("Quantity exceeded");
                            }

                        }
                        else {
                            quan.setError("Invalid quantity");
                        }
                    }
                    else {
                        quan.setError("Empty Quantity");
                    }



                }
            });

        }


        public void calculateTotalAmount(final int position){

            int size=HomeActivity.list.size();
            String listsize=String.valueOf(size);
            if(HomeActivity.list.isEmpty()){
                totalAmount=0;
                String total=String.valueOf(totalAmount);
                HomeActivity.totalAmt.setText(total);
            }
            else{
                totalAmount=0;
                for(int i=0;i<size;i++){
                    totalAmount=totalAmount+(Integer.parseInt(profiles.get(i).getAmount()) * Integer.parseInt(profiles.get(i).getQuan()));
                }
                String total=String.valueOf(totalAmount);
                //String listsize=String.valueOf(size);
                HomeActivity.totalAmt.setText(total);
            }



        }

        public void onClick(final int position){
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    //Toast.makeText(context,position+" is clicked..",Toast.LENGTH_SHORT).show();
                    HomeActivity.list.remove(position);
                    //totalAmount=totalAmount-Integer.parseInt(profiles.get(position).getAmount());
                    ScanCodeActivity.adapter.notifyDataSetChanged();

                    if(HomeActivity.list.isEmpty()){
                        totalAmount=0;
                        String total=String.valueOf(totalAmount);
                        HomeActivity.totalAmt.setText(total);
                    }
                }
            });
        }


    }
}
