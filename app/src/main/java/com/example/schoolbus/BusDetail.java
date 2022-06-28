package com.example.schoolbus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class BusDetail extends AppCompatActivity {

    DatabaseHelper db;
    TextView nama, tipe, plat, tanggal;
    Button reserve;
    ImageView gambar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_detail);

        db = new DatabaseHelper(this);

        Calendar calendar = Calendar.getInstance();

        //textview
        nama = findViewById(R.id.busDetailName);
        tipe = findViewById(R.id.busDetailType);
        plat = findViewById(R.id.busDetailPlate);
        gambar = findViewById(R.id.busDetailImg);
        tanggal = findViewById(R.id.busDetailDate);

        reserve = findViewById(R.id.busButton);


        String name = getIntent().getStringExtra("name");
        String type = getIntent().getStringExtra("type");
        String plate = getIntent().getStringExtra("plate");
        String usernames = getIntent().getStringExtra("username");
        Bundle extras = getIntent().getExtras();
        Bitmap bmp = (Bitmap) extras.getParcelable("imagebitmap");

        nama.setText(name);
        tipe.setText(type);
        plat.setText(plate);
        gambar.setImageBitmap(bmp);

        String busId = db.getBusid(name);
        String userId = db.getReadUserid(usernames);
        String phone = db.getReadPhonebyUsername(usernames);

        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        if(permission == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.SEND_SMS}, 123);
        }

        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strDate = tanggal.getText().toString();
                String strUsername = usernames.toString();
                String message = "Reservation success";

                if(strDate.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Date cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    boolean detailInsert = db.insertDetail(userId, strDate, busId);

                    SmsManager manager = SmsManager.getDefault();;
                    manager.sendTextMessage(phone, null, message, null, null);

                    Intent intent = new Intent(BusDetail.this, ReservationHistoryPage.class);
                    intent.putExtra("userID", userId);
                    startActivity(intent);
                }
            }
        });
    }
}