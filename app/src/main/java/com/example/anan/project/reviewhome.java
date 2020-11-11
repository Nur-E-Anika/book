package com.example.anan.project;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class reviewhome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewhome);



        String url = "https://project-22aa1.firebaseio.com/review.json";


        final ProgressDialog pd = new ProgressDialog(reviewhome.this);
        pd.setMessage("Loading...");
        pd.show();

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {

                try {
                    JSONObject obj = new JSONObject(s);

                    String bookUrl = obj.getJSONObject(UserDetails.BookName).getString(UserDetails.bookRef);


                    WebView webview=(WebView)findViewById(R.id.webView);

                    webview.getSettings().setJavaScriptEnabled(true);

                    webview.loadUrl(bookUrl);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                pd.dismiss();
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
                pd.dismiss();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(reviewhome.this);
        rQueue.add(request);

    }
}
