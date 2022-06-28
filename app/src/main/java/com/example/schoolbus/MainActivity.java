/*

Kelompok 5
2440043452	Alvaro Cleavant
2440007711	Anthony Wijaya Saputra
2440018766	Grace Elleina Wawondhatu

*/

package com.example.schoolbus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;
    Button login, register;
    EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        email = (EditText) findViewById(R.id.edtText_email);
        password = (EditText) findViewById(R.id.edtText_password);
        login = (Button) findViewById(R.id.btn_login);
        register = (Button) findViewById(R.id.btn_register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(MainActivity.this, RegisterPage.class);
                startActivity(registerIntent);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strEmail = email.getText().toString();
                String strPassword = password.getText().toString();

                Boolean entry = db.checkLogin(strEmail, strPassword);

                if(entry == true) {
                        String username = db.getReadUsername(strEmail);
                        String phone = db.getReadPhone(strEmail);
                        Toast.makeText(getApplicationContext(), "Login Successful!", Toast.LENGTH_SHORT).show();
                        Intent mainIntent = new Intent(MainActivity.this, HomePage.class);
                        mainIntent.putExtra("username", username);
                        mainIntent.putExtra("email", strEmail);
                        mainIntent.putExtra("phone", phone);
                        Log.wtf("testdata", username);
                        startActivity(mainIntent);
                        finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Login Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onBackPressed() {

        startActivity(new Intent(MainActivity.this,MainActivity.class));

    }
}