package com.example.finalyearproject_demo1;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.FloatingActionButton;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.Result;

import java.io.Console;
import java.util.ArrayList;
import java.util.Scanner;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class HomeActivity extends AppCompatActivity {

    public static ArrayList<Profile> list;
    public static RecyclerView recyclerView;
    public static TextView totalAmt;
    public static ArrayList<ProductAvailableProfile> idavailablelist;

    String amttot;
    FloatingActionButton scan_btn;
    Button add_btn;
    Button Payment1;
    Firebase mref;
    String productid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        list=new ArrayList<Profile>();

        scan_btn=findViewById(R.id.scanbutton);
        totalAmt=(TextView)findViewById(R.id.totalAmtView);

        amttot=HomeActivity.totalAmt.getText().toString().trim();

        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(),ScanCodeActivity.class));

            }
        });

        add_btn=(Button)findViewById(R.id.addbutton);
        Payment1=(Button)findViewById(R.id.btn_proceed_payment1);

        Payment1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.isEmpty())
                {
                    Toast.makeText(HomeActivity.this,"Oops!! Cart is Empty..",Toast.LENGTH_SHORT).show();
                }
                else{


                    startActivity(new Intent(getApplicationContext(),DisplayBill.class));
                }

            }
        });

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog2();
            }
        });


        recyclerView =(RecyclerView)findViewById(R.id.myRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }




    private void customDialog2() {
        AlertDialog.Builder mydialog=new AlertDialog.Builder(HomeActivity.this);

        LayoutInflater inflater=LayoutInflater.from(HomeActivity.this);
        View myview=inflater.inflate(R.layout.add_manually_box,null);

        final AlertDialog dialog=mydialog.create();

        dialog.setView(myview);

        final EditText id=myview.findViewById(R.id.edt_manual_id);

        Button btnsave1=myview.findViewById(R.id.btn_manualid_save);

        btnsave1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mId=id.getText().toString().trim();

                if(TextUtils.isEmpty(mId)){
                    id.setError("Required field...");
                    return;
                }

                if(Integer.parseInt(mId)<100 || Integer.parseInt(mId)>=150){
                    id.setError("Invalid productID");
                    return;
                }

                for(int j=0;j<list.size();j++){
                    String iiid=list.get(j).getId();
                    if(mId.equals(iiid)){
                        Toast.makeText(HomeActivity.this,"Product already available on the cart...",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }


                boolean value=true;
                int size=MainActivity.llist.size();
                for(int i=0;i<size;i++){
                    String data=MainActivity.llist.get(i).getId();
                    if(mId.equals(data)){
                        value=false;
                        break;
                    }
                }
                if(value){
                    id.setError("Invalid productID");
                    return;
                }else
                {
                    String url="https://shopping-list-257d4.firebaseio.com/persons-list/"+mId;
                    mref=new Firebase(url);
                    mref.keepSynced(true);

                    mref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Profile p =dataSnapshot.getValue(Profile.class);
                            list.add(p);

                            ScanCodeActivity.adapter=new MyAdapter(HomeActivity.this,list);
                            recyclerView.setAdapter(ScanCodeActivity.adapter);

                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                            Toast.makeText(getApplicationContext(),"Something is wrong",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    });

                    Toast.makeText(HomeActivity.this,"Product successfully added....",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

            }
        });

        dialog.show();

    }
    //custom dialog method ends


}