package com.example.finalyearproject_demo1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DisplayBill extends AppCompatActivity {

    private RecyclerView recyclerView;
    Button btn,btnforhistory;
    TextView tlamt;
    public static BillDisplayMyAdapter Billdisplayadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_bill);

        btn=findViewById(R.id.bill_display_proceed_to_payment);
        btnforhistory=findViewById(R.id.bill_display_previous_orders);

        tlamt=(findViewById(R.id.Billdisplay_totalAmtView));

        recyclerView=(RecyclerView)findViewById(R.id.DisplayBill_myRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Billdisplayadapter=new BillDisplayMyAdapter(DisplayBill.this,HomeActivity.list);
        recyclerView.setAdapter(Billdisplayadapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),BeforePaymentActivity.class));
            }
        });

        btnforhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),UUUHPreviousOrderActivity.class));
            }
        });
        tlamt.setText(HomeActivity.totalAmt.getText().toString().trim());

    }
}
