package com.example.kangjisung.likeroom.NetworkManager;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by stories2 on 2016. 12. 27..
 */

public class HttpCommunicationProcess extends AsyncTask<String, Integer, String> {
    //Context context;

    public HttpCommunicationProcess() {
        super();
        //this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        //Log.d(context.getString(R.string.app_name), "onPostExecuteResult: " + s);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
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
        //Log.d(context.getString(R.string.app_name), "doInBackground: " + strings[0]);
        try{
            HttpParams httpParameters = new BasicHttpParams();
// Set the timeout in milliseconds until a connection is established.
// The default value is zero, that means the timeout is not used.
            int timeoutConnection = 3000;
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
// Set the default socket timeout (SO_TIMEOUT)
// in milliseconds which is the timeout for waiting for data.
            int timeoutSocket = 5000;
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

            DefaultHttpClient httpClient = new DefaultHttpClient();
            httpClient.setParams(httpParameters);
            HttpResponse httpResponse = httpClient.execute(new HttpGet(strings[0]));
            InputStream inputStreamContent = httpResponse.getEntity().getContent();
            return ConvertInputStreamToString(inputStreamContent);
        }
        catch (Exception err) {
            //Log.d(context.getString(R.string.app_name), "Error in doInBackground: " + err.getMessage());
        }
        return null;
    }

    String ConvertInputStreamToString(InputStream inputStream) {
        String convertedString = null;
        try{
            ByteArrayOutputStream byteArrayOutputStreamBuffer = new ByteArrayOutputStream();
            int readDataLength;
            byte[] byteData = new byte[1];
            while((readDataLength = inputStream.read(byteData)) != -1) {
                byteArrayOutputStreamBuffer.write(byteData, 0, readDataLength);
            }
            byteArrayOutputStreamBuffer.flush();

            convertedString = new String(byteArrayOutputStreamBuffer.toByteArray());
        }
        catch (Exception err) {
            //Log.d(context.getString(R.string.app_name), "Error in ConvertInputStreamToString: " + err.getMessage());
        }
        return convertedString;
    }
}
