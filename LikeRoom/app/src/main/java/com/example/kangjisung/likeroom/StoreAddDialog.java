package com.example.kangjisung.likeroom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class StoreAddDialog extends Dialog
{
    private Button mLeftButton;
    private Button mRightButton;
    SearchView searchNewStore;

    private View.OnClickListener mLeftClickListener;
    private View.OnClickListener mRightClickListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        View addNewStoreDialogView = View.inflate(getContext(), R.layout.store_add_dialog, null);
        setContentView(addNewStoreDialogView);
        //setContentView(R.layout.store_add_dialog);

        mLeftButton = (Button) addNewStoreDialogView.findViewById(R.id.button_back);
        mRightButton = (Button) addNewStoreDialogView.findViewById(R.id.button_ok);
        searchNewStore = (SearchView) addNewStoreDialogView.findViewById(R.id.searchNewStore);

        // 클릭 이벤트 셋팅
        if (mLeftClickListener != null && mRightClickListener != null) {
            mLeftButton.setOnClickListener(mLeftClickListener);
            //mRightButton.setOnClickListener(mRightClickListener);
        } else if (mLeftClickListener != null
                && mRightClickListener == null) {
            mLeftButton.setOnClickListener(mLeftClickListener);
        } else {

        }

        mRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, getContext().getString(R.string.featureLoadFail), Snackbar.LENGTH_SHORT).show();
            }
        });

        searchNewStore.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(getContext().getString(R.string.app_name), "type: " + newText);
                return true;
            }
        });
    }

    // 클릭버튼이 하나일때 생성자 함수로 클릭이벤트를 받는다.
    public StoreAddDialog(Context context, String title, View.OnClickListener singleListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mLeftClickListener = singleListener;
    }

    // 클릭버튼이 확인과 취소 두개일때 생성자 함수로 이벤트를 받는다
    public StoreAddDialog(Context context, String title, String content, View.OnClickListener leftListener, View.OnClickListener rightListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mLeftClickListener = leftListener;
        this.mRightClickListener = rightListener;
    }
}