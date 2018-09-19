package com.hojong.meokgol.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hojong.meokgol.R;

public class LogoActivity extends AppCompatActivity
{
	final int WAIT_TIME_MSEC = 1500;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logo);

		// 일정 시간 후 액티비티 전환
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent;
				SharedPreferences pref = getSharedPreferences("isFirst", Activity.MODE_PRIVATE);
				boolean isFirst = pref.getBoolean("isFirst", false);

				if(isFirst)
				{
					SharedPreferences.Editor editor = pref.edit();
					editor.putBoolean("isFirst",true);
					editor.apply();

					intent = new Intent(getApplicationContext(), TutorialActivity.class);
				}
				else {
					intent = new Intent(getApplicationContext(), MainActivity.class);
				}

				// DEBUG
				intent = new Intent(getApplicationContext(), TutorialActivity.class);
				startActivity(intent);
				finish();
			}
		}, WAIT_TIME_MSEC);
	}
}
