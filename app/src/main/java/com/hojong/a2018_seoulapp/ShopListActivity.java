package com.hojong.a2018_seoulapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

public class ShopListActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_list);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		ShopListAdapter adapter = new ShopListAdapter();
		ListView listview = (ListView) findViewById(android.R.id.list);
		listview.setAdapter(adapter);

		adapter.addItem(new Shop(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_home_black_24dp)));
		adapter.addItem(new Shop(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_notifications_black_24dp)));
	}

	// back arrow button click
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	};
}
