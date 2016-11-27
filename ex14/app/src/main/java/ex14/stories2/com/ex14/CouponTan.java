package ex14.stories2.com.ex14;

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
import android.widget.ListView;

import ex14.stories2.com.ex14.CustomStoreListView.CustomStoreListViewAdapter;
import ex14.stories2.com.ex14.CustomStoreListView.EachStoreListViewItem;

public class CouponTan extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ListView customerRegisteredStoreList;
    CustomStoreListViewAdapter customStoreListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_coupon_tan_main);

        AndroidUiInit();

        customStoreListViewAdapter.AddNewCustomStoreListItem(null, "testStore", "app test");

        customerRegisteredStoreList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                int itemPosition = i;
                EachStoreListViewItem eachStoreListViewItem = (EachStoreListViewItem) adapterView.getItemAtPosition(itemPosition);

                String eachStoreTitle = eachStoreListViewItem.GetEachStoreTitle(),
                        eachStoreSubTitle = eachStoreListViewItem.GetEachStoreSubTitle();
                Drawable eachStoreIcon = eachStoreListViewItem.GetEachStoreIcon();

                Intent newActivityIntent = new Intent(getApplicationContext(), CouponTanStoreInfo.class);
                newActivityIntent.putExtra("targetStoreTitle", eachStoreTitle);
                startActivity(newActivityIntent);

                Log.d("ex14", "title: " + eachStoreTitle);
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
                Snackbar.make(view, "Check Your Internet Connection", Snackbar.LENGTH_LONG)
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

        customerRegisteredStoreList = (ListView)findViewById(R.id.listOfMyStore);
        customStoreListViewAdapter = new CustomStoreListViewAdapter();
        customerRegisteredStoreList.setAdapter(customStoreListViewAdapter);
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
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
