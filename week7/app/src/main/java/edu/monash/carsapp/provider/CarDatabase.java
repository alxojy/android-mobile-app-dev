package edu.monash.carsapp.provider;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Car.class}, version = 1)
public abstract class CarDatabase extends RoomDatabase {

    public static final String CAR_DB_NAME = "cars_database";

    public abstract CarDao carDao();

    private static volatile CarDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static CarDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CarDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CarDatabase.class, CAR_DB_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
