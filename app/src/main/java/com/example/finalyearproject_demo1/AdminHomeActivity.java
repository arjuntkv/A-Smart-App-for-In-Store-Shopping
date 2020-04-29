package com.example.finalyearproject_demo1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class AdminHomeActivity extends AppCompatActivity {

    private Button add_btn;
    private Button list_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window g=getWindow();
        g.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,WindowManager.LayoutParams.TYPE_STATUS_BAR);
        setContentView(R.layout.activity_admin_home);

        add_btn=(findViewById(R.id.ad_btn_add));
        list_btn=(findViewById(R.id.ad_btn_list));

        //forward to add products page(AdminAddproductsActivity.class)
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), AdminListProductsActivity.class));

            }
        });

        //forward to List customers orders page(AdminAddordersActivity.class)
        list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),AdminListordersActivity.class));

            }
        });
    }
}
