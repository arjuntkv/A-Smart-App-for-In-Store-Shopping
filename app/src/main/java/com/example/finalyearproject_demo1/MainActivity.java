package com.example.finalyearproject_demo1;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText email;
    private EditText pass;
    private Button btnlogin;
    private TextView signup;

    static String memail;

    Firebase mref;
    static ArrayList<ProductAvailableProfile> llist;

    private Button adminbtnlogin;

    private FirebaseAuth mauth;

    private ProgressDialog mdialog;

    //for accessing sms and camera
    final int SEND_SMS_PERMISSION_REQUEST_CODE=1;
    final int CAMERA_PERMISSION_REQUEST_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window g=getWindow();
        g.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,WindowManager.LayoutParams.TYPE_STATUS_BAR);
        setContentView(R.layout.activity_main);

                //for accessing camera and sms
                //for accessing camera
                if(checkPermission(Manifest.permission.CAMERA)==false ||checkPermission(Manifest.permission.SEND_SMS)==false) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.SEND_SMS
                                    }, CAMERA_PERMISSION_REQUEST_CODE);
                }


        mauth=FirebaseAuth.getInstance();

        mdialog=new ProgressDialog(this);

                //to check the product is available or not
                llist=new ArrayList<ProductAvailableProfile>();

                String url="https://shopping-list-257d4.firebaseio.com/persons-list/";
                mref=new Firebase(url);
                mref.keepSynced(true);

                mref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        llist.clear();
                        for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                            ProductAvailableProfile p =dataSnapshot1.getValue(ProductAvailableProfile.class);
                            llist.add(p);
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        Toast.makeText(getApplicationContext(),"Something is wrong",Toast.LENGTH_SHORT).show();
                    }
                });

                //checking ends

        email=findViewById(R.id.email_login);
        pass=findViewById(R.id.password_login);

        btnlogin=findViewById(R.id.btn_login);
        signup=findViewById(R.id.signup_text);

        adminbtnlogin=findViewById(R.id.ad_btn);

        //btn login ->forward to homeactivity
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                memail=email.getText().toString().trim();
                String mpass=pass.getText().toString().trim();

                if(TextUtils.isEmpty(memail)){
                    email.setError("required email field..");
                    return;
                }
                if(TextUtils.isEmpty(mpass)){
                    pass.setError("Required password field");
                    return;
                }


                mdialog.setMessage("processing...");
                mdialog.show();

                mauth.signInWithEmailAndPassword(memail,mpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                            Toast.makeText(getApplicationContext(),"successfull",Toast.LENGTH_SHORT).show();
                            mdialog.dismiss();
                        }else{
                            Toast.makeText(getApplicationContext(),"failed..",Toast.LENGTH_SHORT).show();
                            mdialog.dismiss();
                        }
                    }
                });


            }
        });
        //btn login ends


        //sign up text->forward to registration page(RegistrationActivity)
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));
            }
        });


        //for admin login
        adminbtnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AdminLoginActivity.class));
            }
        });



    }

    public boolean checkPermission(String permission){
        int check= ContextCompat.checkSelfPermission(this,permission);
        return (check== PackageManager.PERMISSION_GRANTED);
    }
}
