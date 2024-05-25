package pw.jadams.mybicycleshopflamingo.db;

import android.app.Application;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pw.jadams.mybicycleshopflamingo.dao.PartDao;
import pw.jadams.mybicycleshopflamingo.dao.ProductDao;
import pw.jadams.mybicycleshopflamingo.entities.Part;
import pw.jadams.mybicycleshopflamingo.entities.Product;

public class Repository {
    private PartDao mPartDao;
    private ProductDao mProductDao;

    private List<Product> mAllProducts;
    private List<Part> mAllParts;

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        BicycleDatabaseBuilder db = BicycleDatabaseBuilder.getDatabase(application);
        mPartDao = db.partDao();
        mProductDao = db.productDao();
    }

    // Product Repository CRUD
    public List<Product> getmAllProducts() {
        databaseExecutor.execute(() -> {
            mAllProducts = mProductDao.getAllProducts();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllProducts;
    }

    public void insert(Product product) {
        databaseExecutor.execute(() -> {
            mProductDao.insert(product);
        });
    }

    public void update(Product product) {
        databaseExecutor.execute(() -> {
            mProductDao.update(product);
        });
    }

    public void delete(Product product) {
        databaseExecutor.execute(() -> {
            mProductDao.delete(product);
        });
    }

    // Part Repository CRUD
    public List<Part> getmAllParts() {
        databaseExecutor.execute(() -> {
            mAllParts = mPartDao.getAllParts();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllParts;
    }

    public List<Part> getAssociatedParts(int productId) {
        databaseExecutor.execute(() -> {
            mAllParts = mPartDao.getAssociatedParts(productId);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllParts;
    }


    public void insert(Part part) {
        databaseExecutor.execute(() -> {
            mPartDao.insert(part);
        });
    }

    public void update(Part part) {
        databaseExecutor.execute(() -> {
            mPartDao.update(part);
        });
    }

    public void delete(Part part) {
        databaseExecutor.execute(() -> {
            mPartDao.delete(part);
        });
    }
}
