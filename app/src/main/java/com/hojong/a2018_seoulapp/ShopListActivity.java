package com.hojong.a2018_seoulapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ListView;

public class ShopListActivity extends AppCompatActivity
{
	private DrawerLayout mDrawerLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_list);

		// set visible back arrow button
		ActionBar actionbar = getSupportActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);

		ListView shopListView = findViewById(android.R.id.list);

		// TODO : get data from REST API
		ShopListAdapter adapter = new ShopListAdapter();
		shopListView.setAdapter(adapter);
		adapter.addItem(new Shop(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_home_black_24dp), "가게1"));
		adapter.addItem(new Shop(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_notifications_black_24dp), "가게2"));

		shopListView.setOnItemClickListener(new ShopListItemClickListener());

		initDrawer();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_shop_list, menu);
		return true;
	}

	// 뒤로가기 버튼, 필터 버튼 onClick
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;
			case R.id.shop_filter:
				mDrawerLayout.openDrawer(GravityCompat.END);
				return true;
		}
		return super.onOptionsItemSelected(item);
	};

	protected void initDrawer()
	{
		mDrawerLayout = findViewById(R.id.drawer_layout);

		NavigationView navigationView = findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(
				new NavigationView.OnNavigationItemSelectedListener() {
					@Override
					public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
						menuItem.setChecked(true);
						mDrawerLayout.closeDrawers();

						return true;
					}
				});
	}
}
