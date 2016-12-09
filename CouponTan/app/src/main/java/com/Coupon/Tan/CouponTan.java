package com.Coupon.Tan;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.Coupon.Tan.CustomStoreListView.CustomStoreListViewAdapter;
import com.Coupon.Tan.CustomStoreListView.EachStoreListViewItem;
import com.Coupon.Tan.UserDeviceInfo.CustomersSavedInfoFromDevice;

public class CouponTan extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ListView customerRegisteredStoreList;
    CustomStoreListViewAdapter customStoreListViewAdapter;
    ImageView userImageInfo;
    TextView userNameInfo, userPhoneNumberInfo;
    View hamburgerViewHeader;
    CustomersSavedInfoFromDevice customersSavedInfoFromDevice;
    String logCatTag;
    int addNewStoreItemPosition = 0;

    String[] testCaseOfUserInfo = new String[]{
      "김현우", "010-5635-1845"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_coupon_tan_main);

        AndroidUiInit();

        logCatTag = getString(R.string.app_name);

        //신규 추가 아이템
        customStoreListViewAdapter.AddNewCustomStoreListItem(getResources().getDrawable(R.drawable.add_icon), getString(R.string.addNewStoreComment), "");

        //매장 목록
        customStoreListViewAdapter.AddNewCustomStoreListItem(getResources().getDrawable(R.mipmap.ic_launcher), "테스트 매장 1", "테스트 중 입니다");
        customStoreListViewAdapter.AddNewCustomStoreListItem(getResources().getDrawable(R.mipmap.ic_launcher), "테스트 매장 2", "테스트 중 입니다");

        customerRegisteredStoreList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                int itemPosition = i;
                EachStoreListViewItem eachStoreListViewItem = (EachStoreListViewItem) adapterView.getItemAtPosition(itemPosition);

                String eachStoreTitle = eachStoreListViewItem.GetEachStoreTitle(),
                        eachStoreSubTitle = eachStoreListViewItem.GetEachStoreSubTitle();
                Drawable eachStoreIcon = eachStoreListViewItem.GetEachStoreIcon();

                if(itemPosition == addNewStoreItemPosition) {//신규 매장 추가
                }
                else {//기존 매장 선택
                    Intent newActivityIntent = new Intent(getApplicationContext(), com.Coupon.Tan.CouponTanStoreInfo.class);
                    newActivityIntent.putExtra("targetStoreTitle", eachStoreTitle);
                    startActivity(newActivityIntent);
                }

                Log.d(logCatTag, "title: " + eachStoreTitle + " #" + itemPosition);
            }
        });
    }

    public void AndroidUiInit() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setImageDrawable(getResources().getDrawable(R.drawable.add_icon));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, getString(R.string.plusButtonExceptionComment), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        hamburgerViewHeader = navigationView.getHeaderView(0);

        userImageInfo = (ImageView)hamburgerViewHeader.findViewById(R.id.userImageInfo);
        userNameInfo = (TextView)hamburgerViewHeader.findViewById(R.id.userNameInfo);
        userPhoneNumberInfo = (TextView)hamburgerViewHeader.findViewById(R.id.userPhoneNumberInfo);

        customerRegisteredStoreList = (ListView)findViewById(R.id.listOfMyStore);
        customStoreListViewAdapter = new CustomStoreListViewAdapter();
        customerRegisteredStoreList.setAdapter(customStoreListViewAdapter);
        customersSavedInfoFromDevice = new CustomersSavedInfoFromDevice(this.getApplicationContext(), this);


        //testCaseOfUserInfo[1] = customersSavedInfoFromDevice.GetDevicePhoneNumber();
        customersSavedInfoFromDevice.CheckRunningAndroidVersion();

        userNameInfo.setText(testCaseOfUserInfo[0]);
        userPhoneNumberInfo.setText(testCaseOfUserInfo[1]);

        navigationView.getMenu().findItem(R.id.nav_camera).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_gallery).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_slideshow).setVisible(false);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.coupon_tan, menu);
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
            Log.d(logCatTag, "setting top button");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Log.d(logCatTag,"cameraTest");
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
            Intent shareAppToOthers = new Intent();
            shareAppToOthers.setAction(Intent.ACTION_SEND);
            shareAppToOthers.putExtra(Intent.EXTRA_TEXT, "Share Test");
            shareAppToOthers.setType("text/*");
            startActivity(Intent.createChooser(shareAppToOthers, getString(R.string.appShareComment)));
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
