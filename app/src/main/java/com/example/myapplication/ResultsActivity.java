package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends AppCompatActivity {
    private RecyclerView recyclerViewModules;
    private TextView tvSemesterAverage;
    private DBHelper dbHelper;
    private List<Module> moduleList;
    private ModuleAdapter adapter;
    private Button btnReturn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);


        recyclerViewModules = findViewById(R.id.recyclerViewModules);
        tvSemesterAverage = findViewById(R.id.tvSemesterAverage);
        dbHelper = new DBHelper(this);
        moduleList = new ArrayList<>();
        btnReturn = findViewById(R.id.btnReturn);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultsActivity.this,
                        MainActivity.class);
                startActivity(intent);
            }});
        loadModules();
        setupRecyclerView();
        displaySemesterAverage();
    }



    private void loadModules() {
        moduleList.clear();
        Cursor cursor = dbHelper.getAllModules();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Module Added", Toast.LENGTH_SHORT).show();
            cursor.close();
            return;
        }

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            double coefficient = cursor.getDouble(2);
            double test = cursor.getDouble(3);
            double practical = cursor.getDouble(4);
            double project = cursor.getDouble(5);
            double testPercentage = cursor.getDouble(6);
            double practicalPercentage = cursor.getDouble(7);
            double projectPercentage = cursor.getDouble(8);
            double average = cursor.getDouble(9);

            moduleList.add(new Module(id, name, coefficient, test, practical, project, testPercentage, practicalPercentage, projectPercentage, average));
        }
        cursor.close();
    }

    private void setupRecyclerView() {
        adapter = new ModuleAdapter(moduleList, new ModuleAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                int moduleId = moduleList.get(position).getId();
                dbHelper.deleteModule(moduleId);
                moduleList.remove(position);
                adapter.notifyItemRemoved(position);
                displaySemesterAverage();
                Toast.makeText(ResultsActivity.this, "Delete Successful", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUpdateClick(int position) {
                Module module = moduleList.get(position);
                showUpdateDialog(module);
            }
        });
        recyclerViewModules.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewModules.setAdapter(adapter);
    }


    private void displaySemesterAverage() {
        double totalWeightedAverage = 0;
        double totalCoefficients = 0;

        for (Module module : moduleList) {
            totalWeightedAverage += module.getAverage() * module.getCoefficient();
            totalCoefficients += module.getCoefficient();
        }

        if (totalCoefficients > 0) {
            double semesterAverage = totalWeightedAverage / totalCoefficients;
            tvSemesterAverage.setText("Sesmester Avg :" + String.format("%.2f", semesterAverage));
        } else {
            tvSemesterAverage.setText("Sesmester Avg : 0.00");
        }
    }


    private void showUpdateDialog(Module module) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update : " + module.getName() + "Module");


        View view = LayoutInflater.from(this).inflate(R.layout.dialog_update_module, null);
        EditText etTest = view.findViewById(R.id.etTest);
        EditText etPractical = view.findViewById(R.id.etPractical);
        EditText etProject = view.findViewById(R.id.etProject);

        etTest.setText(String.valueOf(module.getTest()));
        etPractical.setText(String.valueOf(module.getPractical()));
        etProject.setText(String.valueOf(module.getProject()));

        builder.setView(view);


        builder.setPositiveButton("Update : ", (dialog, which) -> {
            try {
                double test = Double.parseDouble(etTest.getText().toString());
                double practical = Double.parseDouble(etPractical.getText().toString());
                double project = Double.parseDouble(etProject.getText().toString());

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

                dbHelper.updateModule(module.getId(),test, practical, project);


//                loadModules();
                adapter.notifyDataSetChanged();
                displaySemesterAverage();
                Toast.makeText(ResultsActivity.this, "Update Successful", Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                Toast.makeText(ResultsActivity.this, "Please enter a Real num", Toast.LENGTH_SHORT).show();
            }
        });


        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }
}