package com.example.kangjisung.likeroom.inventory;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.DatabaseHelper;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.LocalHostDatabaseManager;

import static android.R.attr.name;
/////////////////////////////////////////재품추가 클래스
public class ProductAdd extends AppCompatActivity {

    int check=0; //페이지
    EditText edText; //입력창
    TextView txView; //입력창 위에 설명
    Button before, after; //버튼
    ImageButton complete; //완료버튼
    DatabaseHelper databaseHelperTest;
    LocalHostDatabaseManager localHostDatabaseManager;
    String testDatabaseName = "ShopkeeperDatabase.db";
    SQLiteDatabase sqLiteDatabase;
    String array[]; //입력값 배열

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                databaseHelperTest = new DatabaseHelper(getApplicationContext(), testDatabaseName);
                localHostDatabaseManager = new LocalHostDatabaseManager(getApplicationContext(), getApplicationInfo().dataDir + "/databases/", testDatabaseName);

                sqLiteDatabase = localHostDatabaseManager.OpenSQLiteDatabase();
              //  sqLiteDatabase.execSQL("insert into `제품정보` (`고유회원등록번호`,`포인트`,`포인트갱신날짜`) values (\""+name+"\",\""+point+"\",(select date('now')));");

            }
        });
//////////////////////이전버튼
        before.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if(check==0) {txView.setText("제품명을 입력하세요");}
                else if(check==1) {txView.setText("원가를 입력하세요");}
                else if(check==2) {txView.setText("판매가를 입력하세요");}
                else if(check==3) {txView.setText("잔존가를 입력하세요");}
                edText.setText("");
                check-=1;
            }
        });
        //////////////////////////////다음버튼
        after.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                array[check]=edText.getText().toString();
                if(check==0) {txView.setText("제품명을 입력하세요");}
                else if(check==1) {txView.setText("원가를 입력하세요");}
                else if(check==2) {txView.setText("판매가를 입력하세요");}
                else if(check==3) {txView.setText("잔존가를 입력하세요");}
                edText.setText("");
                check+=1;
            }
        });
    }
}
