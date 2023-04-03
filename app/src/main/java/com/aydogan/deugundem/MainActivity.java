package com.aydogan.deugundem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.aydogan.deugundem.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {


    private static final Common  common = new Common();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void openRegisterPage(View view){

//        common.openActivity(MainActivity.this,RegisterActivity.class);

        Intent i = new Intent(MainActivity.this,RegisterActivity.class);
        startActivity(i);

    }

    public void openLoginPage(View view){

        Intent i = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(i);

    }


}