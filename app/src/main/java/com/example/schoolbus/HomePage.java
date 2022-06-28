package com.example.schoolbus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.schoolbus.adapters.BusAdapter;
import com.example.schoolbus.models.Bus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

public class HomePage extends AppCompatActivity {

    DatabaseHelper db;
    private Vector<Bus> listBus;
    private RecyclerView rvMain;
    private BusAdapter adapter;
    TextView Username;

    Button history, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        db = new DatabaseHelper(this);

        Username = findViewById(R.id.homeUsername);

        String usernames = getIntent().getStringExtra("username");
        String email = getIntent().getStringExtra("email");
        String phone = getIntent().getStringExtra("phone");

        String userId = db.getReadUserid(usernames);

        Username.setText(usernames);

        init();

        String url = "https://bit.ly/JSONSchoolBus";

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest req = new JsonObjectRequest(url, response -> {
            try {
                JSONArray furnitures = response.getJSONArray("schoolbus");
                for(int i=0; i< furnitures.length(); i++){
                    JSONObject emp = furnitures.getJSONObject(i);
                    String name = emp.getString("supir_name");
                    String type = emp.getString("jenis_car");
                    String plate = emp.getString("plat");
                    String pict = emp.getString("image");
                    String usernamePass = usernames;

                    Boolean insertProduk = db.insertBus(name, type, plate, pict);
                    Bus newFurniture = new Bus(pict, name, type, plate, usernamePass);
                    listBus.add(newFurniture);
                }
                adapter = new BusAdapter(this, listBus);
                rvMain.setAdapter(adapter);
                rvMain.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Log.wtf("error", error.toString());
        });

        queue.add(req);


        history = (Button) findViewById(R.id.btnHistory);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent historyIntent = new Intent();
                historyIntent.setClass(getApplicationContext(), ReservationHistoryPage.class);
                historyIntent.putExtra("userID", userId);
                startActivity(historyIntent);
            }
        });

        profile = (Button) findViewById(R.id.btnProfile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profileIntent = new Intent();
                profileIntent.setClass(getApplicationContext(),ProfilePage.class);
                profileIntent.putExtra("username", usernames);
                profileIntent.putExtra("email", email);
                profileIntent.putExtra("phone", phone);
                startActivity(profileIntent);
            }
        });

    }

    private void init(){
        listBus = new Vector<>();
        rvMain = findViewById(R.id.rvMain);
    }

}