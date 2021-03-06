package com.example.finalyearproject_demo1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartPaymentActivity extends AppCompatActivity {
    PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();
    //declare paymentParam object
    PayUmoneySdkInitializer.PaymentParam paymentParam = null;
    String TAG ="mainActivity", txnid ="txt12346", amount ="20", phone ="",
            prodname ="BlueApp Course", firstname ="kamal", email ="praveen@gmail.com",
            merchantId ="6969213", merchantkey="o7Y7Jy7w",salt="8pgvtjOyV8";

    TextView status ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_payment);
        Intent intent = getIntent();
        phone = intent.getExtras().getString("phone");
        amount = intent.getExtras().getString("amount");
        status=  findViewById(R.id.status);
        startpay();

    }
    public void startpay(){
        builder.setAmount(amount)                          // Payment amount
                .setTxnId(txnid)                     // Transaction ID
                .setPhone(phone)                   // User Phone number
                .setProductName(prodname)                   // Product Name or description
                .setFirstName(firstname)                              // User First name
                .setEmail(email)              // User Email ID
                .setsUrl("https://www.payumoney.com/mobileapp/payumoney/success.php")     // Success URL (surl)
                .setfUrl("https://www.payumoney.com/mobileapp/payumoney/failure.php")     //Failure URL (furl)
                .setUdf1("")
                .setUdf2("")
                .setUdf3("")
                .setUdf4("")
                .setUdf5("")
                .setUdf6("")
                .setUdf7("")
                .setUdf8("")
                .setUdf9("")
                .setUdf10("")
                .setIsDebug(true)                              // Integration environment - true (Debug)/ false(Production)
                .setKey(merchantkey)                        // Merchant key
                .setMerchantId(merchantId);
        try {
            paymentParam = builder.build();
            // generateHashFromServer(paymentParam );
            getHashkey();
        } catch (Exception e) {
            Log.e(TAG, " error s "+e.toString());
        }
    }

    public void getHashkey(){
        ServiceWrapper service = new ServiceWrapper(null);
        //  String hashSequence = merchantkey+"|"+txnid+"|"+amount+"|"+prodname+"|"+firstname+"|"+email+"|"+"|||||||||||"+salt;
        //    final String serverCalculatedHash= hashCal("SHA-512", hashSequence);
        Call<String> call = service.newHashCall(merchantkey, txnid, amount, prodname,firstname, email);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e(TAG, "hash res "+response.body());
                String merchantHash= response.body();
                if (merchantHash.isEmpty() || merchantHash.equals("")) {
                    Toast.makeText(StartPaymentActivity.this, "Could not generate hash", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "hash empty");
                } else {
                    // mPaymentParams.setMerchantHash(merchantHash);
                    paymentParam.setMerchantHash(merchantHash);
                    // Invoke the following function to open the checkout page.
                    // PayUmoneyFlowManager.startPayUMoneyFlow(paymentParam, StartPaymentActivity.this,-1, true);
                    PayUmoneyFlowManager.startPayUMoneyFlow(paymentParam, StartPaymentActivity.this, R.style.AppTheme_default, false);
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "hash error "+ t.toString());
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
// PayUMoneySdk: Success -- payuResponse{"id":225642,"mode":"CC","status":"success","unmappedstatus":"captured","key":"9yrcMzso","txnid":"223013","transaction_fee":"20.00","amount":"20.00","cardCategory":"domestic","discount":"0.00","addedon":"2018-12-31 09:09:43","productinfo":"a2z shop","firstname":"kamal","email":"kamal.bunkar07@gmail.com","phone":"9144040888","hash":"b22172fcc0ab6dbc0a52925ebbd0297cca6793328a8dd1e61ef510b9545d9c851600fdbdc985960f803412c49e4faa56968b3e70c67fe62eaed7cecacdfdb5b3","field1":"562178","field2":"823386","field3":"2061","field4":"MC","field5":"167227964249","field6":"00","field7":"0","field8":"3DS","field9":" Verification of Secure Hash Failed: E700 -- Approved -- Transaction Successful -- Unable to be determined--E000","payment_source":"payu","PG_TYPE":"AXISPG","bank_ref_no":"562178","ibibo_code":"VISA","error_code":"E000","Error_Message":"No Error","name_on_card":"payu","card_no":"401200XXXXXX1112","is_seamless":1,"surl":"https://www.payumoney.com/sandbox/payment/postBackParam.do","furl":"https://www.payumoney.com/sandbox/payment/postBackParam.do"}
        // Result Code is -1 send from Payumoney activity
        Log.e("StartPaymentActivity", "request code " + requestCode + " resultcode " + resultCode);
        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && data != null) {
            TransactionResponse transactionResponse = data.getParcelableExtra( PayUmoneyFlowManager.INTENT_EXTRA_TRANSACTION_RESPONSE );
            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {
                if(transactionResponse.getTransactionStatus().equals( TransactionResponse.TransactionStatus.SUCCESSFUL )){
                    //    Toast.makeText(this,"successfull",Toast.LENGTH_SHORT);
                } else{
                    //  Toast.makeText(this,"failed",Toast.LENGTH_SHORT);
                    //Failure Transaction
                }
                // Response from Payumoney
                String payuResponse = transactionResponse.getPayuResponse();
                if(payuResponse.contains("\"status\":\"success\"")){
                    Log.e(TAG,"successfull with payu");
                    BeforePaymentActivity.SetOrderonDatabase();
                    BeforePaymentActivity.updateItems();
                    SendMessage();
                    status.setText("Payment Successfull");
                }
                else {
                    Log.e(TAG, "failed with payu");
                    status.setText("Payment Failed");
                }

                // Response from SURl and FURL
                String merchantResponse = transactionResponse.getTransactionDetails();
                Log.e(TAG, "tran "+payuResponse+"---"+ merchantResponse);
            } /* else if (resultModel != null && resultModel.getError() != null) {
                Log.d(TAG, "Error response : " + resultModel.getError().getTransactionResponse());
            } else {
                Log.d(TAG, "Both objects are null!");
            }*/
        }else{
            Log.e(TAG,"Transaction Cancelled");
            status.setText("Transaction Cancelled");
        }
    }

    public void SendMessage(){
        if(checkPermission(Manifest.permission.SEND_SMS)){
            SmsManager smsManager=SmsManager.getDefault();
            smsManager.sendTextMessage(BeforePaymentActivity.Umobile,null,
                    "Payment Success!!! - Smart App for In-Store Shopping. " +
                    "Payment Successfully Done, "+BeforePaymentActivity.Uname + ".  " +
                    "Rs:"+BeforePaymentActivity.amount +"               Thanks For Using Our App :)"
                    ,null,null);
        }
    }

    public boolean checkPermission(String permission){
        int check= ContextCompat.checkSelfPermission(this,permission);
        return (check== PackageManager.PERMISSION_GRANTED);
    }
}
