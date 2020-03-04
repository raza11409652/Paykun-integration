package com.hackdroid.paykuntestapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.paykun.sdk.PaykunApiCall;
import com.paykun.sdk.eventbus.Events;
import com.paykun.sdk.eventbus.GlobalBus;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String accessTokenSandbox = "10F17D34493F88B4215AD99748B87842";
        String merchantIdSandbox = "848390085099124";


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("merchant_id", merchantIdSandbox);
            jsonObject.put("access_token", accessTokenSandbox);
            jsonObject.put("customer_name", "Delsto");
            jsonObject.put("customer_email", "dev.delsto@gmail.com");
            jsonObject.put("customer_phone", "9835555982");
            jsonObject.put("product_name", "DELSTO_TEST_PROD");
            jsonObject.put("order_no", System.currentTimeMillis()); // order no. should have 10 to 30 character in numeric format
            jsonObject.put("amount", "10.0");  // minimum amount should be 10
            jsonObject.put("isLive", false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        new PaykunApiCall.Builder(MainActivity.this).sendJsonObject(jsonObject);

    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void getResult(Events.PaymentMessage message) {
        Log.d(TAG, "getResult: " + message);


    }


    @Override
    protected void onStart() {
        super.onStart();
        GlobalBus.getBus().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        GlobalBus.getBus().unregister(this);
    }
}

