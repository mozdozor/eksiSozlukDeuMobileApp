package com.aydogan.deugundem;

import android.app.Activity;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;


public class Common extends AppCompatActivity {


    public void openActivity(Activity firstActivity,Class targetActivity){

        Intent i = new Intent(firstActivity,targetActivity);
        startActivity(i);

    }



}
