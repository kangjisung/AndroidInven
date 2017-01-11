package com.example.kangjisung.likeroom.Util;

import android.content.Context;
import android.widget.Toast;

public class SingleToast
{
    //공지사항 등에서 데이터베이스 연동이 안 된 상태이므로,
    //버튼을 클릭하면 알림창이 뜨는 형식으로, 클릭이 제대로 되는지 확인하고자 하였다.
    //예를 들어 공지사항리스트에서 공지사항1을 선택하면 '1이 선택되었습니다'라는 알림이 뜨는데 그 부분을 담당한다.
    private static Toast mToast;

    public static void show(Context context, String text, int duration)
    {
        if (mToast != null) mToast.cancel();
        mToast = Toast.makeText(context, text, duration);
        mToast.show();
    }
}