package com.example.kangjisung.likeroom.FragmentInfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kangjisung.likeroom.R;

public class FragmentInfoMain extends Fragment {
//매장정보에 관한 부분이다. 아마 지도 등 CouponTan에서 만들어진 부분들을 여기로 옮겨올 수 있을 듯하다.
    //지도, 사진, 주소등이 필요
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /* 초기화 코드는 여기서 */
        return inflater.inflate(R.layout.fragment_info_main, container, false);
    }

    /* 이벤트 코드는 여기서 */
}