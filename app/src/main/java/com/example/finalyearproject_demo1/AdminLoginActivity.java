package com.example.finalyearproject_demo1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText pass;
    private Button btnlogin;

    private ProgressDialog mdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window g=getWindow();
        g.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,WindowManager.LayoutParams.TYPE_STATUS_BAR);
        setContentView(R.layout.activity_admin_login);

        email=findViewById(R.id.ad_email_login);
        pass=findViewById(R.id.ad_password_login);
        btnlogin=findViewById(R.id.ad_btn_login);

        mdialog=new ProgressDialog(this);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String aemail=email.getText().toString().trim();
                String apass=pass.getText().toString().trim();

                if(TextUtils.isEmpty(aemail)){
                    email.setError("required email field..");
                    return;
                }
                if(TextUtils.isEmpty(apass)){
                    pass.setError("Required password field");
                    return;
                }

                mdialog.setMessage("processing...");
                mdialog.show();

                if(aemail.equals("admin")&&apass.equals("123")){
                    startActivity(new Intent(getApplicationContext(),AdminHomeActivity.class));
                    Toast.makeText(getApplicationContext(),"successfull",Toast.LENGTH_SHORT).show();
                    mdialog.dismiss();
                }
                else{
                    Toast.makeText(getApplicationContext(),"failed..",Toast.LENGTH_SHORT).show();
                    mdialog.dismiss();
                }

            }
        });
    }
}
