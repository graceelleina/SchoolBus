package com.example.schoolbus;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ReservationHistoryPage extends AppCompatActivity {
    DatabaseHelper db;
    TextView reservation_id, bus_name, reserve_date, bus_plate;

    ListView historyList;
    ArrayList<String> listItem;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_history_page);
        db = new DatabaseHelper(this);

        listItem = new ArrayList<>();
        reservation_id = findViewById(R.id.historyReservationID);
        bus_name = findViewById(R.id.historyName);
        reserve_date = findViewById(R.id.historyDate);
        bus_plate = findViewById(R.id.historyPlate);
        historyList = findViewById(R.id.lvHistory);

        viewData();

        historyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = historyList.getItemAtPosition(i).toString();
                Toast.makeText(ReservationHistoryPage.this, "" + text, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void viewData() {
        String idUser = getIntent().getStringExtra("userID");
        Cursor cursor = db.viewData(idUser);

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                String busID = cursor.getString(1);

                String busesName = db.getBusname(busID);
                String busesPlate = db.getBusPlate(busID);
                String fromGetBusType = db.getBusType(busID);

                listItem.add("Reservation ID: " + cursor.getString(0) + "\n" + // reservation_id
                        "Bus Type: " + fromGetBusType.toString() + "\n" +
                        "Bus Driver Name: " + busesName.toString() + "\n" + // nembak string name
                        "Bus Plate: " + busesPlate.toString() + "\n" +
                        "Reservation Date: " + cursor.getString(3)); // tanggal
            }
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItem);
            historyList.setAdapter(adapter);
        }
    }
}

