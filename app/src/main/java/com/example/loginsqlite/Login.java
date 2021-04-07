package com.example.loginsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class Login extends AppCompatActivity {
private EditText edit_user, edit_password;
private String user,password;
dbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edit_user = (EditText) findViewById(R.id.edit_user);
        edit_password = (EditText) findViewById(R.id.edit_pass);
        dbHelper = new dbHelper(this);

    }

    public void register(View view) {
        Intent intent = new Intent(Login.this,Register.class);
        startActivity(intent);
    }


    public void login(View view) {
        user = edit_user.getText().toString();
        password= edit_password.getText().toString();
        try {
            if (user.length()>0 && password.length()>0){
                dbHelper = new dbHelper(this);
                SQLiteDatabase db = dbHelper.getReadableDatabase();

                if(dbHelper.Login(user,password)){
                        Message.message(getApplicationContext(),"Successfully Logged In");
                        Intent intent = new Intent(Login.this, Action.class);
                        startActivity(intent);
                }else {
                    Message.message(getApplicationContext(),"Invalid Username or Password");
                } dbHelper.close();
            }

        }catch (Exception e){
            Message.message(getApplicationContext(),"Error"+e);
        }
    }
}
