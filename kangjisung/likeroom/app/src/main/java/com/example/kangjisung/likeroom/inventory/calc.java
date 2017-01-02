package com.example.kangjisung.likeroom.inventory;

/**
 * Created by kangjisung on 2016-11-21.
 */

import android.content.Context;

import com.example.kangjisung.likeroom.MainActivity;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.ClientDataBase;
import com.example.kangjisung.likeroom.inventory.statistics.ChangeStat.InvenActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import static com.example.kangjisung.likeroom.SQLiteDatabaseControl.ClientDataBase.DBstring;
import static java.lang.Math.sqrt;

//calc 상위 클래스가 있고,하위에 1.예상판매량, 2.통계, 3.최적재고&예상매출 하위클래스 있다.
public class calc extends MainActivity {

    String name;  //제품이름
    Date curDate; //오늘날짜
    Date curDateTemp;  //날짜계산용
    DateFormat sdf;  //데이터 포멧용
    String strCurTime = "";  //날짜 스트링형
    Calendar cal;  //켈린더
    long lCurTime;  //시간계산용
    long lCurTimeTemp;  //시간계산용

    public static calc mInstance = null;

    public int Q, c, p, s, min, max; //Q최적재고량, c원가(DB), p판매가(DB), s잔존가(가치)(DB), min최소예상판매량(DB), max최대예상판매량(DB)
    public int tSeason, tWeek, tMonth, tDay, tYear; //tSeason총분기수,tWeek총주수 tmonth총월수, tDay총일수,tYear총년수
    public int[] DailySale, WeeklySale, MonthlySale, SeasonalSale, YearlySale; //DS일별판매량,WS주별판매평균, MS월별판매량 SS분기별판매량 YS일년판매량
    //-->이것도 db있으면, DS만 필요할 듯....
    public int[] dAvg, monAvg, sAvg, yAvg; //dAvg요일별판매량누적평균(dAvg[tDay%7] monAvg월별판매량누적평균 sAvg분기별판매량누적평균 yAvg연별판매량누적평균
    public double m, v, FM, FD; //m전체평균판매량(DB), v표준편차, FD예상평균판매량, FM최종평균
    public double[] FD_Array;
    public int[] monthDay = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    public int currentMonth;
    public int popUpResult;
    public int[] Recent100_Sale, Recent100_FD, Recent16_WeekSale;
    public boolean isChange = false;

    public calc() {
        long tmp;

        sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        curDate = new Date();
        curDateTemp=new Date();
        strCurTime = sdf.format(curDate);
        cal = Calendar.getInstance();
        currentMonth = cal.get(Calendar.MONTH);

        //////////////////////tDay,tWeek,tMonth,tSeason, tYear
        new ClientDataBase("select `등록일` from `매장`;", 1, 1, MainActivity.con); ///////////CurDateTemp 구하기
        int cnt = 0;
        String startDate;
        try {
            startDate=DBstring[cnt];
            curDateTemp = sdf.parse(startDate);  //불러온 스트링값 데이터 형으로 변환
        } catch (ParseException e1) {
            e1.printStackTrace();
        }

        lCurTime = curDate.getTime();
        lCurTimeTemp = curDateTemp.getTime();
        tmp = lCurTime - lCurTimeTemp;
        tDay = (int) (tmp / (24 * 60 * 60 * 1000));
        tWeek = (tDay + 6) / 7;
        tMonth = (int) (tDay / 365.254) * 12;
        tSeason = (tMonth + 2) / 3;
        tYear = (int) ((tDay + 364) / 365.254);

        //////////////////////////////////////////////////////////////////////////////
        /////////////////////////////TEST CODE////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////

       /* p=300;
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
        for(int i=0; i<12; i++) monAvg[i]=random.nextInt(100);*/

        //////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////
    }

    public static calc getInstance() {
        if (calc.mInstance == null) {
            calc.mInstance = new calc();
            return calc.mInstance;
        } else return calc.mInstance;
    }

    public void setName(String nam) {
        name = nam;
    }

