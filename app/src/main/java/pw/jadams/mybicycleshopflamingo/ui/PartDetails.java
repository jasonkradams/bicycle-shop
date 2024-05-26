package pw.jadams.mybicycleshopflamingo.ui;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import pw.jadams.mybicycleshopflamingo.R;
import pw.jadams.mybicycleshopflamingo.db.Repository;
import pw.jadams.mybicycleshopflamingo.entities.Part;
import pw.jadams.mybicycleshopflamingo.entities.Product;

public class PartDetails extends AppCompatActivity {
    String name;
    Double price;
    int partId;
    int productId;
    EditText editName;
    EditText editPrice;
    EditText editNote;
    TextView editDate;
    Repository repository;
    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    final String myDateFormat = "MM/dd/yy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part_details);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        repository = new Repository(getApplication());
        name = getIntent().getStringExtra("name");
        editName = findViewById(R.id.editPartTitle);
        editName.setText(name);
        price = getIntent().getDoubleExtra("price", -1.0);
        editPrice = findViewById(R.id.editPartPrice);
        editPrice.setText(Double.toString(price));
        partId = getIntent().getIntExtra("id", -1);
        productId = getIntent().getIntExtra("productId", -1);
        editNote = findViewById(R.id.editPartNote);
        editDate = findViewById(R.id.editPartDate);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myDateFormat, Locale.US);

        ArrayList<Product> productArrayList = new ArrayList<>(repository.getmAllProducts());
        ArrayList<Integer> productIdList = new ArrayList<>();
        for (Product product : productArrayList) {
            productIdList.add(product.getProductId());
        }
        ArrayAdapter<Integer> productIdAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, productIdList);
        Spinner spinner = findViewById(R.id.partSpinner);
        spinner.setAdapter(productIdAdapter);

        startDate = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendarStart.set(Calendar.YEAR, year);
            myCalendarStart.set(Calendar.MONTH, monthOfYear);
            myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            PartDetails.this.updateLabelStart();
        };

        editDate.setOnClickListener(view -> {

            //get value from other screen,but I'm going to hard code it right now
            String currentDate = editDate.getText().toString();

            if (currentDate.isEmpty())
                currentDate = simpleDateFormat.format(Calendar.getInstance().getTime());
            try {
                myCalendarStart.setTime(simpleDateFormat.parse(currentDate));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            new DatePickerDialog(PartDetails.this, startDate, myCalendarStart
                    .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                    myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();

        });

    }

    private void updateLabelStart() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myDateFormat, Locale.US);

        editDate.setText(simpleDateFormat.format(myCalendarStart.getTime()));
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_part_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }

        if (item.getItemId() == R.id.save_part) {
            Part part;
            if (partId == -1) {
                if (repository.getmAllParts().isEmpty())
                    partId = 1;
                else
                    partId = repository.getmAllParts().get(repository.getmAllParts().size() - 1).getPartId() + 1;
                part = new Part(partId, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()), productId);
                repository.insert(part);
                this.finish();
            } else {
                part = new Part(partId, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()), productId);
                repository.update(part);
                this.finish();
            }
            return true;
        }

        if (item.getItemId() == R.id.share_part) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, editNote.getText().toString());
            sendIntent.putExtra(Intent.EXTRA_TITLE, "Message Title");
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return true;
        }

        if (item.getItemId() == R.id.notify_part) {
            String dateFromScreen = editDate.getText().toString();
            String myDateFormat = "MM/dd/yy"; //In which you need put here
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myDateFormat, Locale.US);
            Date myDate;
            try {
                myDate = simpleDateFormat.parse(dateFromScreen);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            try {
                assert myDate != null;
                long trigger = myDate.getTime();
                Intent intent = new Intent(PartDetails.this, MyReceiver.class);
                intent.putExtra("key", "message I want to see");
                PendingIntent sender = PendingIntent.getBroadcast(PartDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
