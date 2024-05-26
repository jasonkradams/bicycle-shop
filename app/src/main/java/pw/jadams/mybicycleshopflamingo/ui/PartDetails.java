package pw.jadams.mybicycleshopflamingo.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import pw.jadams.mybicycleshopflamingo.R;
import pw.jadams.mybicycleshopflamingo.db.Repository;
import pw.jadams.mybicycleshopflamingo.entities.Part;

public class PartDetails extends AppCompatActivity {

    String name;
    double price;
    int partId;
    int productId;
    EditText editName;
    EditText editPrice;
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_part_details);

        editName = findViewById(R.id.editPartTitle);
        editPrice = findViewById(R.id.editPartPrice);
        partId = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        price = getIntent().getDoubleExtra("price", 0);
        productId = getIntent().getIntExtra("productId", 0);
        editName.setText(name);
        editPrice.setText(String.valueOf(price));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_part_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save_part) {
            repository = new Repository(getApplication());

            productId = getIntent().getIntExtra("productId", 0);
            if (partId == -1) {
                if (repository.getmAllParts().isEmpty()) partId = 1;
                else
                    partId = repository.getmAllParts().get(repository.getmAllParts().size() - 1).getPartId() + 1;
                Part part = new Part(partId, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()), productId);
                repository.insert(part);
            } else {
                Part part = new Part(partId, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()), productId);
                repository.update(part);
            }
            this.finish();
            return true;
        }

        if (item.getItemId() == R.id.delete_part) {
            repository = new Repository(getApplication());
            Part part = new Part(partId, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()), productId);
            repository.delete(part);
            this.finish();
            return true;
        }

        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return true;
    }

}