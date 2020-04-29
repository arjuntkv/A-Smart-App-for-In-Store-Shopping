package com.example.finalyearproject_demo1;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.Scanner;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    public static MyAdapter adapter;
    Firebase mref;

    final int CAMERA_PERMISSION_REQUEST_CODE=1;

    ZXingScannerView ScannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScannerView=new ZXingScannerView(this);
        setContentView(ScannerView);
    }

    @Override
    public void handleResult(Result result) {

       // HomeActivity.Scannerdata.setText(result.getText());


        String resultcode=result.getText();

        for(int j=0;j<HomeActivity.list.size();j++){
            String iiid=HomeActivity.list.get(j).getId();
            if(resultcode.equals(iiid)){
                Toast.makeText(ScanCodeActivity.this,"Product already available on the cart...",Toast.LENGTH_SHORT).show();
                onBackPressed();
                return;
            }
        }

        boolean value=true;
        int size=MainActivity.llist.size();
        for(int i=0;i<size;i++){
            String data=MainActivity.llist.get(i).getId();
            if(resultcode.equals(data)){
                value=false;
                break;
            }
        }
        if(value){
            Toast.makeText(ScanCodeActivity.this,"Invalid product",Toast.LENGTH_SHORT).show();
            onBackPressed();
            return;
        }

            String url="https://shopping-list-257d4.firebaseio.com/persons-list/"+resultcode;
            mref=new Firebase(url);
            mref.keepSynced(true);

            mref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Profile p =dataSnapshot.getValue(Profile.class);
                    HomeActivity.list.add(p);

                    adapter=new MyAdapter(ScanCodeActivity.this,HomeActivity.list);
                    HomeActivity.recyclerView.setAdapter(adapter);

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    Toast.makeText(getApplicationContext(),"Something is wrong",Toast.LENGTH_SHORT).show();
                }
            });



        onBackPressed();

    }

    @Override
    protected void onPause() {
        super.onPause();

        ScannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();

        ScannerView.setResultHandler(this);
        ScannerView.startCamera();
    }
}
