package edu.monash.carsapp.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CarRepository {

    private final CarDao carDao;
    private final LiveData<List<Car>> allCars;

    CarRepository(Application application) {
        CarDatabase db = CarDatabase.getDatabase(application);
        carDao = db.carDao();
        allCars = carDao.getAllCars();
    }

    LiveData<List<Car>> getAllCars() {
        return allCars;
    }

    void insert(Car car) {
        CarDatabase.databaseWriteExecutor.execute(() -> {
            carDao.addCar(car);
        });
    }

    void deleteAll() {
        CarDatabase.databaseWriteExecutor.execute(carDao::deleteAllCars);
    }

    void deleteCar(String model) {
        CarDatabase.databaseWriteExecutor.execute(() -> {
            carDao.deleteCar(model);
        });
    }
}
