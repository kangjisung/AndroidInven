package com.example.kangjisung.likeroom.FragmentProduct;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

<<<<<<< HEAD
import com.example.kangjisung.likeroom.MainActivity;
import com.example.kangjisung.likeroom.NetworkManager.NetworkModule;
import com.example.kangjisung.likeroom.ObjectManager.ProductListItem;
import com.example.kangjisung.likeroom.ObjectManager.ProductMuchStoreListItem;
import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.ClientDataBase;
import com.example.kangjisung.likeroom.Util.LayoutManager;
import com.example.kangjisung.likeroom.inventory.calc;

import java.util.ArrayList;

import static com.example.kangjisung.likeroom.MainActivity.PriNum;
import static com.example.kangjisung.likeroom.ObjectManager.ProductObjManager.context;
import static com.example.kangjisung.likeroom.ObjectManager.ProductObjManager.muchStoreArrayList;
import static com.example.kangjisung.likeroom.SQLiteDatabaseControl.ClientDataBase.DBstring;

=======
import com.example.kangjisung.likeroom.ObjectManager.ProductListItem;
import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.Util.LayoutManager;
import com.example.kangjisung.likeroom.inventory.calc;

>>>>>>> refs/remotes/origin/store-app-byeongmun
public class ProductEditDialog extends Dialog
{
    private EditText Productname;//이름
    private EditText Productcost;//원가
    private EditText Productprice;//판매가
    private EditText Productresidual;//잔존가
    private int dismissMessage = 0;
    int proDuct; //제품코드

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

        if(mode == "MODIFY"){
            new ClientDataBase("select `제품코드`, `원가`,`판매가`,`잔존가` from `제품정보` where `이름`=\""+modifyItem.getName()+"\"",1,4,context);
            if(DBstring[0]!=null) {
                proDuct = Integer.parseInt(DBstring[0]);
            }
            if(DBstring[1]!=null) {
                Productcost.setText(DBstring[1]);
            }
            if(DBstring[2]!=null) {
                Productprice.setText(DBstring[2]);
            }
            if(DBstring[3]!=null) {
                Productresidual.setText(DBstring[3]);
            }
            Productname.setText(modifyItem.getName());
        }

        Button mOKButton = (Button)findViewById(R.id.button_ok);
        mOKButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View onClickView){
                if(mode == "ADD"){
                    new ClientDataBase("insert into `제품정보` (`이름`,`원가`,`판매가`,`잔존가`,`등록일`,`사용여부`) values (\""+Productname.getText().toString()+"\","+Productcost+","+Productprice+","+Productresidual+",(select date('now')),\"true\");",2,0, context);
                    NetworkModule networkModule=new NetworkModule();
                    networkModule.InsertNewProductName(Integer.parseInt(PriNum),proDuct,Productname.getText().toString(),Integer.parseInt(Productcost.getText().toString()),Integer.parseInt(Productprice.getText().toString()),Integer.parseInt(Productresidual.getText().toString()));

                    dismissMessage = 1;
                }
                else if(mode == "MODIFY"){
                    new ClientDataBase("update `제품정보` set `이름`=\""+Productname.getText().toString()+"\",`원가`="+Productcost.getText().toString()+",`판매가`="+Productprice.getText().toString()+",`잔존가`="+Productresidual.getText().toString()+" where `제품코드`="+proDuct+"",2,0,context);
                    NetworkModule networkModule=new NetworkModule();
                    networkModule.UpdateRegisteredProductName(Integer.parseInt(PriNum),proDuct,Productname.getText().toString(),Integer.parseInt(Productcost.getText().toString()),Integer.parseInt(Productprice.getText().toString()),Integer.parseInt(Productresidual.getText().toString()));

                    dismissMessage = 1;
                }
                dismiss();
            }
        });
    }

    public int getDismissMessage()
    {
        return dismissMessage;
    }
}