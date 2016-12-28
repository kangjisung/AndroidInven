package com.example.home7.customlistview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.locallistview.R;

import static com.example.locallistview.R.layout.itemview;

public class ItemView extends Activity {
	static final String[] CONTENTS = new String[] {
		"파리바게트",
		"뚜레쥬르",
		"이성당","이성당","이성당","이성당"
	};
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(itemview);
		Intent intent = getIntent();

		int itext = intent.getExtras().getInt("itemi");
		TextView tx1 = (TextView) findViewById(R.id.text01);
		tx1.setText(CONTENTS[itext]);




	}


}