    public void InitCalc() {
        int cnt;
        new ClientDataBase("select `판매량` from `제품판매량` where `제품코드`=(select `제품코드` from `제품정보` where `이름`=\"" + name + "\");", 1, 1, MainActivity.con);
        cnt = 0;
        while (true) {
            if (DBstring[cnt] != null) {
                popUpResult = Integer.parseInt(DBstring[cnt]);
                cnt++;
            } else if (DBstring[cnt] == null) break;
        }

        ////////////////////////최적재고량

        new ClientDataBase("select `최적재고량` from `최적재고량` where `날짜`=\"" + strCurTime + "\"and `제품코드` =(select `제품코드` from `제품정보` where `이름`=\"" + name + "\");", 1, 1, MainActivity.con);
        cnt = 0;
        while (true) {
            if (DBstring[cnt] != null) {
                Q = Integer.parseInt(DBstring[cnt]);
                cnt++;
            } else if (DBstring[cnt] == null) break;
        }

        ////////////////////////원가,판매가,잔존가
        new ClientDataBase("select `원가`,`판매가`,`잔존가` from `제품정보` where `이름`=\"" + name + "\";", 1, 3, MainActivity.con);
        cnt = 0;
        while (true) {
            if (DBstring[cnt] != null) {
                c = Integer.parseInt(DBstring[cnt]);
                p = Integer.parseInt(DBstring[cnt + 1]);
                s = Integer.parseInt(DBstring[cnt + 2]);
                cnt += 3;
            } else if (DBstring[cnt] == null) break;
        }

        ////////////////////////최소예상판매량, 최대예상판매량
        new ClientDataBase("select Min(`예상판매량`),Max(`예상판매량`) from `제품판매량` where `날짜`>\"" + cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) - 1) + "-" + cal.get(Calendar.DATE) + "\";", 1, 2, MainActivity.con);
        cnt = 0;
        while (true) {
            if (DBstring[cnt] != null) {
                min = Integer.parseInt(DBstring[cnt]);
                max = Integer.parseInt(DBstring[cnt + 1]);
                cnt += 2;
            } else if (DBstring[cnt] == null) break;
        }
        ///////////////////////////dAvg입력,monAvg입력
        new ClientDataBase("select `날짜`,`판매량` from `제품판매량`;", 1, 2, MainActivity.con);
        String strWeek = null; //날짜 스트링형
        Date DateWeek = null;  //날짜 데이터 형
        int WeekdateAvg[] = new int[8];  //요일 판매량 평균계산용
        int MonthdateAvg[] = new int[13];  //달 판매량 평균계산용
        sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);     ///simpleDateformat  (E는 요일,M은 달)   밑에 방법말고 해도됨.
        dAvg = new int[7];
        monAvg = new int[12];
        cnt = 0;
        while (true) {
            if (DBstring[cnt] != null) {
                strWeek = DBstring[cnt];
                try {
                    DateWeek = sdf.parse(strWeek);  //불러온 스트링값 데이터 형으로 변환
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                cal.setTime(DateWeek);  //불러온 날짜값으로 캘린더 변환
                int nMonth = cal.get(Calendar.MONTH);
                if (nMonth == 1) MonthdateAvg[nMonth] += Integer.parseInt(DBstring[cnt + 1]);
                else if (nMonth == 2) MonthdateAvg[nMonth] += Integer.parseInt(DBstring[cnt + 1]);
                else if (nMonth == 3) MonthdateAvg[nMonth] += Integer.parseInt(DBstring[cnt + 1]);
                else if (nMonth == 4) MonthdateAvg[nMonth] += Integer.parseInt(DBstring[cnt + 1]);
                else if (nMonth == 5) MonthdateAvg[nMonth] += Integer.parseInt(DBstring[cnt + 1]);
                else if (nMonth == 6) MonthdateAvg[nMonth] += Integer.parseInt(DBstring[cnt + 1]);
                else if (nMonth == 7) MonthdateAvg[nMonth] += Integer.parseInt(DBstring[cnt + 1]);
                else if (nMonth == 8) MonthdateAvg[nMonth] += Integer.parseInt(DBstring[cnt + 1]);
                else if (nMonth == 9) MonthdateAvg[nMonth] += Integer.parseInt(DBstring[cnt + 1]);
                else if (nMonth == 10) MonthdateAvg[nMonth] += Integer.parseInt(DBstring[cnt + 1]);
                else if (nMonth == 11) MonthdateAvg[nMonth] += Integer.parseInt(DBstring[cnt + 1]);
                else if (nMonth == 12) MonthdateAvg[nMonth] += Integer.parseInt(DBstring[cnt + 1]);
                int nWeek = cal.get(Calendar.DAY_OF_WEEK);
                ///불러온 날짜 값으로 요일구분후 판매량 입력
                if (nWeek == 1) WeekdateAvg[nWeek] += Integer.parseInt(DBstring[cnt + 1]);
                else if (nWeek == 2) WeekdateAvg[nWeek] += Integer.parseInt(DBstring[cnt + 1]);
                else if (nWeek == 3) WeekdateAvg[nWeek] += Integer.parseInt(DBstring[cnt + 1]);
                else if (nWeek == 4) WeekdateAvg[nWeek] += Integer.parseInt(DBstring[cnt + 1]);
                else if (nWeek == 5) WeekdateAvg[nWeek] += Integer.parseInt(DBstring[cnt + 1]);
                else if (nWeek == 6) WeekdateAvg[nWeek] += Integer.parseInt(DBstring[cnt + 1]);
                else if (nWeek == 7) WeekdateAvg[nWeek] += Integer.parseInt(DBstring[cnt + 1]);
                cnt += 2;
            } else if (DBstring[cnt] == null) {
                //판매량 평균낸후 dAvg에 입력
                cnt = (cnt / 2) - 1;
                for (int i = 1; i <= 7; i++) {
                    if (WeekdateAvg[i] != 0)
                        dAvg[i] = (int) (WeekdateAvg[i] / cnt);
                }
            }
            for (int i = 1; i <= 12; i++) {
                if (MonthdateAvg[i] != 0)
                    monAvg[i] = (int) (MonthdateAvg[i] / cnt);
            }
            break;
        }
        /////////////////////////////////////////////////////////////
        dailyUpdate();
        weeklyUpdate();
        monthlyUpdate();
        seasonalUpdate();
        yearlyUpdate();
    }

    //판매량 입력하고나서
    void dailyUpdate() {
        DailySale=new int[tDay];
        FD_Array=new double[tDay];
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
        /*new ClientDataBase("select avg(`판매량`) from `제품판매량`;",1,1,getApplicationContext());
        int cnt=0;
        while(true) {
            if (DBstring[cnt] != null) {
                m=Integer.parseInt(DBstring[cnt]);
                cnt+=1;
            }
            else if(DBstring[cnt]==null) break;
        }
        new ClientDataBase("select `판매량` from `제품판매량` where `날짜`="+(cal.get(Calendar.DATE)-100)+" ;",1,1,getApplicationContext());
        cnt=0;
        while(true) {
            if (DBstring[cnt] != null) {
                Recent100_Sale[cnt]=Integer.parseInt(DBstring[cnt]);
                cnt+=1;
            }
            else if(DBstring[cnt]==null) break;
        }
        new ClientDataBase("select `예상판매량` from `제품판매량` where `날짜`="+(cal.get(Calendar.DATE)-100)+" ;",1,1,getApplicationContext());
        cnt=0;
        while(true) {
            if (DBstring[cnt] != null) {
                Recent100_FD[cnt]=Integer.parseInt(DBstring[cnt]);
                cnt+=1;
            }
            else if(DBstring[cnt]==null) break;
        }*/

        //최적재고량 산출-->main화면의 숫자 바꿔준다.
    }

    //요일별평균판매량(매주 한번 실행) update 일요일 자정
    void weeklyUpdate() {
        for (int k = tDay - 6; k <= tDay; k++) {
            WeeklySale[tWeek] += DailySale[k];
        }
        dAvg[tWeek] = (dAvg[tWeek - 1] * (tWeek - 1) + WeeklySale[tWeek]) / tWeek;
    }

    //월별평균판매량(매월 한번 실행) update
    void monthlyUpdate() {
        for (int k = tDay - monthDay[currentMonth] + 1; k <= tDay; k++) {
            MonthlySale[tMonth] += DailySale[k];
        }
        monAvg[tMonth] = (monAvg[tMonth - 1] * (tMonth - 1) + MonthlySale[tMonth]) / tMonth;
    }

    void seasonalUpdate() {
        //분기별평균판매량(매시즌(3개월)말에 한번 실행) update
        sAvg[tSeason] = (sAvg[tSeason - 1] * (tSeason - 1) + MonthlySale[tMonth] + MonthlySale[tMonth - 1] + MonthlySale[tMonth - 2]) / tSeason;
    }

    void yearlyUpdate() {
        //연별평균판매량(매년 1월1일 0시 한번 실행) update
        yAvg[tYear] = (yAvg[tYear - 1] * (tYear - 1) + SeasonalSale[tSeason] + SeasonalSale[tSeason - 1] + SeasonalSale[tSeason - 2] + SeasonalSale[tSeason - 3]) / tYear;
    }

    /////////////////////////////////////////////////////최적재고량 계산 (메인화면과 3.최적재고&예상매출 화면에 필요)
    public void calcQ(int productCode) {
        v = (max - min) / 6; //표준편차
        FM = (min + max + (4 * FD)) / 6; //최종평균계산
        FD = (int)(calcT().predict(tDay)*calcD(tDay,DailySale,m)*calcMonth(tMonth,monAvg,m))+1; //예상판매량=추세*요일지수*월별지수
        Q = (int) (FM + ((v / 2) * (sqrt((p - c) / (c - s)) - sqrt((c - s) / (p - c))))) + 1; //최적재고량 계산
        new ClientDataBase("insert into `최적재고량` (`제품코드`,`최적재고량`,`날짜`) values ("+productCode+","+Q+",(select date('now')));",2,0,MainActivity.con);
    }

    public int calcQ2(){
        v = (max - min) / 6; //표준편차
        FM = (min + max + (4 * FD)) / 6; //최종평균계산
        return Q=(int) (FM + ((v / 2) * (sqrt((p - c) / (c - s)) - sqrt((c - s) / (p - c))))) + 1;
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
    public int calcProfit(int Q){ //Q개만큼 구비시 예상 이익 --> graph3에 표시
        return -1*(int)(-(p-s)*FM+(c-s)*Q+(p-s)*((Math.sqrt(v*v+(Q-FM)*(Q-FM))-(Q-FM))/2));
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