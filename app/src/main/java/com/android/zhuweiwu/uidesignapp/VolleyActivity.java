package com.android.zhuweiwu.uidesignapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.zhuweiwu.uidesignapp.Volley.ImageCacheManager;

/**
 * Created by zhuweiwu on 5/15/2016.
 */
public class VolleyActivity extends Activity {

    private ImageView imageView;
    private Button loadBtn;
    private EditText imageUrlTxt;
    private String url = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_volleymain);


        imageView = (ImageView) findViewById(R.id.id_image);
        loadBtn = (Button) findViewById(R.id.id_load_image);
        imageUrlTxt = (EditText) findViewById(R.id.id_image_url);

        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!imageUrlTxt.getText().toString().trim().isEmpty())
                {
                    url = imageUrlTxt.getText().toString().trim();

                    CacheImage();

                }
            }
        });


    }

    public void CacheImage(){

        Bitmap defaultImage= BitmapFactory.decodeResource(getResources(), R.drawable.loading);

        Bitmap errorImage=BitmapFactory.decodeResource(getResources(), R.drawable.load_error);

        ImageCacheManager.loadImage(this, url, imageView, defaultImage, errorImage);

    }

}
