package com.amtechnology.drugdosage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MedicineRecord extends AppCompatActivity {
    TextView tvtitle,remindertimes,time,dosage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_record);
        setTitle("Details");

        tvtitle = findViewById(R.id.tvtitle);
        remindertimes = findViewById(R.id.remindertimes);
        time = findViewById(R.id.datetime);
        dosage = findViewById(R.id.dosage);
        Bundle bundle = getIntent().getExtras();

        tvtitle.setText(bundle.getString("Medicine Name"));
        remindertimes.setText(bundle.getString("ReminderTimes"));
        time.setText(bundle.getString("Time"));
        dosage.setText(bundle.getString("Dosage"));

    }

    public void setupdeletemedicine(View view) {
        SQLDatabase sqlDatabase = new SQLDatabase(this, "DrugReminder", null, 1);
        Bundle bundle = getIntent().getExtras();
        sqlDatabase.deletevalue(bundle.getString("id"));
        Toast.makeText(this,"Deletion Success",Toast.LENGTH_SHORT).show();
        finish();
    }
}
