package com.example.kangjisung.likeroom.CommunicationManager;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

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

        return GetServerMessage(CommunicationWithServer(strings[0]));
    }

    String GetServerMessage(InputStream serverDataStream) {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader serverDataBufferedReader = new BufferedReader(new InputStreamReader(serverDataStream));
        String tempAppendString = null;
        try {
            while((tempAppendString = serverDataBufferedReader.readLine()) != null) {
                stringBuilder.append(tempAppendString + "\n");
            }
        }
        catch (Exception err) {
            Log.d(logCatTag, "Error in GetServerMessage: " + err.getMessage());
        }
        return stringBuilder.toString();
    }

    InputStream CommunicationWithServer(String targetUrl) {
        InputStream serverResultContent = null;
        try {
            HttpClient androidHttpClient = new DefaultHttpClient();
            HttpResponse androidHttpResponse = androidHttpClient.execute(new HttpGet(targetUrl));
            serverResultContent = androidHttpResponse.getEntity().getContent();
        }
        catch (Exception err) {
            Log.d(logCatTag, "Error in CommunicationWithServer: " + err.getMessage());
        }
        return serverResultContent;
    }
}
