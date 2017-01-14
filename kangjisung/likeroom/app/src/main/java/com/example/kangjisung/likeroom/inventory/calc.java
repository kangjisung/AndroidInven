package com.example.kangjisung.likeroom.inventory;

/*
 * Created by kangjisung on 2016-11-21.
 */

import android.content.Context;
import android.content.Intent;

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

public class calc extends MainActivity {

    public String name;  //제품이름
    Date curDate; //오늘날짜
    Date curDateTemp;  //날짜계산용
    DateFormat sdf;  //데이터 포멧용
    String strCurTime = "";  //날짜 스트링형
    public Calendar cal;  //켈린더
    long lCurTime;  //시간계산용
    long lCurTimeTemp;  //시간계산용

    public static calc mInstance = null;

    //1.db only ----->기존에 있던 제품 정보(순수하게 db로부터 받아오기만 하면 됨)
    public int c, p, s; //c원가(DB), p판매가(DB), s잔존가(가치)(DB)
    //2.db + Android ----->db로 부터 받아온 데이터를 안드로이드에서 가공해 얻은 정보
    public int tWeek, tMonth, tDay; // tDay현재까지 총일수, tWeek총주수, tmonth총월수
    public int curDay,curMonth;//현재 요일, 현재 월(이건 db 개입x// 순수안드로이드)
    public double [] dAvg, monAvg; //dAvg요일별판매량총평균, monAvg월별판매량총평균
    public double m;//m전체평균판매량
    //1,2정보만으로 계산한 Q(최적재고량)값을 Q목록에 띄운다.이를 수정하고 싶은 User가 수정버튼을 눌러 Q값 결정에 사용되는 값(FD,min,max)를 변경->3.
    //3.db + Android + User -----> 모든 정보를 다 합쳐 얻는 최종결론
    public int FD, min, max; //FD예측판매량, min, max최소최대예상판매량 (min max: db의 한달판매량의 min,max가 default+ graph1에서 사용자가 이 값 변경가능)
    public double v, FM; //v표준편차, FM최종평균


    public int Q;//Q최적재고량


    //4.그래프를 그리기 위해 생성해야하는 정보
    public int[] Recent100_Sale, Recent100_FD;//최근 100일간의 판매량과 예측판매량(User입력한것 only)(Graph1-(2))
    public int[] Recent16_WeekSale; //최근 16주의 각 주 판매량 평균(Graph2)

    public boolean isChange = false;

