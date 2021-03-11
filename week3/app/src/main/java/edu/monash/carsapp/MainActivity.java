package edu.monash.carsapp;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;
import android.os.Bundle;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private final String SAVED_FILENAME = "CAP_APP_WEEK_3";

    private Button addNewCarBtn;
    private EditText makerEditText;
    private EditText modelEditText;
    private EditText yearEditText;
    private EditText colorEditText;
    private EditText seatsEditText;
    private EditText priceEditText;
    private EditText addressEditText;

    private final String MAKER_STR = "maker";
    private final String MODEL_STR = "model";
    private final String YEAR_STR = "year";
    private final String COLOR_STR = "color";
    private final String SEATS_STR = "seats";
    private final String PRICE_STR = "price";
    private final String ADDRESS_STR = "address";

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
        addressEditText = (EditText)findViewById(R.id.addressEditText);

        onRestoreSharedPreferences();
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
        outState.putString(ADDRESS_STR, addressEditText.getText().toString());

        super.onSaveInstanceState(outState);
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
        addressEditText.setText(savedInstanceState.getString(ADDRESS_STR));
    }

    // save last added car
    private void onSaveSharedPreferences() {
        SharedPreferences sharedPref = getSharedPreferences(SAVED_FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPref.edit();

        sharedPrefEditor.putString(MAKER_STR, makerEditText.getText().toString());
        sharedPrefEditor.putString(MODEL_STR, modelEditText.getText().toString());
        sharedPrefEditor.putString(YEAR_STR, yearEditText.getText().toString());
        sharedPrefEditor.putString(COLOR_STR, colorEditText.getText().toString());
        sharedPrefEditor.putString(SEATS_STR, seatsEditText.getText().toString());
        sharedPrefEditor.putString(PRICE_STR, priceEditText.getText().toString());
        sharedPrefEditor.putString(ADDRESS_STR, addressEditText.getText().toString());

        sharedPrefEditor.apply();
    }

    // restore last added car when reopening app
    private void onRestoreSharedPreferences() {
        SharedPreferences sharedPref = getSharedPreferences(SAVED_FILENAME, Context.MODE_PRIVATE);

        makerEditText.setText(sharedPref.getString(MAKER_STR, ""));
        modelEditText.setText(sharedPref.getString(MODEL_STR, ""));
        yearEditText.setText(sharedPref.getString(YEAR_STR, ""));
        colorEditText.setText(sharedPref.getString(COLOR_STR, ""));
        seatsEditText.setText(sharedPref.getString(SEATS_STR, ""));
        priceEditText.setText(sharedPref.getString(PRICE_STR, ""));
        addressEditText.setText(sharedPref.getString(ADDRESS_STR, ""));
    }

    // add new car and save it as persistent data
    public void onClickAddButton(View view) {
        Toast.makeText(getApplicationContext(), "We added a new car: " + makerEditText.getText().toString(), 10).show();
        onSaveSharedPreferences();
    }

    // clears all fields & removes value of the last car added
    public void onClickClearAllButton(View view) {
        makerEditText.setText("");
        modelEditText.setText("");
        yearEditText.setText("");
        colorEditText.setText("");
        seatsEditText.setText("");
        priceEditText.setText("");
        addressEditText.setText("");

        getSharedPreferences(SAVED_FILENAME, Context.MODE_PRIVATE).edit().clear().commit();
    }
}