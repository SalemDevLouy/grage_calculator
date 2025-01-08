package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddModuleActivity extends AppCompatActivity {
    private EditText etName, etCoefficient, etTestPercentage,
            etTPPercentage, etTDPercentage, etTest, etTP, etTD;
    private Button btnSave , btnReturn;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_module);

        etName = findViewById(R.id.etName);
        etCoefficient = findViewById(R.id.etCoefficient);
        etTestPercentage = findViewById(R.id.etTestPercentage);
        etTPPercentage = findViewById(R.id.etPracticalPercentage);
        etTDPercentage = findViewById(R.id.etProjectPercentage);
        etTest = findViewById(R.id.etTest);
        etTP = findViewById(R.id.etPractical);
        etTD = findViewById(R.id.etProject);
        btnSave = findViewById(R.id.btnSave);
        btnReturn = findViewById(R.id.btnReturn);
        dbHelper = new DBHelper(this);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddModuleActivity.this,
                        MainActivity.class);
                startActivity(intent);
            }});

        btnSave.setOnClickListener(v -> saveModule());
    }

    private void saveModule() {
        String name = etName.getText().toString().trim();
        String coefficientStr = etCoefficient.getText().toString().trim();
        String testPercentageStr = etTestPercentage.getText().toString().trim();
        String practicalPercentageStr = etTPPercentage.getText().toString().trim();
        String projectPercentageStr = etTDPercentage.getText().toString().trim();
        String testStr = etTest.getText().toString().trim();
        String practicalStr = etTP.getText().toString().trim();
        String projectStr = etTD.getText().toString().trim();


        if (name.isEmpty() || coefficientStr.isEmpty() || testPercentageStr.isEmpty() ||
                practicalPercentageStr.isEmpty() || projectPercentageStr.isEmpty() ||
                testStr.isEmpty() || practicalStr.isEmpty() || projectStr.isEmpty()) {
            Toast.makeText(this, "Please full all field", Toast.LENGTH_SHORT).show();
            return;
        }

        double coefficient = Double.parseDouble(coefficientStr);
        double testPercentage = Double.parseDouble(testPercentageStr);
        double practicalPercentage = Double.parseDouble(practicalPercentageStr);
        double projectPercentage = Double.parseDouble(projectPercentageStr);
        double test = Double.parseDouble(testStr);
        double practical = Double.parseDouble(practicalStr);
        double project = Double.parseDouble(projectStr);



        if (test < 0 || test > 20) {
            Toast.makeText(this, "Exament Point must be 0 to 20", Toast.LENGTH_SHORT).show();
            return;
        }
        if (practical < 0 || practical > 20) {
            Toast.makeText(this, "TP Point must be 0 to 20", Toast.LENGTH_SHORT).show();
            return;
        }
        if (project < 0 || project > 20) {
            Toast.makeText(this, "TD Point must be 0 to 20", Toast.LENGTH_SHORT).show();
            return;
        }


        double totalPercentage = testPercentage + practicalPercentage + projectPercentage;
        if (totalPercentage != 100) {
            Toast.makeText(this, "The sum of the percentages must equal 100%.", Toast.LENGTH_SHORT).show();
            return;
        }


        double average = (test * (testPercentage / 100)) +
                (practical * (practicalPercentage / 100)) +
                (project * (projectPercentage / 100));

        boolean isInserted = dbHelper.addModule(name, coefficient, test, practical, project, testPercentage, practicalPercentage, projectPercentage, average);
        if (isInserted) {
            Toast.makeText(this, " Added Successfully.", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Something went wrong Or module already exist", Toast.LENGTH_SHORT).show();
        }
    }
}