package com.example.kangjisung.likeroom;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.kangjisung.likeroom.NetworkManager.NetworkModule;
import com.example.kangjisung.likeroom.PermissionManager.AndroidVersionController;
import com.example.kangjisung.likeroom.PermissionManager.UserAccountCrawler;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.DatabaseHelper;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.SynchronizedLocalAndServerDatabase;
import com.example.kangjisung.likeroom.Util.ColorTheme;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;

import static com.example.kangjisung.likeroom.DefineManager.customerDatabaseName;
import static com.example.kangjisung.likeroom.DefineManager.synchronizedLocalAndServerDatabase;

//0218import com.example.kangjisung.likeroom.NetworkManager.NetworkModule;


public class MainActivity extends ActionBarActivity {

    private Handler mHandler;
    private Runnable mRunnable;
    UserAccountCrawler userAccountCrawler;
    String userAccountInfo;
    DatabaseHelper databaseHelper;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTheme(ColorTheme.getTheme(2));
        setContentView(R.layout.activity_main);

        userAccountCrawler = new UserAccountCrawler(this);
        databaseHelper = new DatabaseHelper(getApplicationContext(), customerDatabaseName);
        final AlertDialog.Builder alertNoticeBuilder = new AlertDialog.Builder(this);

        NetworkModule networkModule=new NetworkModule();
        //networkModule.LoadAllStoreInfo();
        //networkModule.InsertNewCustomerInfo("강지성");
        //networkModule.AddToStoreAsNewMember(1, 1);
        //networkModule.DelMemberFromStore(1);
        //networkModule.GetStoreAndCustomerRegisteredInfo(1);
        //networkModule.InsertMileageLog(1, 200);
       ////0218  networkModule.GetMileageSum(1);

        synchronizedLocalAndServerDatabase = new SynchronizedLocalAndServerDatabase(getApplicationContext(), networkModule);
        synchronizedLocalAndServerDatabase.SettingAllStoreDataFromServer();

        //SimpleDatabaseTest simpleDatabaseTest = new SimpleDatabaseTest(getApplicationContext());

        userAccountInfo = userAccountCrawler.CheckPermissionGranted();
        synchronizedLocalAndServerDatabase.RegisterMyInfoToServer("customer", "N/A", userAccountInfo, "0000-00-00");
        synchronizedLocalAndServerDatabase.RegisterCustomerToStore();

        Log.d(getString(R.string.app_name), "crawled account info: " + userAccountInfo);

        alertNoticeBuilder.setMessage(getString(R.string.emailLoadFail));
        alertNoticeBuilder.setPositiveButton(getString(R.string.positiveButtonMessage),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new AndroidVersionController(getApplicationContext()).GoToApplicationPermissionSettingScreen();
                        finish();
                    }
                });
        //activity_main-> 배경색만 채워져 있는 레이아웃(나중에 시작화면, 대기화면 등을 넣으면 될 것 같다)
        //맨 처음 시작할 때 activity_main이 뜨고, 한번 클릭하면 ActivityMenu로 넘어간다.

        mRunnable = new Runnable() {
            @Override
            public void run() {
                Log.d(getString(R.string.app_name), "go to store select layout");
                Intent intent = new Intent(getApplicationContext(), ActivityStoreSelect.class);
                startActivity(intent);  //ActivityMenu으로 넘어간다.
                finish();
            }
        };

        if (userAccountInfo == null) {
            AlertDialog alertDialog = alertNoticeBuilder.create();
            alertDialog.show();
        }
        else {
            mHandler = new Handler();
            mHandler.postDelayed(mRunnable, 1);
            Log.d(getString(R.string.app_name), "starting app");
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    }

    protected void onDestroy() {
        Log.i("test", "onDstory()");
        if (mHandler != null)
            mHandler.removeCallbacks(mRunnable);
        super.onDestroy();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public InputStream getInputStreamFromUrl(String url) {
        InputStream content = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(url));
            content = response.getEntity().getContent();
        } catch (Exception e) {
            Log.d("[GET REQUEST]", "Network exception", e);
        }
        return content;
    }
}
