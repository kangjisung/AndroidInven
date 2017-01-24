package com.example.kangjisung.likeroom.CommunicationManager;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by stories2 on 2017. 1. 24..
 */

public class NetworkModule extends AsyncTask<String, Void, String>{
    public NetworkModule() {
        super();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d("test", "post: " + s);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected String doInBackground(String... strings) {
        Log.d("test", "back: " + strings[0]);
        return strings[0];
    }
}
