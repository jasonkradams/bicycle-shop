package pw.jadams.mybicycleshopflamingo.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import pw.jadams.mybicycleshopflamingo.dao.PartDao;
import pw.jadams.mybicycleshopflamingo.dao.ProductDao;
import pw.jadams.mybicycleshopflamingo.entities.Part;
import pw.jadams.mybicycleshopflamingo.entities.Product;

@Database(entities = {Product.class, Part.class}, version = 1, exportSchema = false)
public abstract class BicycleDatabaseBuilder extends RoomDatabase {
    public abstract ProductDao productDao();

    public abstract PartDao partDao();

    private static volatile BicycleDatabaseBuilder INSTANCE;

    static BicycleDatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BicycleDatabaseBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    BicycleDatabaseBuilder.class, "MyBicycleDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
