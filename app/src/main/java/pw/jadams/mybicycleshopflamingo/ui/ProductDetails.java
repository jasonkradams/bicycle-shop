package pw.jadams.mybicycleshopflamingo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import pw.jadams.mybicycleshopflamingo.R;
import pw.jadams.mybicycleshopflamingo.db.Repository;
import pw.jadams.mybicycleshopflamingo.entities.Part;
import pw.jadams.mybicycleshopflamingo.entities.Product;

public class ProductDetails extends AppCompatActivity {

    String name;
    double price;
    int productId;
    EditText editName;
    EditText editPrice;
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_details);

        editName = findViewById(R.id.editProductTitle);
        editPrice = findViewById(R.id.editProductPrice);
        productId = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        price = getIntent().getDoubleExtra("price", 0);
        editName.setText(name);
        editPrice.setText(String.valueOf(price));


        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButtonProductDetails);
        floatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProductDetails.this, PartDetails.class);
            intent.putExtra("productId", productId);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        RecyclerView recyclerView = findViewById(R.id.recyclerViewPartList);
        repository = new Repository(getApplication());
        final PartAdapter partAdapter = new PartAdapter(this);
        recyclerView.setAdapter(partAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Part> filteredParts = repository.getAssociatedParts(productId);
        partAdapter.setParts(filteredParts);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_product_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save_product) {
            repository = new Repository(getApplication());

            if (productId == -1) {
                if (repository.getmAllProducts().isEmpty()) productId = 1;
                else productId = repository.getmAllProducts().get(repository.getmAllProducts().size() - 1).getProductId() + 1;
                Product product = new Product(productId, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()));
                repository.insert(product);
            } else {
                Product product = new Product(productId, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()));
                repository.update(product);
            }
            this.finish();
            return true;
        }

        if (item.getItemId() == R.id.delete_product) {
            repository = new Repository(getApplication());
            Product product = new Product(productId, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()));
            repository.delete(product);
            this.finish();
            return true;
        }

        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.recyclerViewPartList);
        repository = new Repository(getApplication());
        final PartAdapter partAdapter = new PartAdapter(this);
        recyclerView.setAdapter(partAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Part> filteredParts = repository.getAssociatedParts(productId);
        partAdapter.setParts(filteredParts);
    }
}