package edu.monash.carsapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.monash.carsapp.provider.Car;

// reference: https://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView
public class ListViewAdapter extends ArrayAdapter<Car> {

    List<Car> cars;

    public ListViewAdapter(Context context) {
        super(context, 0);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_layout, parent, false);
        }
        // Get the data item for this position
        Car car = getItem(position);
        // Lookup view for data population
        TextView carText = (TextView) convertView.findViewById(R.id.list_car_item);
        // Populate the data into the template view using the data object
        carText.setText(car.getMaker() + " | " + car.getModel());
        // Return the completed view to render on screen
        return convertView;
    }

    @Override
    public int getCount() {
        if (cars == null) {
            return 0;
        }
        return cars.size();
    }

    @Override
    public Car getItem(final int position) {
        return cars.get(position);
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public int getLastElemId() {
        if (cars.size() == 0) {
            return -1;
        }
        else {
            return cars.get(cars.size()-1).getId();
        }
    }
}
