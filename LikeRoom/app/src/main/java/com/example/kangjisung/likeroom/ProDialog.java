package com.example.kangjisung.likeroom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

/**
 * Created by Soyeon on 2017-02-25.
 */

public class ProDialog extends Dialog{

    Button closeButton ;
    public ProDialog(Context context) {
        super(context);
    }

    public ProDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected ProDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams Window = new WindowManager.LayoutParams();
        Window.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        Window.dimAmount=0.8f;
        getWindow().setAttributes(Window);

        View proDialog = View.inflate(getContext(),R.layout.dialog_pro,null);
        setContentView(proDialog);


        closeButton = (Button)proDialog.findViewById(R.id.closeButton);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }



}
