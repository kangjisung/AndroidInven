package com.Coupon.Tan.DataIOManager;

/**
 * Created by stories2 on 2016. 12. 10..
 */

public class DemoDataPusher {

    String[][] demoDataWhichIsTestProcess = new String[][]{
        {"1", "태평양 어느 한적한 곳", "-0.9051291", "124.9369705", "악마빵집", "1", ""},
        {"2", "서울시 내가 알게 뭐야", "37.5650172", "126.8494648", "내 배 빵", "1", ""},
        {"3", "독일의 랜덤 좌표", "52.549141", "8.1382393", "그램카", "1", ""},
        {"4", "디지몬 세계 입구", "32.3190004", "-64.8374675", "찾아라 비밀의 빵", "0", ""},
        {"5", "충청남도 천안시 서북구 불당동 993", "36.8035481", "127.104921", "은하철도993", "0", ""},
        {"6", "일본 〒302-0115 茨城県 守谷市中央2-18-3", "35.9506698", "139.9900788", "守谷駅", "1", ""},
    };

    String[] demoDataWhichIsUserInfo = new String[]{
        "김현우", "010-5635-1845"
    };

    public String[][] GetAllSavedData() {
        return demoDataWhichIsTestProcess;
    }

    public String[] GetSomeSavedData(int position) {
        return demoDataWhichIsTestProcess[position];
    }

    public String[] GetAllSavedUserInfoData() {
        return demoDataWhichIsUserInfo;
    }
}