    public calc(String name) {
        int cnt;
        this.name=name;

        graphdata();

        //1. db 불러오기
//        int c, p, s; //c원가(DB), p판매가(DB), s잔존가(가치)(DB) db에서 불러와 저장하는 코드필요!
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
        //2. db 불러오기 + Android 계산
//        int tWeek, tMonth, tDay; // tDay현재까지 총일수, tWeek총주수, tmonth총월수
//        int curDay, curMonth;//현재 요일, 현재 월(이건 db 개입x// 순수안드로이드)
//        double[] dAvg, monAvg; //dAvg요일별판매량총평균, monAvg월별판매량총평균
//        double m;//m전체평균판매량

        /////int tWeek, tMonth, tDay; //매장등록 이후 지금까지 총 일수,주수,월수 구하기
        sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        long tmp;
        curDate = new Date();//curDate는 현재날짜로 자동설정 되는 것임?
        curDateTemp = new Date();
        /////////CurDateTemp
        new ClientDataBase("select `등록일` from `매장`;", 1, 1, MainActivity.con);
        cnt = 0;
        String startDate;
        try {
            startDate = DBstring[cnt];
            curDateTemp = sdf.parse(startDate);  //불러온 스트링값 데이터 형으로 변환
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        lCurTime = curDate.getTime();
        lCurTimeTemp = curDateTemp.getTime();
        tmp = lCurTime - lCurTimeTemp;
        tDay = (int) (tmp / (24 * 60 * 60 * 1000));
        tWeek = (tDay + 6) / 7;
        tMonth = (int) ((tDay / 365.254) * 12);

        //////curDay,curMonth 구하기
        cal = Calendar.getInstance();
        curMonth = cal.get(Calendar.MONTH)+1; //안드로이드로부터 현재 월 받음
        curDay = cal.get(Calendar.DAY_OF_WEEK); //안드로이드로부터 현재 요일 받는 코드 필요
        strCurTime = sdf.format(curDate);

        dAvg = new double[8];
        monAvg = new double[13];

                //////dAvg[7], monAvg[12] 구하기 //dAvg요일별판매량총평균, monAvg월별판매량총평균 구하기
        //select AVG(`판매량`) from `제품판매량` where `
        new ClientDataBase("select avg(`판매량`),`요일` from `제품판매량` group by `요일`",1,2, MainActivity.con);
        cnt = 0;
        while (true) {
            if (DBstring[cnt] != null) {
                dAvg[Integer.parseInt(DBstring[cnt+1])]=Double.parseDouble(DBstring[cnt]);
                cnt += 2;
            } else if (DBstring[cnt] == null) break;
        }
        new ClientDataBase("select avg(`판매량`),`월` from `제품판매량` group by `월`",1,2, MainActivity.con);
        cnt = 0;
        while (true) {
            if (DBstring[cnt] != null) {
                monAvg[Integer.parseInt(DBstring[cnt+1])]=Double.parseDouble(DBstring[cnt]);
                cnt += 2;
            } else if (DBstring[cnt] == null) break;
        }

        //case문을 이용해 db에서 해당 요일에 속하는 날의 판매량을 불러와 평균내서 dAvg()에 저장(처음에 db에 저장할 때부터 0~6 숫자로 요일을 저장하는 것도 좋을 듯
        //for(d=0;d<7;d++){case (db-판매량의 요일==d)-->불러와서 그 값들의 평균을 dAvg[d]에 저장}
        //monAvg도 마찬가지for(m=0;m<7;m++){case (db-판매량의 월==ㅡm-->불러와서 그 값들의 평균을 monAvg[m]에 저장}

        //////m 구하기 //m전체평균판매량
        new ClientDataBase("select avg(`판매량`) from `제품판매량`",1,1, MainActivity.con);
        cnt = 0;
        m=Double.parseDouble(DBstring[cnt]);

        //db의 판매량 column의 총평균을 m에 저장

        ////////////////////////////////////////////최소예상판매량, 최대예상판매량
        //new ClientDataBase("select Min(`판매량`),Max(`판매량`) from `제품판매량` where `년`>\"" + cal.get(Calendar.YEAR) + "\" and `월`>\"" + cal.get(Calendar.MONTH) + "\" and `일`>\"" + cal.get(Calendar.DATE) + "\";", 1, 2, MainActivity.con);
        new ClientDataBase("select Min(`판매량`),Max(`판매량`) from `제품판매량` where `년`>=2016 and `월`>9 and `일`>1;", 1, 2, MainActivity.con);
        cnt = 0;
        while (true) {
            if (DBstring[cnt] != null) {
                min = Integer.parseInt(DBstring[cnt]);
                max = Integer.parseInt(DBstring[cnt + 1]);
                cnt += 2;
            } else if (DBstring[cnt] == null) break;
        }

        v = (max - min) / 6; //표준편차
        FD = (int)(calcT().predict(tDay)*(dAvg[curDay]/m)*(monAvg[curMonth]/m))+1; //예상판매량=추세*요일지수*월별지수
        FM = (min + max + (4 * FD)) / 6; //최종평균계산
    }

    public static calc getInstance() {
        if (calc.mInstance!=null) return calc.mInstance;
        return null;
    }

    public static calc RefreshClass(String name){
        calc.mInstance=new calc(name);
        return calc.mInstance;
    }

    ////////////////////매일 데이터값 받아오기
    public void graphdata() {
        Recent100_Sale=new int[101];
        Recent100_FD=new int[101];
        Recent16_WeekSale = new int[16];

        //최근 사용자가 "FD를 변경한"날짜에 해당하는 FD와 Sale만 불러와야
        new ClientDataBase("select `판매량`,`예상판매량` from `제품판매량` where `제품코드`=(select `제품코드` from `제품정보` where `이름`=\""+name+"\") and `변경유무`=\"true\" order by `년` desc, `월` desc,`일` desc limit 100",1,2,MainActivity.con);
        int cnt = 0;
        while (true) {
            if (DBstring[cnt] != null) {
                Recent100_Sale[(cnt+2)/2]=Integer.parseInt(DBstring[cnt]);
                Recent100_FD[(cnt+2)/2]=Integer.parseInt(DBstring[cnt+1]);
                cnt+=2;
            } else if (DBstring[cnt] == null) break;
        }
        if(cnt<101) Recent100_Sale[cnt]=-1;
        if(cnt<101) Recent100_FD[cnt]=-1;
        /////////////////////////////////////////////최근 16주차 평균
        new ClientDataBase("select avg(`판매량`) from `제품판매량` where `제품코드`=(select `제품코드` from `제품정보` where `이름`=\""+name+"\") and `몇주차`>(select max(`몇주차`) from `제품판매량`)-16 group by `몇주차`",1,1,MainActivity.con);
        cnt = 0;
        while (true) {
            if (DBstring[cnt] != null) {
                Recent16_WeekSale[cnt]=(int)Double.parseDouble(DBstring[cnt]);
                cnt++;
            } else if (DBstring[cnt] == null) break;
        }
        if(cnt<16) Recent16_WeekSale[cnt]=-1;


        //최적재고량 산출-->main화면의 숫자 바꿔준다.
    }
    /////////////////////////////////////////////////////최적재고량 계산
    //////////////////////////////////////판매량 넣을때 실행
    public void calcQ() {
        cal = Calendar.getInstance();
        Q = (int) (FM + ((v / 2) * (sqrt((p - c) / (c - s)) - sqrt((c - s) / (p - c))))) + 1; //최적재고량 계산
        new ClientDataBase("insert into `최적재고량` (`제품코드`,`최적재고량`,`날짜`) values ((select `제품코드` from `제품정보` where `이름`=\""+name+"\"),"+Q+",\"" + cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) +1) + "-" + (cal.get(Calendar.DATE)+1) + "\");",2,0,MainActivity.con);
    }
    ////////////////////////////////////////////그래프용 calc2
    public int calcQ2(){
        v = (max - min) / 6; //표준편차
        FM = (min + max + (4 * FD)) / 6; //최종평균계산
        return Q=(int) (FM + ((v / 2) * (sqrt((p - c) / (c - s)) - sqrt((c - s) / (p - c))))) + 1;
    }

    ////////////////////////////////////////////////////////////주별평균으로 추세계산
    public LinearRegression calcT() {
        int [] x = new int[Recent16_WeekSale.length];

        for(int i=0; i<Recent16_WeekSale.length; i++) x[i]=i;

        LinearRegression LR = new LinearRegression(x,Recent16_WeekSale);
        return LR;
    }

    //////////////////////////////////////구비재고량을 입력받아 그에따른 예상이익을 출력
    public int calcProfit(int Q){ //Q개만큼 구비시 예상 이익 --> graph3에 표시
        return -1*(int)(-(p-s)*FM+(c-s)*Q+(p-s)*((Math.sqrt(v*v+(Q-FM)*(Q-FM))-(Q-FM))/2));
    }
