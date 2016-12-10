package com.Coupon.Tan.PopViewManager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.Coupon.Tan.CustomStoreListView.CustomStoreListViewAdapter;
import com.Coupon.Tan.DataIOManager.DemoDataPusher;
import com.Coupon.Tan.R;
import com.Coupon.Tan.SearchEngine.SimpleFinder;

/**
 * Created by stories2 on 2016. 12. 10..
 */

public class AddNewStoreManager extends AppCompatActivity {
    ImageButton btnClosePopUp, btnCheckTarget;
    SearchView searchStoreWhatYouWant;
    ListView listOfSearchedStore;
    SimpleFinder storeListFinder;
    DemoDataPusher demoDataPusher;
    String[][] allStoreListData;
    CustomStoreListViewAdapter customStoreListViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_new_store);

        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getSupportActionBar().hide();

        btnClosePopUp = (ImageButton) findViewById(R.id.btnClosePopUp);
        btnCheckTarget = (ImageButton) findViewById(R.id.btnCheckTarget);
        searchStoreWhatYouWant = (SearchView) findViewById(R.id.searchStoreWhatYouWant);
        listOfSearchedStore = (ListView) findViewById(R.id.listOfSearchedStore);
        storeListFinder = new SimpleFinder();
        demoDataPusher = new DemoDataPusher();
        customStoreListViewAdapter = new CustomStoreListViewAdapter();

        listOfSearchedStore.setAdapter(customStoreListViewAdapter);

        allStoreListData = demoDataPusher.GetAllSavedData();

        btnClosePopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnCheckTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        searchStoreWhatYouWant.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(getString(R.string.app_name), "query: " +query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(getString(R.string.app_name), "newText: " + newText);

                int cnt = 0, listShowRowLength, i;

                try {
                    listShowRowLength = customStoreListViewAdapter.getCount();
                    Log.d(getString(R.string.app_name), "list size: " + listShowRowLength);
                    for(i = listShowRowLength - 1; i >= 0; i -= 1) {
                        customStoreListViewAdapter.DeleteTargetStoreListItem(i);
                    }
                    Log.d(getString(R.string.app_name), "list cleaned");
                    for(boolean isSimillarStore : storeListFinder.DemoFindDataThatContainsString(newText)) {
                        if(isSimillarStore) {
                            Log.d(getString(R.string.app_name), "simillar store exist at #" + cnt + " " + allStoreListData[cnt][4]);
                            customStoreListViewAdapter.AddNewCustomStoreListItem(null, allStoreListData[cnt][4], allStoreListData[cnt][1]);
                        }
                        cnt += 1;
                    }
                    customStoreListViewAdapter.notifyDataSetChanged();
                }
                catch (Exception err) {
                    Log.d(getString(R.string.app_name), "Error in onQueryTextChange: " + err.getMessage());
                }

                return false;
            }
        });


    }


}
