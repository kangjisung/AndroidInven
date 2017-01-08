package com.example.kangjisung.likeroom;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

/**
 * Created by stories2 on 2017. 1. 7..
 */

public class StoreDeleteCheckDialog extends Dialog {

    Button btnCancel, btnAccept;
    Activity storeTabActivity;
    String targetStoreId;

    public StoreDeleteCheckDialog(Activity storeTabActivity, Context context, String targetStoreId) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.storeTabActivity = storeTabActivity;
        this.targetStoreId = targetStoreId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        View delStoreDialogView = View.inflate(getContext(), R.layout.store_delete_dialog, null);
        setContentView(delStoreDialogView);

        btnCancel = (Button) delStoreDialogView.findViewById(R.id.btnCancel);
        btnAccept = (Button) delStoreDialogView.findViewById(R.id.btnAccept);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent storeDetailViewInfoTab = new Intent();
                storeDetailViewInfoTab.putExtra("deleteTargetStoreId", targetStoreId);
                //getActivity().setResult(Activity.RESULT_OK);
                storeTabActivity.setResult(Activity.RESULT_OK, storeDetailViewInfoTab);
                storeTabActivity.finish();
            }
        });
    }
}
