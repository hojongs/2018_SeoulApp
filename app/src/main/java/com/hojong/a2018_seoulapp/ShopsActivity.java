package com.hojong.a2018_seoulapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class ShopsActivity extends AppCompatActivity
{

	private TextView mTextMessage;

	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
			= new BottomNavigationView.OnNavigationItemSelectedListener()
	{

		@Override
		public boolean onNavigationItemSelected(@NonNull MenuItem item)
		{
			switch (item.getItemId()) {
				case R.id.navigation_locations:
					mTextMessage.setText(R.string.location_find);
					return true;
				case R.id.navigation_favorites:
					mTextMessage.setText(R.string.favorites);
					return true;
				case R.id.navigation_my_page:
					mTextMessage.setText(R.string.my_page);
					return true;
			}
			return false;
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store);

		mTextMessage = (TextView) findViewById(R.id.message);
		BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
	}

}
