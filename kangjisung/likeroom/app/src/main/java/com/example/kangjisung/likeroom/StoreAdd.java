package com.example.kangjisung.likeroom;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kangjisung.likeroom.SQLiteDatabaseControl.ClientDataBase;
import com.example.kangjisung.likeroom.server.HttpCommunicationProcess;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kangjisung on 2016-12-21.
 */

public class StoreAdd extends Activity {

    EditText StoreName, StoreAddress, StorePhone;
    Button StoreAddBtn;
    String checkSql=" ";
    HttpCommunicationProcess httpCommunicationProcess;
    JSONObject jsonObject;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_add);

        StoreName=(EditText)findViewById(R.id.StoreName);
        StoreAddress=(EditText)findViewById(R.id.StoreAddress);
        StorePhone=(EditText)findViewById(R.id.StorePhone);
        StoreAddBtn=(Button)findViewById(R.id.StoreAddBtn);


        StoreAddBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //서버에 매장을 검색하고
                httpCommunicationProcess=new HttpCommunicationProcess(getApplicationContext());
                try {

                    String text="http://lamb.kangnam.ac.kr:4200/Smoothie/2/LoadStoreInfo/?name="+StoreName.getText().toString()+"&phone="+StorePhone.getText().toString()+";";
                    String dbResult = httpCommunicationProcess.execute(text).get();
                    jsonObject = new JSONObject(dbResult);
                    Log.d("test", "test: "+dbResult);
                    checkSql=jsonObject.getString("Result");
                }
                catch (Exception err){
                }

                httpCommunicationProcess=new HttpCommunicationProcess(getApplicationContext());
                //매장이 없을시 입력력
               if(checkSql.equals("Fail")) {
                    try {
                        httpCommunicationProcess.execute("http://lamb.kangnam.ac.kr:4200/Smoothie/2/InsertNewStoreInfoData/?&address="+StoreAddress.getText().toString()+"&name="+StoreName.getText().toString()+"&phone="+StorePhone.getText().toString()+";");
                    }
                    catch (Exception err){
                        Log.d("error",err.getMessage());
                    }
                }
                else{
                   try {
                       new ClientDataBase("insert `매장` into(`전화번호`,`매장번호`,`주소`,`이름`,`위도`,`경도`,`등록일`) value("+jsonObject.getString("전화번호")+","+jsonObject.getString("매장번호")+","+jsonObject.getString("주소")+","+jsonObject.getString("이름")+","+jsonObject.getString("위도")+","+jsonObject.getString("경도")+","+jsonObject.getString("서비스 가입 날짜")+");",2,0,getApplicationContext());
                   } catch (JSONException e) {
                       e.printStackTrace();
                   }

               }

                finish();
            }
        });
    }
}
