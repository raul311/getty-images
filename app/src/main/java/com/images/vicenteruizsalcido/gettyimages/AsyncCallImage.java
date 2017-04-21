package com.images.vicenteruizsalcido.gettyimages;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.images.vicenteruizsalcido.gettyimages.response.PayLoad;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by vicente.ruiz.salcido on 4/1/2017.
 */

public class AsyncCallImage extends AsyncTask<String, Void, Void> {

    private static final String GETTY_KEY = "n5ttb3egk6592kv4wde4yhtn";
    private static final String GETTY_URL = "https://api.gettyimages.com/v3/search/images/creative?phrase=";
    private static final String API_KEY = "Api-Key";

    private MainFragment.FragmentCallback fragmentCallback;
    private PayLoad payLoad;

    public AsyncCallImage(MainFragment.FragmentCallback fragmentCallback) {
        this.fragmentCallback = fragmentCallback;
    }
    @Override
    public Void doInBackground(String... params) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(GETTY_URL + params[0])
                .addHeader(API_KEY, GETTY_KEY)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String s = response.body().string();
            Gson gson = new Gson();
            payLoad = gson.fromJson(s, PayLoad.class);
        } catch (IOException e) {
            Log.i("", e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        Log.i("", "onPostExecute");
        fragmentCallback.onTaskDone(payLoad);
    }

    @Override
    protected void onPreExecute() {
        Log.i("", "onPreExecute");
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        Log.i("", "onProgressUpdate");
    }

    public PayLoad getPayLoad() {
        return payLoad;
    }
}