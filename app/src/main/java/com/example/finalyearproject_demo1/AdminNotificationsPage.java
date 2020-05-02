package com.example.finalyearproject_demo1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AdminNotificationsPage extends AppCompatActivity {
    public static String result="";
    public static int count=0;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_notifications_page);

        txt=(findViewById(R.id.noti_txt));
        txt.setText(result);
    }

    public static void Notification_method(){
        int size=MainActivity.llist.size();
        result=" ";
        count=0;
        for(int i=0;i<size;i++){
            if(Integer.parseInt(MainActivity.llist.get(i).getQuantity())<=0){
                count++;
                result=result+MainActivity.llist.get(i).getName()+" is Out of Stock\n";
            }
        }
        if(count<=0){
            result="Sorry No Notifications...";
        }
    }
}
