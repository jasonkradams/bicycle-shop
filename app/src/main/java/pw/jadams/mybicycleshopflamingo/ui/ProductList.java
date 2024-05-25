package pw.jadams.mybicycleshopflamingo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import pw.jadams.mybicycleshopflamingo.R;
import pw.jadams.mybicycleshopflamingo.db.Repository;
import pw.jadams.mybicycleshopflamingo.entities.Part;
import pw.jadams.mybicycleshopflamingo.entities.Product;

public class ProductList extends AppCompatActivity {
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_list);


        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButtonProductList);
        floatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProductList.this, ProductDetails.class);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_product_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sample) {
            repository = new Repository(getApplication());
            //Toast.makeText(ProductList.this, "Sample Text String", Toast.LENGTH_SHORT).show();
            Product product = new Product(0, "bicycle", 1000.00);
            repository.insert(product);
            product = new Product(1, "mountain bike", 4000.00);
            repository.insert(product);
            Part part = new Part(0, "frame", 300.00, 1);
            repository.insert(part);
            part = new Part(0, "spokes", 150.00, 1);
            repository.insert(part);
            return true;
        }

        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return true;
    }
}