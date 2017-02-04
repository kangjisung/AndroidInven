package com.example.kangjisung.likeroom.inventory;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kangjisung.likeroom.MainActivity;
import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.Util.SQLiteDatabaseControl.ClientDataBase;
import com.example.kangjisung.likeroom.Util.SQLiteDatabaseControl.DatabaseHelper;
import com.example.kangjisung.likeroom.Util.SQLiteDatabaseControl.LocalHostDatabaseManager;
import com.example.kangjisung.likeroom.server.HttpCommunicationProcess;

import static android.R.attr.name;
import static com.example.kangjisung.likeroom.MainActivity.PriNum;
import static com.example.kangjisung.likeroom.SQLiteDatabaseControl.ClientDataBase.DBstring;
//import static com.example.kangjisung.likeroom.inventory.calc.v;

/////////////////////////////////////////재품추가 클래스
public class ProductAdd extends Activity {

    int check=0; //페이지
    EditText edText; //입력창
    TextView txView; //입력창 위에 설명
    Button before, after; //버튼
    ImageButton complete; //완료버튼
    String array[]; //입력값 배열
    HttpCommunicationProcess httpCommunicationProcess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //타이틀 지우기
        setContentView(R.layout.activity_product_add);

        array=new String[5];

        edText=(EditText)findViewById(R.id.ProductName);
        txView=(TextView)findViewById(R.id.ex1textview);
        before=(Button)findViewById(R.id.before);
        after=(Button)findViewById(R.id.after);
        complete=(ImageButton)findViewById(R.id.complete);

///////////////////////완료버튼
        complete.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                new ClientDataBase("insert into `제품정보` (`이름`,`원가`,`판매가`,`잔존가`,`등록일`,`사용여부`) values (\""+array[0]+"\","+array[1]+","+array[2]+","+array[3]+",(select date('now')),\"true\");",2,0,getApplicationContext());
                new ClientDataBase("select `제품코드` from `제품정보` where`이름`=\""+array[0]+"\";",1,1,getApplicationContext());
                    int ProDuctCode=Integer.parseInt(DBstring[0]);
                httpCommunicationProcess=new HttpCommunicationProcess(getApplicationContext());
                httpCommunicationProcess.execute("http://lamb.kangnam.ac.kr:4200/Smoothie/2/InsertNewProductName/?shopId="+ MainActivity.PriNum+"&productId="+ProDuctCode+"&productName="+array[0]+"&primeCost="+array[1]+"&cellCost="+array[2]+"&remainCost="+array[3]+";");
            }
        });
//////////////////////이전버튼
        before.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                check-=1;
                if(check==0) {txView.setText("제품명을 입력하세요");}
                else if(check==1) {txView.setText("원가를 입력하세요");}
                else if(check==2) {txView.setText("판매가를 입력하세요");}
                else if(check==3) {txView.setText("잔존가를 입력하세요");}
                else if(check<0) {
                    Toast.makeText(getApplicationContext(), "가장 처음페이지 입니다.", Toast.LENGTH_SHORT).show();
                    check=0;
                }
                edText.setText("");
            }
        });
        //////////////////////////////다음버튼
        after.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                check+=1;
                array[check]=edText.getText().toString();//제품명,원가,판매가,잔존가 check에 맞춰 입력
                if(check==0) {txView.setText("제품명을 입력하세요");}
                else if(check==1) {txView.setText("원가를 입력하세요");}
                else if(check==2) {txView.setText("판매가를 입력하세요");}
                else if(check==3) {txView.setText("잔존가를 입력하세요");}
                else if(check>3) {
                    Toast.makeText(getApplicationContext(), "가장 마지막페이지 입니다.", Toast.LENGTH_SHORT).show();
                    check=3;
                }
                edText.setText("");
            }
        });
    }
}
