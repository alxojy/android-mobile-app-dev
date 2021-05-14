package edu.monash.carsapp.provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CarViewModel extends AndroidViewModel {
    private final CarRepository carRepository;
    private final LiveData<List<Car>> allCars;

    public CarViewModel(@NonNull Application application) {
        super(application);
        carRepository = new CarRepository(application);
        allCars = carRepository.getAllCars();
    }

    public LiveData<List<Car>> getAllCars() {
        return allCars;
    }

    public void insert(Car car) {
        carRepository.insert(car);
    }

    public void deleteAll() {
        carRepository.deleteAll();
    }

    public void deleteCar(String model) {
        carRepository.deleteCar(model);
    }
}
