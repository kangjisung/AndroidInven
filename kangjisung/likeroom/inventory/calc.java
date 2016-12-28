package com.example.kangjisung.likeroom.inventory;

/**
 * Created by kangjisung on 2016-11-21.
 */

import android.database.Cursor;

import com.example.kangjisung.likeroom.MainActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import static java.lang.Math.sqrt;

//calc 상위 클래스가 있고,하위에 1.예상판매량, 2.통계, 3.최적재고&예상매출 하위클래스 있다.
public class calc extends MainActivity {
    //DatabaseHelper databaseHelperTest;
    //LocalHostDatabaseManager localHostDatabaseManager;
    //String testDatabaseName = "ShopkeeperDatabase.db";
    //SQLiteDatabase sqLiteDatabase;

    String name;  //제품이름

    Date curDate;
    Date curDateTemp;
    DateFormat sdf;
    String strCurTime="";
    Calendar cal;
    long lCurTime;
    long lCurTimeTemp;

    public static calc mInstance=null;

    public int Q, c, p, s, min, max; //Q최적재고량, c원가(DB), p판매가(DB), s잔존가(가치)(DB), min최소예상판매량(DB), max최대예상판매량(DB)
    public int tSeason, tWeek, tMonth, tDay, tYear; //tSeason총분기수,tWeek총주수 tmonth총월수, tDay총일수,tYear총년수
    public int profit; // 판매이익
    public int[] DailySale, WeeklySale, MonthlySale, SeasonalSale, YearlySale; //DS일별판매량,WS주별판매량 MS월별판매량 SS분기별판매량 YS일년판매량
    //-->이것도 db있으면, DS만 필요할 듯....
    public int[] dAvg, monAvg, sAvg, yAvg; //dAvg요일별판매량누적평균(dAvg[tDay%7] monAvg월별판매량누적평균 sAvg분기별판매량누적평균 yAvg연별판매량누적평균
    public double m, v, w, FM, FD; //m전체평균판매량(DB), v표준편차, FD예상평균판매량, FM최종평균
    public double[] FD_Array;
    public int[] monthDay = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    public int popUpResult=20; //입력받은 실제 판매량
    public int currentMonth=12; //
    public int[] Recent100_Sale,Recent100_FD,Recent16_WeekSale;

