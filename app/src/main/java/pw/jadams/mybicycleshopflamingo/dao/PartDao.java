package pw.jadams.mybicycleshopflamingo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import pw.jadams.mybicycleshopflamingo.entities.Part;

@Dao
public interface PartDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Part part);

    @Update
    void update(Part part);

    @Delete
    void delete(Part part);

    @Query("SELECT * FROM parts ORDER BY partID ASC;")
    List<Part> getAllParts();

    @Query("SELECT * FROM parts WHERE productId=:prod ORDER BY partID ASC;")
    List<Part> getAssociatedParts(int prod);

}
