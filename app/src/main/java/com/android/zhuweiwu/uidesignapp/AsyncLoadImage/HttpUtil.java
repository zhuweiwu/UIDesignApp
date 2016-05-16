package com.android.zhuweiwu.uidesignapp.AsyncLoadImage;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by zhuweiwu on 5/15/2016.
 */
public class HttpUtil {

    public static void sendHttpRequest(final String address) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);


                    InputStream in = connection.getInputStream();


                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));


                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                } catch (Exception e) {

                } finally {

                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

}
