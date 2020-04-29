package com.example.finalyearproject_demo1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collection;

public class BeforePaymentActivity extends AppCompatActivity {

    public static String amount;
    String permission;
    EditText name;
    EditText mobileno;
    TextView totaltextview;
    Button payment_btn;
    CheckBox cb;
    public static orderProfile ob;
    public static String Uname;
    public static String Umobile;


    //listorder setction
    //public static ArrayList<orderProfile> orderlist;
    final int SEND_SMS_PERMISSION_REQUEST_CODE=1;

    //database ref
     public static DatabaseReference mDatabase;
    public static DatabaseReference mDatabase1;
    public static DatabaseReference updateDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_payment);

        //orderlist=new ArrayList<orderProfile>();

        final StringBuffer result = new StringBuffer();


        name=(EditText) findViewById(R.id.edt_user_name);
        mobileno=(EditText) findViewById(R.id.edt_mobileno);
        cb=(CheckBox)findViewById(R.id.checkbox_permission);

        permission="false";

        totaltextview=(TextView)findViewById(R.id.total_amount);
        payment_btn=(Button)findViewById(R.id.btn_proceed_payment2);

        amount=HomeActivity.totalAmt.getText().toString().trim();

        totaltextview.setText(amount);

        mDatabase= FirebaseDatabase.getInstance().getReference().child("orders-list");
        mDatabase1=FirebaseDatabase.getInstance().getReference().child("history-list");
        updateDB=FirebaseDatabase.getInstance().getReference().child("persons-list");
        mDatabase.keepSynced(true);
        mDatabase1.keepSynced(true);
        updateDB.keepSynced(true);



        payment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Uname=name.getText().toString().trim();
                Umobile=mobileno.getText().toString().trim();

                if(TextUtils.isEmpty(Uname)){
                    name.setError("required name field..");
                    return;
                }
                if(TextUtils.isEmpty(Umobile)){
                    mobileno.setError("Required Mobile Number field..");
                    return;
                }

                if(Umobile.length()!=10){
                    mobileno.setError("Invalid mobile no");
                    return;
                }

                if(cb.isChecked()){
                    permission="true";
                }

                //orders display section
                    int size=HomeActivity.list.size();
                    result.delete(0, result.length());//has to check this
                    for(int i=0;i<size;i++){
                       String name= HomeActivity.list.get(i).getName();
                        String quantity=HomeActivity.list.get(i).getQuan();

                        result.append("Product:  "+ name+"("+quantity+")"+"\n");

                    }
                String newString=result.toString();
                ob=new orderProfile(MainActivity.memail,Uname,Umobile,newString,permission);

                //need to add message dialog like something from AdminLoginActivity.java
                //SetOrderonDatabase();

                permission="false";

                //updateItems();

                //Toast.makeText(BeforePaymentActivity.this,"Order Successfully Added...",Toast.LENGTH_SHORT).show();


                //Next step to payment processing
                Intent intent = new Intent(BeforePaymentActivity.this, StartPaymentActivity.class);
                intent.putExtra("phone", mobileno.getText().toString());
                intent.putExtra("amount", HomeActivity.totalAmt.getText().toString());
                startActivity(intent);



            }
        });



    }

    public static void SetOrderonDatabase(){
        mDatabase.child(Uname).setValue(ob);
        mDatabase1.child(Uname).setValue(ob);
    }

    public static void updateItems(){
        int size=HomeActivity.list.size();
        for(int i=0;i<size;i++){
            String id= HomeActivity.list.get(i).getId();

            String quantity=HomeActivity.list.get(i).getQuantity();
            String quan=HomeActivity.list.get(i).getQuan();
            int q1=Integer.parseInt(quantity);
            int q2=Integer.parseInt(quan);

            updateDB.child(id).child("quantity").setValue(q1-q2);
        }
    }


}
