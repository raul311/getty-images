package com.images.vicenteruizsalcido.gettyimages;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.images.vicenteruizsalcido.gettyimages.response.Example;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by vicente.ruiz.salcido on 4/1/2017.
 */

public class AsyncCallImage extends AsyncTask<String, Void, Void> {
    private MainFragment.FragmentCallback fragmentCallback;
    private Example example;

    public AsyncCallImage(MainFragment.FragmentCallback fragmentCallback) {
        this.fragmentCallback = fragmentCallback;
    }
    @Override
    public Void doInBackground(String... params) {
        String search = params[0];
        String key = "n5ttb3egk6592kv4wde4yhtn";
        String Url = "https://api.gettyimages.com/v3/search/images/creative?phrase=" + search;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Url)
                .addHeader("Api-Key", key)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String s = response.body().string();
            Gson gson = new Gson();
            example = gson.fromJson(s, Example.class);
        } catch (Exception e) {
            Log.i("", e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        Log.i("", "onPostExecute");
        fragmentCallback.onTaskDone(example);
    }

    @Override
    protected void onPreExecute() {
        Log.i("", "onPreExecute");
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        Log.i("", "onProgressUpdate");
    }

    public Example getExample() {
        return example;
    }

}