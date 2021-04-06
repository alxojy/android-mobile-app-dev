package edu.monash.carsapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ListAllActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all);

        recyclerView = findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);  //A RecyclerView.LayoutManager implementation which provides similar functionality to ListView.
        recyclerView.setLayoutManager(layoutManager);

        String carsString = getIntent().getExtras().getString("cars");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Car>>(){}.getType();
        ArrayList<Car> cars = gson.fromJson(carsString,type);

        adapter = new RecyclerAdapter(cars);
        // assign (i.e. set) the adapter to the recycler view
        recyclerView.setAdapter(adapter);
    }
}
