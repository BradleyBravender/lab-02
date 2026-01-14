package com.example.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // These variables are declared outside onCreate so that the entire program can access them
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    int selectedCity = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // cityList is the listView of all the cities
        cityList = findViewById(R.id.city_list);
        String[] cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};

        // Convert String[] (which is static) to ArrayList (which is dynamic)
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        // ArrayAdapter bridges between the dataList and the listView
        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        // Tells my listView cityList to use the cityAdapter as its source
        cityList.setAdapter(cityAdapter);

        // Set up a click listener for the cities in the cityList listView
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedCity = position;

            for (int i = 0; i < cityList.getChildCount(); i++) {
                // Any time a city is clicked, first reset the background color of all cities
                cityList.getChildAt(i).setBackgroundColor(0x00000000);
            }
            // Next, change the clicked city's background color
            view.setBackgroundColor(0xFFB0B0B0);
        });

        // Set up a click listener to delete the selected city
        Button deleteButton = findViewById(R.id.delete_city);

        deleteButton.setOnClickListener(v -> {
            if (selectedCity != -1) {
                dataList.remove(selectedCity);
                cityAdapter.notifyDataSetChanged();
                selectedCity = -1;
            }
        });

        Button addCityButton = findViewById(R.id.add_city);
        EditText cityInput = findViewById(R.id.cityInput);
        LinearLayout addCityLayout = findViewById(R.id.addCityLayout);
        Button confirmButton = findViewById(R.id.confirmButton);

        // Add a click listener for when the addCity button is clicked
        addCityButton.setOnClickListener(v -> {
            // Make the input box visible
            addCityLayout.setVisibility(View.VISIBLE);
            cityInput.requestFocus();
        });

        confirmButton.setOnClickListener(v -> {
            String newCity = cityInput.getText().toString().trim();
            if (!newCity.isEmpty()) {
                dataList.add(newCity);
                // Refresh the listView
                cityAdapter.notifyDataSetChanged();
                cityInput.setText("");
                addCityLayout.setVisibility(View.GONE);
            } else {
                Toast.makeText(this, "Enter a city name", Toast.LENGTH_SHORT).show();
            }
        });

    }
}