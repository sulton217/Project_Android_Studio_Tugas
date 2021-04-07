package com.example.loginsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class Action extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);


    }

    public void insert(View view) {
        Intent intent = new Intent(Action.this,Insert.class);
        startActivity(intent);
    }

    public void update(View view) {
        Intent intent = new Intent(Action.this,Update.class);
        startActivity(intent);
    }

    public void delete(View view) {
        Intent intent = new Intent(Action.this,Delete.class);
        startActivity(intent);
    }

    public void logout(View view) {
        Intent intent = new Intent(Action.this,Login.class);
        startActivity(intent);
    }


}
