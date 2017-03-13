package com.example.kangjisung.likeroom.FragmentProduct;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.kangjisung.likeroom.ObjectManager.ProductListItem;
import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.Util.LayoutManager;
import com.example.kangjisung.likeroom.inventory.calc;

public class ProductEditDialog extends Dialog
{
    private EditText Productname;//이름
    private EditText Productcost;//원가
    private EditText Productprice;//판매가
    private EditText Productresidual;//잔존가
    private int dismissMessage = 0;

    private String mode;
    private ProductListItem modifyItem;

    ProductEditDialog(Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);

        mode = "ADD";
    }

    ProductEditDialog(Context context, ProductListItem object){
        super(context, android.R.style.Theme_Translucent_NoTitleBar);

        mode = "MODIFY";

        modifyItem = object;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.product_add_dialog);

        LayoutManager.setDialogTitle(findViewById(R.id.layout_title), true, false, "새 제품 추가");
        findViewById(R.id.inc_btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Productname=(EditText)findViewById(R.id.editText_name);
        Productcost=(EditText)findViewById(R.id.editText_cost);
        Productprice=(EditText)findViewById(R.id.editText_price);
        Productresidual=(EditText)findViewById(R.id.editText_residual);

        calc mCalc = calc.getInstance();
        if(mode == "MODIFY"){
            Productname.setText(modifyItem.getName());
        }


        Button mOKButton = (Button)findViewById(R.id.button_ok);
        mOKButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View onClickView){
                if(mode == "ADD"){

                }
                else if(mode == "MODIFY"){
                    dismissMessage = 1;
                }
                //제품정보 넣기
                /*
                new ClientDataBase("insert into `제품정보` (`이름`,`원가`,`판매가`,`잔존가`,`등록일`,`사용여부`) values (\""+Productname.getText().toString()+"\","+Productcost+","+Productprice+","+Productresidual+",(select date('now')),\"true\");",2,0, MainActivity.con);
                int proDuct;//제품코드
                new ClientDataBase("select `제품번호` from `제품정보` where `이름`=\""+Productname.getText().toString()+"\"",1,1, MainActivity.con);
                proDuct=Integer.parseInt(DBstring[0]);
                NetworkModule networkModule = new NetworkModule();
                networkModule.InsertNewProductName(Integer.parseInt(7), proDuct,Productname.getText().toString(),Integer.parseInt(Productcost.getText().toString()),Integer.parseInt(Productprice.getText().toString()),Integer.parseInt(Productresidual.getText().toString()));
                */
                dismiss();
            }
        });
    }

    public int getDismissMessage()
    {
        return dismissMessage;
    }
}