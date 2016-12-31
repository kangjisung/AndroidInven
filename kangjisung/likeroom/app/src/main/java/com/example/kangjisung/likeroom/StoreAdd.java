package com.example.kangjisung.likeroom;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kangjisung.likeroom.SQLiteDatabaseControl.ClientDataBase;
import com.example.kangjisung.likeroom.server.HttpCommunicationProcess;

/**
 * Created by kangjisung on 2016-12-21.
 */

public class StoreAdd extends Activity {

    EditText StoreName, StoreAddress, StorePhone;
    Button StoreAddBtn;
    String checkSql;
    HttpCommunicationProcess httpCommunicationProcess;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_add);

        StoreName=(EditText)findViewById(R.id.StoreName);
        StoreAddress=(EditText)findViewById(R.id.StoreAddress);
        StorePhone=(EditText)findViewById(R.id.StorePhone);
        StoreAddBtn=(Button)findViewById(R.id.StoreAddBtn);

        StoreAddBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                httpCommunicationProcess=new HttpCommunicationProcess(getApplicationContext());
                /*try {
                    String dbResult = httpCommunicationProcess.execute("http://lamb.kangnam.ac.kr:4200/Smoothie/2/DatabaseQueryTest/?query=select `이름` from `매장` where `이름`=\"이름\" and `전화번호`=\"전화번호\";").get().toString();
                    //"http://lamb.kangnam.ac.kr:4200/Smoothie/2/DatabaseQueryTest/?query=select `이름` from `매장` where `이름`=\"" + StoreName.getText().toString() + "\" and `전화번호`=\"" + StorePhone.getText().toString() + "\";"
                    Log.d("test", "test: "+dbResult);
                    checkSql=dbResult;
                }
                catch (Exception err){
                }*/

               /* Cursor c = sqLiteDatabase.rawQuery("select `이름` from `매장` where `이름`=\""+StoreName.getText().toString()+"\" and `전화번호`=\""+StorePhone.getText().toString()+"\";",null);
                while(c.moveToNext()) {
                    checkSql = c.getString(0);
                }*/
                if(checkSql==null) {
                    try {
                        String dbResult = httpCommunicationProcess.execute("http://lamb.kangnam.ac.kr:4200/Smoothie/2/InsertNewStoreInfoData?shopAddress=" + StoreAddress.getText().toString() + "&shopName=" + StoreName.getText().toString() + "&shopPhoneNumber=" + StorePhone.getText().toString() + "").get();
                        Log.d("test", "test: " + dbResult);
                    }
                    catch (Exception err){
                    }
                    //new ClientDataBase("insert into `매장` (`이름`,`주소`,`전화번호`) values(\"" + StoreName.getText().toString() + "\",\"" + StoreAddress.getText().toString() + "\",\"" + StorePhone.getText().toString() + "\");",2,0,getApplicationContext());
                   // Toast.makeText(getApplicationContext(), "입력이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }
}
