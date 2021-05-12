package edu.monash.carsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.solver.state.Dimension;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.SharedPreferences;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.StringTokenizer;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import edu.monash.carsapp.provider.Car;
import edu.monash.carsapp.provider.CarViewModel;

public class MainActivity extends AppCompatActivity {

    private final String SAVED_FILENAME = "CAR_APP_WEEK_7";

    // key values to save in SharedPreferences file
    private final String MAKER_STR = "maker";
    private final String MODEL_STR = "model";
    private final String YEAR_STR = "year";
    private final String COLOR_STR = "color";
    private final String SEATS_STR = "seats";
    private final String PRICE_STR = "price";

    private EditText makerEditText;
    private EditText modelEditText;
    private EditText yearEditText;
    private EditText colorEditText;
    private EditText seatsEditText;
    private EditText priceEditText;
    private DrawerLayout drawerLayout;

    private int x, y, newX, newY;
    private final int X_MAX = 300;
    private final int Y_MAX = 300;
    private final int X_MIN = 40;
    private final int Y_MIN = 40;

    Toolbar toolbar;
    FloatingActionButton fab;

    CarViewModel carViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_main);

        // text fields
        makerEditText = (EditText)findViewById(R.id.makerEditText);
        modelEditText = (EditText)findViewById(R.id.modelEditText);
        yearEditText = (EditText)findViewById(R.id.yearEditText);
        colorEditText = (EditText)findViewById(R.id.colorEditText);
        seatsEditText = (EditText)findViewById(R.id.seatsEditText);
        priceEditText = (EditText)findViewById(R.id.priceEditText);

        toolbar = findViewById(R.id.toolbar);
        NavigationView navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer_layout);

        setSupportActionBar(toolbar);

        // hook drawer & toolbar
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new MyNavigationListener());

        // attach listener to FAB when clicked
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewCar();
            }
        });

        carViewModel = new ViewModelProvider(this).get(CarViewModel.class);

        // restore saved preferences
        onRestoreSharedPreferences();

        // request permission to access SMS
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, 0);
        // create and instantiate local broadcast receiver
        MyBroadCastReceiver myBroadCastReceiver = new MyBroadCastReceiver();
        // register broadcast handler with intent filter
        registerReceiver(myBroadCastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER));

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getApplicationContext().getDisplay().getRealMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        View mainLayout = findViewById(R.id.main_layout);
        mainLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getActionMasked();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        x = (int)event.getX();
                        y = (int)event.getY();
                        if (0 <= x && x <= 200 && 0 <= y && y <= 200) {
                            priceEditText.setText(Math.max(0, Integer.parseInt(priceEditText.getText().toString().trim()) - 50) + "");
                        }
                        else if (width - 200 <= x && x <= width && 0 <= y && y <= 200) {
                            priceEditText.setText((Integer.parseInt(priceEditText.getText().toString().trim()) + 50) + "");
                        }
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        return true;
                    case (MotionEvent.ACTION_UP):
                        newX = (int)event.getX();
                        newY = (int)event.getY();
                        // swipe right
                        if ((newX - x > X_MAX) && (Math.abs(newY - y) < Y_MIN)) {
                            addNewCar();
                        }
                        // swipe down
                        else if ((Math.abs(newX - x) < X_MIN) && (newY - y > Y_MAX)) {
                            clearAll();
                        }
                        return true;
                    default :
                        return false;
                }
            }
        });
    }

    // add new car to listItems
    private void addNewCar() {
        Toast.makeText(getApplicationContext(), "We added a new car: " + makerEditText.getText().toString(), Toast.LENGTH_LONG).show();

        Car car = new Car(makerEditText.getText().toString(), modelEditText.getText().toString(),
                yearEditText.getText().toString(), colorEditText.getText().toString(),
                        seatsEditText.getText().toString(), priceEditText.getText().toString());

        carViewModel.insert(car);
        onSaveSharedPreferences();
    }

    // clears all fields & removes value of the last car added
    public void clearAll() {
        makerEditText.setText("");
        modelEditText.setText("");
        yearEditText.setText("");
        colorEditText.setText("");
        seatsEditText.setText("");
        priceEditText.setText("");

        getSharedPreferences(SAVED_FILENAME, Context.MODE_PRIVATE).edit().clear().apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id == R.id.clear) {
            clearAll();
        }
        return true;
    }

    class MyNavigationListener implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // get the id of the selected item
            int id = item.getItemId();

            switch (id) {
                case R.id.add_car_menu:
                    addNewCar();
                    break;
                case R.id.remove_last_menu:
                    /*int carId = adapter.getLastElemId();
                    if (carId != -1) {
                        carViewModel.deleteCar(carId);
                    }*/
                    break;
                case R.id.remove_all_menu:
                    carViewModel.deleteAll();
                    break;
                case R.id.list_all_menu:
                    openListAllActivity();
                    break;
            }

            // close the drawer
            drawerLayout.closeDrawers();
            // tell the OS
            return true;
        }
    }

    private void openListAllActivity() {
        Intent intent = new Intent(this, ListAllActivity.class);
        startActivity(intent);
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
    }

    // sms
    class MyBroadCastReceiver extends BroadcastReceiver {
        // 'onReceive' will get executed every time class SMSReceive sends a broadcast
        @Override
        public void onReceive(Context context, Intent intent) {
            // retrieve the message from the intent
            String msg = intent.getStringExtra(SMSReceiver.SMS_MSG_KEY);

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

            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        }
    }
}