/////////////////FD업데이트(예상판매량)
    public void updateFD(){
        new ClientDataBase("select `예상판매량` from `제품판매량`where `년`=\""+cal.get(Calendar.YEAR)+"\" and `월`=\""+(cal.get(Calendar.MONTH)+1)+"\" and `일`=\""+cal.get(Calendar.DATE)+"\"",1,1,MainActivity.con);
        if(DBstring[0]!=null)
            new ClientDataBase("update `제품판매량` set `예상판매량`=\""+FD+"\" where `년`=\""+cal.get(Calendar.YEAR)+"\" and `월`=\""+(cal.get(Calendar.MONTH)+1)+"\" and `일`=\""+cal.get(Calendar.DATE)+"\"",3,0,MainActivity.con);
        else
            new ClientDataBase("insert into `제품판매량` (`제품코드`,`예상판매량`,`년`,`월`,`일`) values ((select `제품코드` from `제품정보` where `이름`=\""+name+"\"),"+FD+",\"" + cal.get(Calendar.YEAR) + "\",\"" + (cal.get(Calendar.MONTH) +1) + "\",\"" + (cal.get(Calendar.DATE)+1) + "\");",2,0,MainActivity.con);
}
    ///////////////////Q업데이트(최적재고량)
    public void updateQ(){
        new ClientDataBase("select `최적재고량` from `최적재고량` where `년`=\""+cal.get(Calendar.YEAR)+"\" and `월`=\""+(cal.get(Calendar.MONTH)+1)+"\" and `일`=\""+cal.get(Calendar.DATE)+"\"",1,1,MainActivity.con);
        if(DBstring[0]!=null)
            new ClientDataBase("update `최적재고량` set `최적재고량`=\""+Q+"\" where `년`=\""+cal.get(Calendar.YEAR)+"\" and `월`=\""+(cal.get(Calendar.MONTH)+1)+"\" and `일`=\""+cal.get(Calendar.DATE)+"\"",3,0,MainActivity.con);
        else
            new ClientDataBase("insert into `최적재고량` (`제품코드`,`최적재고량`,`날짜`) values ((select `제품코드` from `제품정보` where `이름`=\""+name+"\"),"+Q+",\"" + cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) +1) + "-" + cal.get(Calendar.DATE) + "\");",2,0,MainActivity.con);
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
    /*
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void InitCalc() {
        int cnt;
        new ClientDataBase("select `판매량` from `제품판매량` where `제품코드`=(select `제품코드` from `제품정보` where `이름`=\"" + name + "\");", 1, 1, MainActivity.con);
        cnt = 0;
        while (true) {
            if (DBstring[cnt] != null) {
                dail = Integer.parseInt(DBstring[cnt]);
                cnt++;
            } else if (DBstring[cnt] == null) break;
        }

        /////////////////////////////////////////////최적재고량
        new ClientDataBase("select `최적재고량` from `최적재고량` where `날짜`=\"" + strCurTime + "\"and `제품코드` =(select `제품코드` from `제품정보` where `이름`=\"" + name + "\");", 1, 1, MainActivity.con);
        cnt = 0;
        while (true) {
            if (DBstring[cnt] != null) {
                Q = Integer.parseInt(DBstring[cnt]);
                cnt++;
            } else if (DBstring[cnt] == null) break;
        }

        //////////////////////////////////////////원가,판매가,잔존가
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



        //////////////////////////////////////////////dAvg입력,monAvg입력
        new ClientDataBase("select `날짜`,`판매량` from `제품판매량`;", 1, 2, MainActivity.con);
        String strWeek = null; //날짜 스트링형
        Date DateWeek = null;  //날짜 데이터 형
        int WeekdateAvg[] = new int[8];  //요일 판매량 평균계산용
        int MonthdateAvg[] = new int[13];  //달 판매량 평균계산용
        int WeekdateCnt[]=new int[8];  //요일 판매량 전체갯수 용
        int MonthdateCnt[]=new int[13];  //달 판매량 전체갯수 용
        sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);     ///simpleDateformat  (E는 요일,M은 달)   밑에 방법말고 해도됨.
        dAvg = new double[7];
        monAvg = new double[12];
        cnt = 0;
        //달별,요일별 db배열에 넣기
        while (true) {
            if (DBstring[cnt] != null) {
                strWeek = DBstring[cnt];
                try {
                    DateWeek = sdf.parse(strWeek);  //불러온 스트링값 데이터 형으로 변환
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                cal.setTime(DateWeek);  //불러온 날짜값으로 캘린더 변환
                int nMonth = cal.get(Calendar.MONTH)+1;
                MonthdateAvg[nMonth] += Integer.parseInt(DBstring[cnt + 1]);
                MonthdateCnt[nMonth]+=1;
                ///불러온 날짜 값으로 요일구분후 판매량 입력
                int nWeek = cal.get(Calendar.DAY_OF_WEEK);
                WeekdateAvg[nWeek] += Integer.parseInt(DBstring[cnt + 1]);
                WeekdateCnt[nWeek]+=1;
                cnt += 2;
            } else if (DBstring[cnt] == null) {
                ///////판매량 요일 평균낸후 dAvg에 입력
                for (int i = 1; i <= 7; i++) {
                    if (WeekdateAvg[i] != 0)
                        dAvg[i] = (int) (WeekdateAvg[i] / WeekdateCnt[i]);
                }
            }
            ///////판매량 달 평균낸후 monAvg에 입력
            for (int i = 1; i <= 12; i++) {
                if (MonthdateAvg[i] != 0)
                    monAvg[i] = (int) (MonthdateAvg[i] / MonthdateCnt[i]);
            }
            break;
        }
        /////////////////////////////////////////////////////////////

    }

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

//////////////////////////////////////////////////////////////////////////////
/////////////////////////////TEST CODE////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////

       /*p=300;
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

//        tSeason = (tMonth + 2) / 3;
//        tYear = (int) ((tDay + 364) / 365.254);
//yAvg연별판매량총평균(현재는 없으나 나중에 update가능->graph2에도 반영가능)