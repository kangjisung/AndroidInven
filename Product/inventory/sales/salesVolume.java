package com.example.kangjisung.likeroom.inventory.sales;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.ClientDataBase;
import com.example.kangjisung.likeroom.inventory.InvenList.InvenAdapter;
import com.example.kangjisung.likeroom.inventory.InvenList.InvenListViewItem;
import com.example.kangjisung.likeroom.inventory.calc;
import com.example.kangjisung.likeroom.inventory.statistics.ChangeStat.InvenActivity;

import java.util.Calendar;

import static com.example.kangjisung.likeroom.SQLiteDatabaseControl.ClientDataBase.DBstring;

/**
 * Created by kangjisung on 2016-12-05.
 */
/////////////////////////////////////////판매량 보여주는 클레스
public class salesVolume extends AppCompatActivity {

    ListView Blistview ;
    InvenAdapter ivAdapter;
    Calendar cal;
    //calc c;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bread);

        Blistview = (ListView) findViewById(R.id.Breadlist);

        ivAdapter = new InvenAdapter();
        Blistview.setAdapter(ivAdapter);
        cal=Calendar.getInstance();
        //c = calc.getInstance();

        //제품 이름,날짜,판매량 불러오기
        new ClientDataBase("select `제품정보`.`이름`,`제품판매량`.`년`,`제품판매량`.`월`,`제품판매량`.`일`,`제품판매량`.`판매량` from `제품정보` join `제품판매량` on `제품정보`.`제품코드`= `제품판매량`.`제품코드` group by `제품판매량`.`제품코드` order by `년` desc, `월` desc,`일` desc;",1,5,getApplicationContext());
        int cnt=0;
        String date;
        while(true){
            if(DBstring[cnt]!=null) {
                date=""+DBstring[cnt+1]+"-"+DBstring[cnt+2]+"-"+DBstring[cnt+3]+"";
                ivAdapter.addItem(DBstring[cnt], date, DBstring[cnt + 4]);
                cnt += 5;
            }
            else if(DBstring[cnt]==null) break;
        }

        ////////////////////////////버튼
        final Intent mil = new Intent(this, InvenActivity.class); ///마일리지창
        Blistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                InvenListViewItem item = (InvenListViewItem) parent.getItemAtPosition(position) ;

                calc.RefreshClass(item.getBname());
                //String nameStr = item.getBname() ;
                //mil.putExtra("name", nameStr);

                //get TextView's Text.
                startActivity(mil);
                Toast.makeText(getApplicationContext(), "클릭.", Toast.LENGTH_SHORT).show();

                // TODO : use strText
            }
        }) ;



    }

}
