package com.hojong.meokgol.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.hojong.meokgol.R;
import com.hojong.meokgol.fragment.FavoriteShopListFragment;
import com.hojong.meokgol.fragment.LocationListFragment;
import com.hojong.meokgol.fragment.MyPageFragment;
import com.hojong.meokgol.fragment.NoticeListFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final BottomNavigationView navigation = findViewById(R.id.navigation);
		navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem item) {
				Fragment fragment = null;

				// LAZY_TODO : 중복 코드 (id=2)
				Menu menu = navigation.getMenu();
				List<MenuItem> menuItemList = new ArrayList<>();
				for (int i=0;i<menu.size();i++)
					menuItemList.add(menu.getItem(i));

				int[] menuItemIconList = {
						R.drawable.ic_location_list,
						R.drawable.ic_favorites,
						R.drawable.ic_notices_events,
						R.drawable.ic_my_page,
				};

				switch (item.getItemId())
				{
					case R.id.nav_locations:
						fragment = new LocationListFragment();
						menuItemIconList[0] = R.drawable.ic_location_list_selected;
						break;
					case R.id.nav_favorites:
						fragment = new FavoriteShopListFragment();
						menuItemIconList[1] = R.drawable.ic_favorites_selected;
						break;
					case R.id.nav_notices:
						fragment = new NoticeListFragment();
						menuItemIconList[2] = R.drawable.ic_notices_events_selected;
						break;
					case R.id.nav_my_page:
						fragment = new MyPageFragment();
						menuItemIconList[3] = R.drawable.ic_my_page_selected;
						break;
				}

				for (int i=0;i<menuItemList.size();i++)
					menuItemList.get(i).setIcon(menuItemIconList[i]);

				return loadFragment(fragment);
			}
		});

		Fragment defaultFragment = new LocationListFragment();
		// LAZY_TODO : 중복 코드 (id=2)
		Menu menu = navigation.getMenu();
		List<MenuItem> menuItemList = new ArrayList<>();
		for (int i=0;i<menu.size();i++)
			menuItemList.add(menu.getItem(i));
		int[] menuItemIconList = {
				R.drawable.ic_location_list_selected,
				R.drawable.ic_favorites,
				R.drawable.ic_notices_events,
				R.drawable.ic_my_page,
		};
		for (int i=0;i<menuItemList.size();i++)
			menuItemList.get(i).setIcon(menuItemIconList[i]);
		loadFragment(defaultFragment);
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
