package com.example.kangjisung.likeroom.inventory;

/**
 * Created by kangjisung on 2016-11-21.
 */
import static java.lang.Math.sqrt;

//calc 상위 클래스가 있고,하위에 1.예상판매량, 2.통계, 3.최적재고&예상매출 하위클래스 있다.
    public class calc {
        static int Q, c, p, s, min, max; //Q최적재고량, c원가(DB), p판매가(DB), s잔존가(가치)(DB), min최소예상판매량(DB), max최대예상판매량(DB)
        static int tSeason, tWeek, tMonth, tDay, tYear; //tSeason총분기수,tWeek총주수 tmonth총월수, tDay총일수,tYear총년수
        static int[] DailySale, WeaklySale, MonthlySale, SeasonalSale, YearlySale; //DS일별판매량,WS주별판매량 MS월별판매량 SS분기별판매량 YS일년판매량

        static int[] wAvg, monAvg, sAvg, yAvg; //wAVg요일판매량누적평균 monAVg월판매량누적평균 sAvg분기판매량누적평균 yAvg연판매량누적평균
        static double m, v, t, w, season, FM, FD, e; //m전체평균판매량(DB), v표준편차, FD예상평균판매량, FM최종평균, t추세, w요일지수, season분기별지수, e이벤트지수
        static int[] monthDay = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int popUpResult=20;
        int currentMonth=12;

        int mode = tDay % 7;// 7일주일

        //매일 자정에 실행
        void dailyUpdate() {
            tDay++;
            DailySale[tDay] = popUpResult;//팝업으로 받은 판매량
            m = (m * (tDay - 1) + DailySale[tDay]) / DailySale[tDay];
            //최적재고량 산출-->main화면의 숫자 바꿔준다.
        }

        //요일별평균판매량(매주 한번 실행) update 일요일 자정
        void weeklyUpdate() {
            tWeek++;
            for (int k = tDay - 6; k <= tDay; k++) {
                WeaklySale[tWeek] += DailySale[k];
            }
            wAvg[tWeek] = (wAvg[tWeek - 1] * (tWeek - 1) + WeaklySale[tWeek]) / tWeek;
        }

        //월별평균판매량(매월 한번 실행) update
        void monthlyUpdate() {
            tMonth++;
            for (int k = tDay - monthDay[currentMonth] + 1; k <= tDay; k++) {
                MonthlySale[tMonth] += DailySale[k];
            }

            monAvg[tMonth] = (monAvg[tMonth - 1] * (tMonth - 1) + MonthlySale[tMonth]) / tMonth;
        }

        void seasonalUpdate() {
            tSeason++;
            //분기별평균판매량(매시즌(3개월)말에 한번 실행) update
            sAvg[tSeason] = (sAvg[tSeason - 1] * (tSeason - 1) + MonthlySale[tMonth] + MonthlySale[tMonth - 1] + MonthlySale[tMonth - 2]) / tSeason;
        }

        void yearlyUpdate() {
            tYear++;
            //연별평균판매량(매년 1월1일 0시 한번 실행) update
            yAvg[tYear] = (yAvg[tYear - 1] * (tYear - 1) + SeasonalSale[tSeason] + SeasonalSale[tSeason - 1] + SeasonalSale[tSeason - 2] + SeasonalSale[tSeason - 3]) / tYear;
        }


        /////////////////////////////////////////////////////최적재고량 계산 (메인화면과 3.최적재고&예상매출 화면에 필요)
        void calcQ() {
            v = (max - min) / 6; //표준편차
            FM = (min + max + (4 * FD)) / 6; //최종평균계산
            FD = (int) (m * w * season * e + t) + 1; //예상판매량 계산
            Q = (int) (FM + ((v / 2) * (sqrt((p - c) / (c - s)) - sqrt((c - s) / (p - c))))) + 1; //최적재고량 계산
        }

        ////////////////////////////////////////////////////////////주별평균으로 추세계산(베타만 계산)
        void calcT(int n, int SS[]) {//n분기수, SS[]분기별 판매량

            //t=Sxy/Sxx-->수치해석 계산식 필요

        }

        ////////////////////////////////요일지수= 특정요일평균/전체평균
        void calcW() {
            w = wAvg[mode] / m; // int mode = tDay % 7;// 7일주일

        }

        ///////////////////////////////////////분기별 지수 특정분기의 작년판매량/작년전체판매량평균
        void calcSeason() {
            season = SeasonalSale[tSeason - 4] / YearlySale[tYear - 1];

        }

        /////////////////////////////////////이벤트지수 계산
        void calcE() {

            //이벤트(공휴일마다 해당번호있음)

            // e= 작년이벤트 실제판매량/(이벤트 속한 달 평균*28-(이벤트날 판매량))/27//작년의 판매량 불러오려면 어떤 방식으로? (배열에 저장?)
            //이벤트는 한 달에 한번정도 있는것이 좋다.
        }

        //////////////////////////////////////구비재고량을 입력받아 그에따른 예상이익을 출력
        void calcProfit(){

        }

    }

