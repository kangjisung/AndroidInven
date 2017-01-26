package com.example.kangjisung.likeroom.FragmentPoint;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.kangjisung.likeroom.FragmentUser.UserStampDialog;
import com.example.kangjisung.likeroom.R;

public class PointMain extends Fragment
{
    private PointSaveDialog pointSaveDialog;
    private UserStampDialog userStampDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.point_main, container, false);

        Button buttonTempSave = (Button)contentView.findViewById(R.id.buttonTempSave);
        buttonTempSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                pointSaveDialog = new PointSaveDialog(getActivity(),
                        "", // 제목
                        "", // 내용
                        addDialogBackListener, // 왼쪽 버튼 이벤트
                        addDialogOKListener); // 오른쪽 버튼 이벤트
                pointSaveDialog.show();
            }
        });

        final Button buttonTempStamp = (Button)contentView.findViewById(R.id.button_temp_stamp);
        buttonTempStamp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
            }
        });

        return contentView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        int MENU1 = Menu.FIRST + 1;

        menu.add(Menu.NONE, MENU1, Menu.NONE, "메뉴1");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(getActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener addDialogBackListener = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getActivity().getApplicationContext(), "취소 버튼 클릭", Toast.LENGTH_SHORT).show();
            pointSaveDialog.dismiss();
        }
    };

    private View.OnClickListener addDialogOKListener = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getActivity().getApplicationContext(), "완료 버튼 클릭", Toast.LENGTH_SHORT).show();
            pointSaveDialog.dismiss();
        }
    };
}