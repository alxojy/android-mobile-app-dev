package edu.monash.carsapp.provider;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.util.List;

public class CarRepository {

    private final CarDao carDao;
    private final LiveData<List<Car>> allCars;
    //private final DatabaseReference carsRef;

    CarRepository(Application application) {
        CarDatabase db = CarDatabase.getDatabase(application);
        carDao = db.carDao();
        allCars = carDao.getAllCars();

        //carsRef = FirebaseDatabase.getInstance().getReference().child("Car");
    }

    LiveData<List<Car>> getAllCars() {
        return allCars;
    }

    void insert(Car car) {
        CarDatabase.databaseWriteExecutor.execute(() -> {
            carDao.addCar(car);
        });
        //carsRef.push().setValue(car);
    }

    void deleteAll() {
        CarDatabase.databaseWriteExecutor.execute(carDao::deleteAllCars);
        //carsRef.removeValue();
    }

    void deleteCar(String model) {
        CarDatabase.databaseWriteExecutor.execute(() -> {
            carDao.deleteCar(model);
        });
    }

    void getLargestOrder() {
        CarDatabase.databaseWriteExecutor.execute(carDao::deleteAllCars);
    }
}
