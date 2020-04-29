package com.example.finalyearproject_demo1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class UUUHPreviousOrderActivity extends AppCompatActivity {


    private RecyclerView uh_recyclerView;
    static ArrayList<OrderDisplayProfile> uh_list;
    static ArrayList<OrderDisplayProfile> dummy_uh_list;

    Firebase mref;
    public static UUUHPreviousOrdersDisplayAdapter uh_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uuuhprevious_order);

        uh_recyclerView=(RecyclerView)findViewById(R.id.previous_orders_myrecycler);
        uh_recyclerView.setLayoutManager(new LinearLayoutManager(this));

        uh_list=new ArrayList<OrderDisplayProfile>();
        dummy_uh_list=new ArrayList<OrderDisplayProfile>();

        String url="https://shopping-list-257d4.firebaseio.com/history-list/";
        mref=new Firebase(url);
        mref.keepSynced(true);

        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                uh_list.clear();
                dummy_uh_list.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    OrderDisplayProfile p =dataSnapshot1.getValue(OrderDisplayProfile.class);
                    uh_list.add(p);
                }

                int i;
                for(i=0;i< uh_list.size();i++){
                    //Toast.makeText(UUUHPreviousOrderActivity.this,uh_list.get(i).getEmail()+"    "+MainActivity.memail,Toast.LENGTH_SHORT).show();
                    if(uh_list.get(i).getEmail().equals(MainActivity.memail)){
                        dummy_uh_list.add(uh_list.get(i)) ;
                    }
                }

                uh_adapter=new UUUHPreviousOrdersDisplayAdapter(UUUHPreviousOrderActivity.this,dummy_uh_list);
                uh_recyclerView.setAdapter(uh_adapter);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(getApplicationContext(),"Something is wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
