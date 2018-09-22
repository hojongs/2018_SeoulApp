package com.hojong.meokgol.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.hojong.meokgol.R;
import com.hojong.meokgol.fragment.ShopInfoFragment;
import com.hojong.meokgol.fragment.ShopMapFragment;
import com.hojong.meokgol.fragment.ShopReviewListFragment;

public class ShopActivity extends AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop);

		// set visible back arrow button
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		Fragment defaultFragment = new ShopInfoFragment();
		loadFragment(defaultFragment);

		BottomNavigationView navigation = findViewById(R.id.navigation_shop);
		navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem item) {
				Fragment fragment = null;

				switch (item.getItemId())
				{
					case R.id.nav_shop_info:
						fragment = new ShopInfoFragment();
						break;
					case R.id.nav_shop_position:
						fragment = new ShopMapFragment();
						break;
					case R.id.nav_shop_review_list:
						fragment = new ShopReviewListFragment();
						break;
				}

				return loadFragment(fragment);
			}
		});
	}

	private boolean loadFragment(Fragment fragment) {
		//switching fragment
		if (fragment != null) {
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.fragment_container, fragment)
					.commit();

			return true;
		}
		return false;
	}

	// 뒤로가기 버튼 onClick
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	};
}
