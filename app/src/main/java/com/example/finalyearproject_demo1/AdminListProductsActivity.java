package com.example.finalyearproject_demo1;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;;import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdminListProductsActivity extends AppCompatActivity {

    private FloatingActionButton fab_btn;

    private DatabaseReference mDatabase;
    Firebase mref;

    private RecyclerView recyclerView;
    static ArrayList<AdminProfile> list;
     AdminMyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_list_products);

        recyclerView=(RecyclerView)findViewById(R.id.admin_myRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list=new ArrayList<AdminProfile>();

        mDatabase=FirebaseDatabase.getInstance().getReference().child("persons-list");
        mDatabase.keepSynced(true);

        String url="https://shopping-list-257d4.firebaseio.com/persons-list/";
        mref=new Firebase(url);
        mref.keepSynced(true);

        fab_btn=findViewById(R.id.fab_addproduct);

        fab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog();
            }
        });


        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    AdminProfile p =dataSnapshot1.getValue(AdminProfile.class);
                    list.add(p);
                }

                adapter=new AdminMyAdapter(AdminListProductsActivity.this,list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(getApplicationContext(),"Something is wrong",Toast.LENGTH_SHORT).show();
            }
        });


    }



    private void customDialog(){
        AlertDialog.Builder mydialog=new AlertDialog.Builder(AdminListProductsActivity.this);

        LayoutInflater inflater=LayoutInflater.from(AdminListProductsActivity.this);
        View myview=inflater.inflate(R.layout.admin_input_data,null);

        final AlertDialog dialog=mydialog.create();

        dialog.setView(myview);

        final EditText id=myview.findViewById(R.id.edt_id);
        final EditText name=myview.findViewById(R.id.edt_name);
        final EditText amount=myview.findViewById(R.id.edt_amt);
        final EditText quantity=myview.findViewById(R.id.edt_quantity);
        final EditText quan=myview.findViewById(R.id.edt_quan);

        Button btnsave=myview.findViewById(R.id.btn_save);



        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mId=id.getText().toString().trim();
                String mName=name.getText().toString().trim();
                String mAmt=amount.getText().toString().trim();
                String mQuantity=quantity.getText().toString().trim();
                String mQuan=quan.getText().toString().trim();

                if(TextUtils.isEmpty(mId)){
                    id.setError("Required field...");
                    return;
                }

                if(TextUtils.isEmpty(mName)){
                    name.setError("Required field...");
                    return;
                }

                if(TextUtils.isEmpty(mQuan)){
                    quan.setError("Required field...");
                    return;
                }

                if(TextUtils.isEmpty(mAmt)){
                    amount.setError("Required field...");
                    return;
                }

                if(TextUtils.isEmpty(mQuantity)){
                    quantity.setError("Required field...");
                    return;
                }


                AdminProfile p=new AdminProfile(mId,mName,mAmt,mQuantity,mQuan);


                mDatabase.child(mId).setValue(p);


                Toast.makeText(AdminListProductsActivity.this,"Product Successfully Added...",Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }
        });

        dialog.show();
    }






}
