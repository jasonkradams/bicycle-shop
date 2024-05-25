package pw.jadams.mybicycleshopflamingo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import pw.jadams.mybicycleshopflamingo.entities.Product;

@Dao
public interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Product product);

    @Update
    void update(Product product);

    @Delete
    void delete(Product product);

    @Query("SELECT * FROM products ORDER BY productID ASC;")
    List<Product> getAllProducts();

    @Query("SELECT * FROM products WHERE productId=:prod ORDER BY productID ASC;")
    List<Product> getAssociatedProducts(int prod);

}