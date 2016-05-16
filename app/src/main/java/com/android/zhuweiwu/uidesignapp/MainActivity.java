package com.android.zhuweiwu.uidesignapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * Created by zhuweiwu on 4/30/2016.
 */
public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


        Button myBtn1 = (Button) findViewById(R.id.id_slide_menu);

        myBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SlideMenuActivity.class);

                startActivity(intent);
            }
        });

        Button myBtn2 = (Button) findViewById(R.id.id_bidir_slide_menu);

        myBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BidirSlideMenuActivity.class);

                startActivity(intent);
            }
        });



        Button myBtn3 = (Button) findViewById(R.id.id_volley);

        myBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, VolleyActivity.class);

                startActivity(intent);
            }
        });


    }
}
