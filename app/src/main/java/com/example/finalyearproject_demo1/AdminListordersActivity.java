package com.example.finalyearproject_demo1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
//import android.support.design.widget.FloatingActionButton;
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

public class AdminListordersActivity extends AppCompatActivity {

    Firebase mref;

    private RecyclerView recyclerView;
    static ArrayList<OrderDisplayProfile> list;
   public static OrderDisplayAdapter orderadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_listorders);

        recyclerView=(RecyclerView)findViewById(R.id.list_orders_myRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list=new ArrayList<OrderDisplayProfile>();

        String url="https://shopping-list-257d4.firebaseio.com/orders-list/";
        mref=new Firebase(url);
        mref.keepSynced(true);


        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    OrderDisplayProfile p =dataSnapshot1.getValue(OrderDisplayProfile.class);
                    list.add(p);
                }

                orderadapter=new OrderDisplayAdapter(AdminListordersActivity.this,AdminListordersActivity.list);
                recyclerView.setAdapter(orderadapter);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(getApplicationContext(),"Something is wrong",Toast.LENGTH_SHORT).show();
            }
        });


    }


}
