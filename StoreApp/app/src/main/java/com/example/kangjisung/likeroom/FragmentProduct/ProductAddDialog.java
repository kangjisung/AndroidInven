package com.example.kangjisung.likeroom.FragmentProduct;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kangjisung.likeroom.MainActivity;
import com.example.kangjisung.likeroom.NetworkManager.NetworkModule;
import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.ClientDataBase;

import static android.os.Build.VERSION_CODES.N;
import static com.example.kangjisung.likeroom.MainActivity.PriNum;
import static com.example.kangjisung.likeroom.R.id.recyclerView;
import static com.example.kangjisung.likeroom.SQLiteDatabaseControl.ClientDataBase.DBstring;

public class ProductAddDialog extends Dialog
{
    private EditText Productname;//이름
    private EditText Productcost;//원가
    private EditText Productprice;//판매가
    private EditText Productresidual;//잔존가

    ProductAddDialog(Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);
        Productname=(EditText)findViewById(R.id.editText_name);
        Productcost=(EditText)findViewById(R.id.editText_cost);
        Productprice=(EditText)findViewById(R.id.editText_price);
        Productresidual=(EditText)findViewById(R.id.editText_residual);

        setContentView(R.layout.product_add_dialog);

        initializeDialogTitleBar();

        Button mOKButton = (Button)findViewById(R.id.button_ok);
        mOKButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View onClickView){
                //제품정보 넣기
                new ClientDataBase("insert into `제품정보` (`이름`,`원가`,`판매가`,`잔존가`,`등록일`,`사용여부`) values (\""+Productname.getText().toString()+"\","+Productcost+","+Productprice+","+Productresidual+",(select date('now')),\"true\");",2,0, MainActivity.con);
                int proDuct;//제품코드
                new ClientDataBase("select `제품번호` from `제품정보` where `이름`=\""+Productname.getText().toString()+"\"",1,1,MainActivity.con);
                proDuct=Integer.parseInt(DBstring[0]);
                NetworkModule networkModule=new NetworkModule();
                networkModule.InsertNewProductName(Integer.parseInt(PriNum),proDuct,Productname.getText().toString(),Integer.parseInt(Productcost.getText().toString()),Integer.parseInt(Productprice.getText().toString()),Integer.parseInt(Productresidual.getText().toString()));
                dismiss();
            }
        });

        // TODO : 초기화 코드 삽입
    }

    private void initializeDialogTitleBar()
    {
        TextView mTextViewTitle = (TextView)findViewById(R.id.textView_title);
        Button mBackButton = (Button)findViewById(R.id.button_dialog_back);
        Button mOKButton = (Button)findViewById(R.id.button_dialog_ok);
        mOKButton.setVisibility(View.GONE);

        mTextViewTitle.setText("새 제품 추가");
        mBackButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View onClickView){
                cancel();
            }
        });
    }
}