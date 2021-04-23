package com.fit2081.roomcp_b;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    TextView tV;
    Uri uri;
    Cursor result;

//    public static final String COLUMN_NAME = "taskName";
//    public static final String COLUMN_DESCRIPTION = "taskDescription";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tV = findViewById(R.id.textView_id);
        uri= Uri.parse("content://fit2081.app.megan/car");
        result = getContentResolver().query(uri,null,null,null);
        tV.setText(result.getCount()+"");

//        ContentValues values= new ContentValues();
//        values.put(COLUMN_NAME,"New Task Name");
//        values.put(COLUMN_DESCRIPTION,"New Desc");
//        Uri uri2= getContentResolver().insert(uri,values);
//        Toast.makeText(this,uri2.toString(),Toast.LENGTH_LONG).show();
    }

    public void refresh(View v) {
        result = getContentResolver().query(uri,null,null,null);
        tV.setText(result.getCount()+"");
    }

    public void delete(View v) {
        EditText year = findViewById(R.id.editTextYear);
        String[] args = new String[] {year.getText().toString()};
        int values = getContentResolver().delete(uri, "year < ?", args);
        Toast.makeText(v.getContext(), values + " cars deleted", Toast.LENGTH_SHORT).show();
    }
}