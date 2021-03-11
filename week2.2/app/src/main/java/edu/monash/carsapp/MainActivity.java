package edu.monash.carsapp;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addNewCarBtn = (Button)findViewById(R.id.button);
        EditText makerEditText = (EditText)findViewById(R.id.makerText);

        addNewCarBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "We added a new car: " + makerEditText.getText().toString(), 10).show();
            }
        });
    }
}