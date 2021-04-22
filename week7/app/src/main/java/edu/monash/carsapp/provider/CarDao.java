package edu.monash.carsapp.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CarDao {

    @Query("select * from cars")
    LiveData<List<Car>> getAllCars();

    @Query("select * from cars where carId=:id")
    List<Car> getCar(int id);

    @Insert
    void addCar(Car car);

    @Query("delete from cars where model=:carModel")
    void deleteCar(String carModel);

    @Query("delete from cars")
    void deleteAllCars();
}
