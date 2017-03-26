package com.teamdk.android.bakery.fragments.product;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.teamdk.android.bakery.utility.NetworkManager.NetworkModule;
import com.teamdk.android.bakery.objectmanager.ProductListItem;
import com.teamdk.android.bakery.R;
import com.teamdk.android.bakery.utility.SQLiteDatabaseControl.ClientDataBase;
import com.teamdk.android.bakery.utility.LayoutManager;

import static com.teamdk.android.bakery.MainActivity.PriNum;
import static com.teamdk.android.bakery.utility.SQLiteDatabaseControl.ClientDataBase.DBstring;

public class ProductEditDialog extends Dialog
{
    private EditText Productname;//이름
    private EditText Productcost;//원가
    private EditText Productprice;//판매가
    private EditText Productresidual;//잔존가
    private String putResidual;
    private int dismissMessage = 0;
    int proDuct; //제품코드
    private String query;

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

        setContentView(R.layout.product_edit_dialog);


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
        Productname.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void afterTextChanged(Editable s) {
                for(int i = s.length()-1; i >= 0; i--){
                    if(s.charAt(i) == '\n'){
                        s.delete(i, i + 1);
                        return;
                    }
                }
            }
        });

        if(mode == "ADD"){
            LayoutManager.setDialogTitle(findViewById(R.id.layout_title), true, false, "새 제품 추가");
        }
        else{
            LayoutManager.setDialogTitle(findViewById(R.id.layout_title), true, false, "새 제품 수정");
            proDuct = modifyItem.getNum();
            Productcost.setText(String.valueOf(modifyItem.getCost()));
            Productprice.setText(String.valueOf(modifyItem.getPrice()));
            Productresidual.setText(String.valueOf(modifyItem.getResidual()));
            Productname.setText(modifyItem.getName());
        }

        Button mOKButton = (Button)findViewById(R.id.button_ok);
        mOKButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View onClickView){
                new ClientDataBase("select `제품코드` from `제품정보` where `이름`=\"" + Productname.getText().toString()+"\"", 1,1,getContext());
                if(Productname.getText().length() == 0){
                    Toast.makeText(getContext(), "제품 이름을 입력해야 합니다.", Toast.LENGTH_LONG).show();
                    return;
                }
                if(Productcost.getText().length() == 0){
                    Toast.makeText(getContext(), "원가를 입력해야 합니다.", Toast.LENGTH_LONG).show();
                    return;
                }
                if(Productprice.getText().length() == 0){
                    Toast.makeText(getContext(), "판매가를 입력해야 합니다.", Toast.LENGTH_LONG).show();
                    return;
                }
                putResidual = (Productresidual.getText().length() == 0)?("0"):(Productresidual.getText().toString());

                if (mode == "ADD") {
                    if (DBstring[0] != null){
                        Toast.makeText(getContext(), "중복된 이름입니다.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    else {
                        query = "insert into `제품정보` (`매장번호`, `이름`,`원가`,`판매가`,`잔존가`,`등록일`,`사용여부`) values (" + PriNum + ", \"" + Productname.getText().toString() + "\"," + Productcost.getText().toString() + "," + Productprice.getText().toString() + "," + putResidual + ",(select date('now')),\"true\");";
                        new ClientDataBase(query, 2, 0, getContext());

                        query ="select `제품코드` from `제품정보` where `이름`=\""+ Productname.getText().toString() + "\"";
                        new ClientDataBase(query, 1, 1, getContext());
                        if(DBstring[0]!=null) {
                            proDuct = Integer.parseInt(DBstring[0]);

                            NetworkModule networkModule = new NetworkModule();
                            networkModule.InsertNewProductName(Integer.parseInt(PriNum), proDuct, Productname.getText().toString(), Integer.parseInt(Productcost.getText().toString()), Integer.parseInt(Productprice.getText().toString()), Integer.parseInt(putResidual));

                            dismissMessage = 1;

                            dismiss();
                        }
                    }
                }
                else if (mode == "MODIFY") {
                    if(DBstring[0]!=null && proDuct != Integer.parseInt(DBstring[0])) {
                        Toast.makeText(getContext(), "중복된 이름입니다.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    else{
                        new ClientDataBase("update `제품정보` set `이름`=\"" + Productname.getText().toString() + "\",`원가`=" + Productcost.getText().toString() + ",`판매가`=" + Productprice.getText().toString() + ",`잔존가`=" + putResidual + " where `제품코드`=" + proDuct + "", 2, 0, getContext());
                        NetworkModule networkModule = new NetworkModule();
                        networkModule.UpdateRegisteredProductName(Integer.parseInt(PriNum), proDuct, Productname.getText().toString(), Integer.parseInt(Productcost.getText().toString()), Integer.parseInt(Productprice.getText().toString()), Integer.parseInt(putResidual));

                        dismissMessage = 1;

                        dismiss();
                    }
                }
            }
        });
    }

    public int getDismissMessage()
    {
        return dismissMessage;
    }
}