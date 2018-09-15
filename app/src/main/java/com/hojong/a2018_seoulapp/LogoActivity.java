package com.hojong.a2018_seoulapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LogoActivity extends AppCompatActivity
{
	final int WAIT_TIME_MSEC = 1500;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logo);

		// 일정 시간 후 액티비티 전환
		new Handler().postDelayed(new Runnable(){
			@Override
			public void run() {
				Intent intent = new Intent(LogoActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		}, WAIT_TIME_MSEC);
	}
}
