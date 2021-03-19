package edu.monash.carsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;
import android.os.Bundle;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    private final String SAVED_FILENAME = "CAR_APP_WEEK_3";
    private final String car_maker_file = "carMakerFile";

    // key values to save in SharedPreferences file
    private final String MAKER_STR = "maker";
    private final String MODEL_STR = "model";
    private final String YEAR_STR = "year";
    private final String COLOR_STR = "color";
    private final String SEATS_STR = "seats";
    private final String PRICE_STR = "price";

    private Button addNewCarBtn;
    private EditText makerEditText;
    private EditText modelEditText;
    private EditText yearEditText;
    private EditText colorEditText;
    private EditText seatsEditText;
    private EditText priceEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addNewCarBtn = (Button)findViewById(R.id.addBtn);
        makerEditText = (EditText)findViewById(R.id.makerEditText);
        modelEditText = (EditText)findViewById(R.id.modelEditText);
        yearEditText = (EditText)findViewById(R.id.yearEditText);
        colorEditText = (EditText)findViewById(R.id.colorEditText);
        seatsEditText = (EditText)findViewById(R.id.seatsEditText);
        priceEditText = (EditText)findViewById(R.id.priceEditText);

        onRestoreSharedPreferences();

        // request permission to access SMS
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, 0);
        // create and instantiate local broadcast receiver
        MyBroadCastReceiver myBroadCastReceiver = new MyBroadCastReceiver();
        // register broadcast handler with intent filter
        registerReceiver(myBroadCastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER));
    }

    class MyBroadCastReceiver extends BroadcastReceiver {
         // 'onReceive' will get executed every time class SMSReceive sends a broadcast
        @Override
        public void onReceive(Context context, Intent intent) {
            // retrieve the message from the intent
            String msg = intent.getStringExtra(SMSReceiver.SMS_MSG_KEY);
            Toast.makeText(getApplicationContext(), msg, 10).show();

            // String Tokenizer is used to parse the incoming message
            StringTokenizer sT = new StringTokenizer(msg, ";");
            String maker = sT.nextToken();
            String model = sT.nextToken();
            String year = sT.nextToken();
            String color = sT.nextToken();
            String seats = sT.nextToken();
            String price = sT.nextToken();

            // update UI
            makerEditText.setText(maker);
            modelEditText.setText(model);
            yearEditText.setText(year);
            colorEditText.setText(color);
            seatsEditText.setText(seats);
            priceEditText.setText(price);
        }
    }

    // save view data when rotating device
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(MAKER_STR, makerEditText.getText().toString());
        outState.putString(MODEL_STR, modelEditText.getText().toString());
        outState.putString(YEAR_STR, yearEditText.getText().toString());
        outState.putString(COLOR_STR, colorEditText.getText().toString());
        outState.putString(SEATS_STR, seatsEditText.getText().toString());
        outState.putString(PRICE_STR, priceEditText.getText().toString());
    }

    // restore view data after rotating device
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        makerEditText.setText(savedInstanceState.getString(MAKER_STR));
        modelEditText.setText(savedInstanceState.getString(MODEL_STR));
        yearEditText.setText(savedInstanceState.getString(YEAR_STR));
        colorEditText.setText(savedInstanceState.getString(COLOR_STR));
        seatsEditText.setText(savedInstanceState.getString(SEATS_STR));
        priceEditText.setText(savedInstanceState.getString(PRICE_STR));
    }

    // save last added car
    private void onSaveSharedPreferences() {
        SharedPreferences sharedPref = getSharedPreferences(SAVED_FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPref.edit();

        SharedPreferences sharedPref2 = getSharedPreferences(car_maker_file, Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor2 = sharedPref2.edit();

        sharedPrefEditor2.putString(MAKER_STR, makerEditText.getText().toString());
        sharedPrefEditor.putString(MODEL_STR, modelEditText.getText().toString());
        sharedPrefEditor.putString(YEAR_STR, yearEditText.getText().toString());
        sharedPrefEditor.putString(COLOR_STR, colorEditText.getText().toString());
        sharedPrefEditor.putString(SEATS_STR, seatsEditText.getText().toString());
        sharedPrefEditor.putString(PRICE_STR, priceEditText.getText().toString());

        sharedPrefEditor.apply();
        sharedPrefEditor2.apply();
    }

    // restore last added car when reopening app
    private void onRestoreSharedPreferences() {
        SharedPreferences sharedPref = getSharedPreferences(SAVED_FILENAME, Context.MODE_PRIVATE);
        SharedPreferences sharedPref2 = getSharedPreferences(car_maker_file, Context.MODE_PRIVATE);

        makerEditText.setText(sharedPref2.getString(MAKER_STR, ""));
        modelEditText.setText(sharedPref.getString(MODEL_STR, ""));
        yearEditText.setText(sharedPref.getString(YEAR_STR, ""));
        colorEditText.setText(sharedPref.getString(COLOR_STR, ""));
        seatsEditText.setText(sharedPref.getString(SEATS_STR, ""));
        priceEditText.setText(sharedPref.getString(PRICE_STR, ""));
    }

    // add new car and save it as persistent data
    public void onClickAddButton(View view) {
        Toast.makeText(getApplicationContext(), "We added a new car: " + makerEditText.getText().toString(), 10).show();
        onSaveSharedPreferences();
    }

    // add new car and save it as persistent data
    public void onClickLoadButton(View view) {
        SharedPreferences sharedPref = getSharedPreferences(car_maker_file, Context.MODE_PRIVATE);
        makerEditText.setText(sharedPref.getString(MAKER_STR, ""));
    }

    // clears all fields & removes value of the last car added
    public void onClickClearAllButton(View view) {
        makerEditText.setText("");
        modelEditText.setText("");
        yearEditText.setText("");
        colorEditText.setText("");
        seatsEditText.setText("");
        priceEditText.setText("");

        getSharedPreferences(car_maker_file, Context.MODE_PRIVATE).edit().clear().commit();
        getSharedPreferences(SAVED_FILENAME, Context.MODE_PRIVATE).edit().clear().commit();
    }
}