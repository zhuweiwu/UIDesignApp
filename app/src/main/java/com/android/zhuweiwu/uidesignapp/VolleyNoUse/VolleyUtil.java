package com.android.zhuweiwu.uidesignapp.VolleyNoUse;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.zhuweiwu.uidesignapp.Volley.VolleyController;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhuweiwu on 5/15/2016.
 */
public class VolleyUtil {

    public interface volleyListener{
        void onResponse(JSONObject response);
        void onErrorResponse(String message);
    }


    /**
     *
     * @param context
     * @param url
     * @param params
     * 用来保存post参数
     */
    public static void doPost(Context context, String url,HashMap<String, Object> params,final volleyListener listener) {
        // 优先级有LOW，NORMAL,HIGH,IMMEDIATE
        // 设置请求的优先级别通过覆写getPrioriity()方法
        final Request.Priority priority = Request.Priority.HIGH;

        JsonObjectRequest req = new JsonObjectRequest(url, new JSONObject(
                params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                // 正确响应时回调此函数
                listener.onResponse(response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                listener.onErrorResponse(error.getMessage());

            }
        }) {
            // 设置请求级别
            @Override
            public Priority getPriority() {
                return priority;
            }

            // 设置请求头
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // TODO Auto-generated method stub
                return super.getHeaders();
            }
        };
        // 第一个代表超时时间：即超过30S认为超时，第三个参数代表最大重试次数，这里设置为1.0f代表如果超时，则不重试
        req.setRetryPolicy(new DefaultRetryPolicy(1000*30, 1, 1.0f));
        // 可以通过setTag方法为每一个Request添加tag
        req.setTag("My Tag");
        // 也可以在我们实现的添加进RequestQueue的时候设置
        VolleyController.getInstance(context).addToRequestQueue(req, "My Tag");

        // 取消Request
        // VolleyController.getInstance(context).getRequestQueue().cancelAll("My Tag");
        // 或者我们前面实现的方法
        // VolleyController.getInstance(context).cancelPendingRequests("My Tag");
    }


    public interface MyImageListener{
        void setImageBitmap(Bitmap bitmap);
    }

    public static void loadImage(Context context,String url,final MyImageListener listener){
        ImageLoader imageLoader=VolleyController.getInstance(context).getImageLoader();

        imageLoader.get(url,new ImageLoader.ImageListener(){
            @Override
            public void onResponse(ImageLoader.ImageContainer response,boolean arg)  {
                if(response.getBitmap()!=null){
                    //设置imageView
                    //    imageView.setImageBitmap(response.getBitmap());
                    listener.setImageBitmap(response.getBitmap());
                }
            }
            @Override
            public void onErrorResponse(VolleyError error){
                throw new IllegalArgumentException("Image Error"+error.getMessage());
            }
        });
    }

}
