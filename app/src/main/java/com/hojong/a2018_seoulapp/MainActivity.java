package com.hojong.a2018_seoulapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Fragment defaultFragment = new LocationListFragment();
		loadFragment(defaultFragment);

		BottomNavigationView navigation = findViewById(R.id.navigation);
		navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem item) {
				Fragment fragment = null;

				switch (item.getItemId())
				{
					case R.id.nav_locations:
						fragment = new LocationListFragment();
						break;
					case R.id.nav_favorites:
						fragment = new FavoriteShopListFragment();
						break;
					case R.id.nav_my_page:
						fragment = new MyPageFragment();
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		int loginSuccess = resultCode;
		if (loginSuccess == RESULT_OK)
		{
			// TODO : Update MyPage
		}
	}
}
