package com.example.doglife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnLogin, btnRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin= findViewById(R.id.btnLogin);
        btnRegistro = findViewById(R.id.btnRegistro);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarRegistro();
              //startActivity(new Intent(MainActivity.this, RegisterActivity.class));

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarLogin();
            }
        });
    }

    private void cambiarRegistro(){
        Intent cambiar = new Intent(this, RegisterActivity.class);
        startActivity(cambiar);
    }

    private void cambiarLogin(){
        Intent cambiar = new Intent(this, LoginActivity.class);
        startActivity(cambiar);
    }

}