    public calc(){
        long tmp;

        sdf=new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        curDate=new Date();
        strCurTime = sdf.format(curDate);
        cal = Calendar.getInstance ( );
        cal.setTime(curDate);


        //databaseHelperTest = new DatabaseHelper(getApplicationContext(), testDatabaseName);
        //localHostDatabaseManager = new LocalHostDatabaseManager(getApplicationContext(), getApplicationInfo().dataDir + "/databases/", testDatabaseName);

        // sqLiteDatabase = localHostDatabaseManager.OpenSQLiteDatabase();

        ////////////////////////최적재고량
        /*Cursor cursor1=sqLiteDatabase.rawQuery("select `최적재고량` from `최적재고량` where `날짜`="+(cal.get(Calendar.DAY_OF_MONTH)-1)+" and `제품코드`=(select `제품코드` from `제품정보` where `이름`=\"\"+name+\"\";) ;",null);
        while(cursor1.moveToNext()){
            Q=cursor1.getInt(0);
        }
        ////////////////////////원가,판매가,잔존가
        cursor1=sqLiteDatabase.rawQuery("select `원가`,`판매가`,`잔존가` from `제품정보` where `이름`=\""+name+"\";",null);
        while(cursor1.moveToNext()){
            c=cursor1.getInt(0);
            p=cursor1.getInt(1);
            s=cursor1.getInt(2);
        }

        ////////////////////////최소예상판매량, 최대예상판매량
        cursor1=sqLiteDatabase.rawQuery("select Min(`예상판매량`),Max(`예상판매량`) from `제품판매량` where `날짜`>"+(cal.get(Calendar.MONTH)-3)+";",null);
        while(cursor1.moveToNext()){
            min=cursor1.getInt(0);
            max=cursor1.getInt(1);
        }*/
        //////////////////////tDay,tWeek,tMonth,tSeason, tYear
        /*Cursor cursor1=sqLiteDatabase.rawQuery("SELECT `등록일` from `매장`;",null);
        while(cursor1.moveToNext()){
            strCurTime= cursor1.getString(0);
        }*/
        try {
            curDateTemp=sdf.parse(strCurTime);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        lCurTime = curDate.getTime();
        lCurTimeTemp = curDateTemp.getTime();
        tmp=lCurTime-lCurTimeTemp;
        tDay=(int)(tmp/(24*60*60*1000));
        tWeek= (int) ((tDay+6)/7);

        //////////////////////////////////////////////////////////////////////////////
        /////////////////////////////TEST CODE////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////

        p=300;
        c=100;
        s=50;
        min=44;
        max=111;
        tWeek=16;
        Q=88;
        FD=99;
        Recent100_Sale=new int[14];
        Recent100_FD=new int[14];
        Recent16_WeekSale=new int[16];
        WeeklySale=new int[16];
        dAvg=new int[7];
        monAvg=new int[12];
        Random random=new Random();
        for(int i=0; i<14; i++) Recent100_Sale[i]=random.nextInt(100);
        for(int i=0; i<14; i++) Recent100_FD[i]=random.nextInt(100);
        for(int i=0; i<16; i++) WeeklySale[i]=random.nextInt(100);
        for(int i=0; i<16; i++) Recent16_WeekSale[i]=WeeklySale[i];
        for(int i=0; i<7; i++) dAvg[i]=random.nextInt(100);
        for(int i=0; i<12; i++) monAvg[i]=random.nextInt(100);

        //////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////

        //tMonth=cursor1.getInt(1);
        //tSeason= (int)((tMonth+2)/3);
        //tYear= (int)((tDay+364)/365.254);
    }

    //int mode = tDay % 7;// 7일주일

    /*protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }*/

    public static calc getInstance(){
        if(calc.mInstance==null){
            calc.mInstance=new calc();
            return calc.mInstance;
        }
        else return calc.mInstance;
    }

    //매일 자정에 실행
    void dailyUpdate() {
        //tDay++;
        DailySale[tDay] = popUpResult;//메인에서 입력받은 판매량
        FD_Array[tDay] = FD;
        int[] Recent100_Sale;
        if(tDay<100){
            Recent100_Sale=new int[tDay];
            Recent100_FD=new int[tDay];
            for(int i=0; i<tDay; i++) Recent100_Sale[i]=DailySale[i];
            for(int i=0; i<tDay; i++) Recent100_FD[i]=DailySale[i];
        }
        else{
            Recent100_Sale=new int[100];
            Recent100_FD=new int[100];
            for(int i=0; i<100; i++) Recent100_Sale[i]=DailySale[tDay-100+i];
            for(int i=0; i<100; i++) Recent100_FD[i]=DailySale[tDay-100+i];
        }
        m = (m * (tDay - 1) + DailySale[tDay]) / DailySale[tDay];
        //최적재고량 산출-->main화면의 숫자 바꿔준다.
    }

    //요일별평균판매량(매주 한번 실행) update 일요일 자정
    void weeklyUpdate() {

        //tWeek++;
        for (int k = tDay - 6; k <= tDay; k++) {
            WeeklySale[tWeek] += DailySale[k];
        }
        dAvg[tWeek] = (dAvg[tWeek - 1] * (tWeek - 1) + WeeklySale[tWeek]) / tWeek;
    }

    //월별평균판매량(매월 한번 실행) update
    void monthlyUpdate() {
        //tMonth++;
        for (int k = tDay - monthDay[currentMonth] + 1; k <= tDay; k++) {
            MonthlySale[tMonth] += DailySale[k];
        }
        monAvg[tMonth] = (monAvg[tMonth - 1] * (tMonth - 1) + MonthlySale[tMonth]) / tMonth;
    }

    void seasonalUpdate() {
        //tSeason++;
        //분기별평균판매량(매시즌(3개월)말에 한번 실행) update
        sAvg[tSeason] = (sAvg[tSeason - 1] * (tSeason - 1) + MonthlySale[tMonth] + MonthlySale[tMonth - 1] + MonthlySale[tMonth - 2]) / tSeason;
    }

    void yearlyUpdate() {
        //tYear++;
        //연별평균판매량(매년 1월1일 0시 한번 실행) update
        yAvg[tYear] = (yAvg[tYear - 1] * (tYear - 1) + SeasonalSale[tSeason] + SeasonalSale[tSeason - 1] + SeasonalSale[tSeason - 2] + SeasonalSale[tSeason - 3]) / tYear;
    }

    /////////////////////////////////////////////////////최적재고량 계산 (메인화면과 3.최적재고&예상매출 화면에 필요)
    void calcQ(int productCode) {
        v = (max - min) / 6; //표준편차
        FM = (min + max + (4 * FD)) / 6; //최종평균계산
        FD = (int)(calcT().predict(tDay)*calcD(tDay,DailySale,m)*calcMonth(tMonth,monAvg,m))+1; //예상판매량=추세*요일지수*월별지수
        Q = (int) (FM + ((v / 2) * (sqrt((p - c) / (c - s)) - sqrt((c - s) / (p - c))))) + 1; //최적재고량 계산
        //sqLiteDatabase.execSQL("insert into `최적재고량` (`제품코드`,`최적재고량`,`날짜`) values ("+productCode+","+Q+",(select date('now')));");
    }

    ////////////////////////////////////////////////////////////주별평균으로 추세계산
    public LinearRegression calcT() {
        int [] x = new int[tWeek];

        if(tWeek<16){
            Recent16_WeekSale=new int[tWeek];
            for(int i=0; i<tWeek; i++) Recent16_WeekSale[i]=WeeklySale[i];
        }
        else{
            Recent16_WeekSale=new int[16];
            for(int i=0; i<16; i++) Recent16_WeekSale[i]=WeeklySale[i];
        }
        for(int i=0; i<16; i++) x[i]=i;

        LinearRegression LR = new LinearRegression(x,Recent16_WeekSale);
        //return LR.predict(tDay);
        return LR;
    }
    ////////////////요일지수
    double calcD(int tDay,int[] DailySale, double m){
        return dAvg[tDay%7]/m;
    }
    ///////////////////////////////////////월별지수: 월판매량/전체평균(m)
    double calcMonth(int tMonth, int[] monAvg, double m) {
        return monAvg[tMonth%12]/m;
    }

    //////////////////////////////////////구비재고량을 입력받아 그에따른 예상이익을 출력
    double calcProfit(int Q, int p, int c, int s, int FM, int v){ //Q개만큼 구비시 예상 이익 --> graph3에 표시
        return (int)(-(p-s)*FM+(c-s)*Q+(p-s)*((Math.sqrt(v*v+(Q-FM)*(Q-FM))-(Q-FM))/2));
    }

    public class LinearRegression {
        private final double intercept, slope;
        private final double r2;
        private final double svar0, svar1;

        /**
         * Performs a linear regression on the data points {@code (y[i], x[i])}.
         *
         * @param  x the values of the predictor variable
         * @param  y the corresponding values of the response variable
         * @throws IllegalArgumentException if the lengths of the two arrays are not equal
         */
        public LinearRegression(int[] x, int[] y) {
            if (x.length != y.length) {
                throw new IllegalArgumentException("array lengths are not equal");
            }
            int n = x.length;

            // first pass
            double sumx = 0.0, sumy = 0.0, sumx2 = 0.0;
            for (int i = 0; i < n; i++) {
                sumx  += x[i];
                sumx2 += x[i]*x[i];
                sumy  += y[i];
            }
            double xbar = sumx / n;
            double ybar = sumy / n;

            // second pass: compute summary statistics
            double xxbar = 0.0, yybar = 0.0, xybar = 0.0;
            for (int i = 0; i < n; i++) {
                xxbar += (x[i] - xbar) * (x[i] - xbar);
                yybar += (y[i] - ybar) * (y[i] - ybar);
                xybar += (x[i] - xbar) * (y[i] - ybar);
            }
            slope  = xybar / xxbar;
            intercept = ybar - slope * xbar;

            // more statistical analysis
            double rss = 0.0;      // residual sum of squares
            double ssr = 0.0;      // regression sum of squares
            for (int i = 0; i < n; i++) {
                double fit = slope*x[i] + intercept;
                rss += (fit - y[i]) * (fit - y[i]);
                ssr += (fit - ybar) * (fit - ybar);
            }

            int degreesOfFreedom = n-2;
            r2    = ssr / yybar;
            double svar  = rss / degreesOfFreedom;
            svar1 = svar / xxbar;
            svar0 = svar/n + xbar*xbar*svar1;
        }

        /**
         * Returns the <em>y</em>-intercept &alpha; of the best of the best-fit line <em>y</em> = &alpha; + &beta; <em>x</em>.
         *
         * @return the <em>y</em>-intercept &alpha; of the best-fit line <em>y = &alpha; + &beta; x</em>
         */
        public double intercept() {
            return intercept;
        }

        /**
         * Returns the slope &beta; of the best of the best-fit line <em>y</em> = &alpha; + &beta; <em>x</em>.
         *
         * @return the slope &beta; of the best-fit line <em>y</em> = &alpha; + &beta; <em>x</em>
         */
        public double slope() {
            return slope;
        }

        /**
         * Returns the expected response {@code y} given the value of the predictor
         * variable {@code x}.
         *
         * @param  x the value of the predictor variable
         * @return the expected response {@code y} given the value of the predictor
         *         variable {@code x}
         */
        public double predict(int x) {
            return slope*x + intercept;
        }
    }
}