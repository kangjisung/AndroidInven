package com.example.kangjisung.likeroom.CommunicationManager;

import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;

/**
 * Created by stories2 on 2017. 1. 24..
 */

public class NetworkModule extends AsyncTask<String, Void, String>{
    String logCatTag = "test";

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
        Log.d(logCatTag, "post: " + s);
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
        Log.d(logCatTag, "back: " + strings[0]);
        return strings[0];
    }

    InputStream CommunicationWithServer(String targetUrl) {
        InputStream serverResultContent = null;
        try {
            //HttpClient
        }
        catch (Exception err) {
            Log.d(logCatTag, "Error in CommunicationWithServer: " + err.getMessage());
        }
        return serverResultContent;
    }
}
