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

public class HelpDialog extends Dialog{

    Button closeButton ;
    public HelpDialog(Context context) {
        super(context);
    }

    public HelpDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected HelpDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams Window = new WindowManager.LayoutParams();
        Window.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        Window.dimAmount=0.8f;
        getWindow().setAttributes(Window);

        View helpDialog = View.inflate(getContext(),R.layout.dialog_help,null);
        setContentView(helpDialog);


        closeButton = (Button)helpDialog.findViewById(R.id.closeButton);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }



}
