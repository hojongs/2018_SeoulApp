package com.hojong.meokgol.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.hojong.meokgol.R;
import com.hojong.meokgol.fragment.FavoriteShopListFragment;
import com.hojong.meokgol.fragment.MyFragment;
import com.hojong.meokgol.fragment.LocationListFragment;
import com.hojong.meokgol.fragment.MyPageFragment;
import com.hojong.meokgol.fragment.NoticeListFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener
{
	public BottomNavigationView navigation;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Drawable actionBarBg = ResourcesCompat.getDrawable(getResources(), R.drawable.img_action_bar_background, null);
		getSupportActionBar().setBackgroundDrawable(actionBarBg);

		navigation = findViewById(R.id.navigation);
		navigation.setOnNavigationItemSelectedListener(this);

		Fragment defaultFragment = new LocationListFragment();
		enableNavIcon(0, R.drawable.ic_location_list_selected);
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
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		Log.d(this.toString(), "onNavigationItemSelected");
		Fragment fragment = null;

		switch (item.getItemId())
		{
			case R.id.nav_locations:
				fragment = new LocationListFragment();
				enableNavIcon(0, R.drawable.ic_location_list_selected);
				break;
			case R.id.nav_favorites:
				fragment = new FavoriteShopListFragment();
				enableNavIcon(1, R.drawable.ic_favorites_selected);
				break;
			case R.id.nav_notices:
				fragment = new NoticeListFragment();
				enableNavIcon(2, R.drawable.ic_notices_events_selected);
				break;
			case R.id.nav_my_page:
				fragment = new MyPageFragment();
				enableNavIcon(3, R.drawable.ic_my_page_selected);
				break;
		}

		return loadFragment(fragment);
	}

	private void enableNavIcon(int index, int drawableID)
	{
		int[] iconList = {
				R.drawable.ic_location_list,
				R.drawable.ic_favorites,
				R.drawable.ic_notices_events,
				R.drawable.ic_my_page,
		};
		iconList[index] = drawableID;

		Menu menu = navigation.getMenu();
		for (int i=0;i<menu.size();i++)
			menu.getItem(i).setIcon(iconList[i]);;
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                MyFragment fragment = (MyFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                fragment.attemptData();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
