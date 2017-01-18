package com.example.kangjisung.likeroom.User.MileageManage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.ClientDataBase;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.DatabaseHelper;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.LocalHostDatabaseManager;
import com.example.kangjisung.likeroom.server.HttpCommunicationProcess;

import static android.os.Build.ID;
import static com.example.kangjisung.likeroom.R.id.StoreAddress;
import static com.example.kangjisung.likeroom.R.id.StoreName;
import static com.example.kangjisung.likeroom.R.id.StorePhone;
import static com.example.kangjisung.likeroom.SQLiteDatabaseControl.ClientDataBase.DBstring;

/////////////////////////////////////마일리지 적립 클래스
public class mileage extends Activity {

    Button Plus;  //적립버튼
    Button[] Btn=new Button[12];  //버튼배열
    ImageButton CloBtn; //닫기 버튼
    int[] numBtn={R.id.Num0,R.id.Num1,R.id.Num2,R.id.Num3,R.id.Num4,R.id.Num5,R.id.Num6,R.id.Num7,R.id.Num8,R.id.Num9,R.id.Num10}; //버튼 id 배열
    EditText NumView;
    int i;
    double Per=0.1;  //퍼센트
    HttpCommunicationProcess httpCommunicationProcess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_mileage);
        Intent mil = getIntent(); ////user클래스에서 눌렀던 사용자의 이름을 가져옴
        final String name = mil.getExtras().getString("name");  //가져온 사용자의 이름을 넣음

        NumView = (EditText) findViewById(R.id.NumView);
/////////////////////버튼만들기
        for (i = 0; i <= 10; i++) {
            Btn[i] = (Button) findViewById(numBtn[i]);
        }
        for (i = 0; i <= 10; i++) {
            final int index;
            index = i;
            Btn[index].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String a;
                    a = NumView.getText().toString()
                            + Btn[index].getText().toString();
                    NumView.setText(a);
                }
            });
        }

        CloBtn = (ImageButton) findViewById(R.id.BtnClose);
        CloBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ///////////////마일리지 적립 버튼
        Plus = (Button) findViewById(R.id.PlusBtn);
        Plus.setOnClickListener(new View.OnClickListener(){
            int point; //산가격
            int sum; //디비 포인트 합계
            int PriNum; //고유회원등록번호
            public void onClick(View v) {
                point = Integer.parseInt(NumView.getText().toString());
                point=(int)(point*Per);
                new ClientDataBase("select `포인트`,`고유회원등록번호` from `포인트` where `고유회원등록번호`=(select `고유회원등록번호` from `회원정보` where `이름`=\"" + name + "\");;",1,2,getApplicationContext()); //포인트 있는지 검색
                PriNum=Integer.parseInt(DBstring[1]);
                if(DBstring[0]==null){//////////////////없다면 insert
                    new ClientDataBase("insert into `포인트` (`고유회원등록번호`,`포인트`,`포인트갱신날짜`) values ((select `고유회원등록번호` from `회원정보` where `이름`=\""+name+"\"),"+point+",(select date('now')));",2,0,getApplicationContext());
                }
                else {////////////////있으면 계산해서 update
                    sum=point+Integer.parseInt(DBstring[0]);
                    new ClientDataBase("UPDATE `포인트` SET `포인트`="+sum+", `포인트갱신날짜`=(select date('now')) WHERE `고유회원등록번호`=(select `고유회원등록번호` from `회원정보` where `이름`=\"" + name + "\");", 3, 0, getApplicationContext());
                }
                httpCommunicationProcess=new HttpCommunicationProcess(getApplicationContext());
                httpCommunicationProcess.execute("http://lamb.kangnam.ac.kr:4200/Smoothie/2/CustomerMileageUpdate?id="+PriNum+"&mileage="+point+"");
                Toast.makeText(getApplicationContext(), name+"에게"+point+"빵이 적립되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

}
