package com.example.finalyearproject_demo1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import javax.xml.transform.Templates;

public class RegistrationActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button btnreg;
    private TextView signin;

    private FirebaseAuth mauth;

    private ProgressDialog mdialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mauth=FirebaseAuth.getInstance();

        mdialog=new ProgressDialog(this);

        email=findViewById(R.id.email_reg);
        password=findViewById(R.id.password_reg);

        btnreg=findViewById(R.id.btn_reg);
        signin=findViewById(R.id.signin_text);

        //btn registration to create new user in firebase
        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String memail=email.getText().toString().trim();
                String mpass=password.getText().toString().trim();

                if(TextUtils.isEmpty(memail)){
                    email.setError("required email field..");
                    return;
                }
                if(TextUtils.isEmpty(mpass)){
                    password.setError("Required Password field");
                    return;
                }
                if(mpass.length()<6){
                    password.setError("Password must Be Greater than 6 Characters");
                    return;
                }

                mdialog.setMessage("processing");
                mdialog.show();

                mauth.createUserWithEmailAndPassword(memail,mpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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


        //sign in text->forward to login form(MainActivity.class)
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }
}
