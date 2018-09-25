package com.hojong.meokgol.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hojong.meokgol.R;
import com.hojong.meokgol.data_model.Location;
import com.hojong.meokgol.fragment.FavoriteShopListFragment;
import com.hojong.meokgol.fragment.LocationListFragment;
import com.hojong.meokgol.fragment.MyFragment;
import com.hojong.meokgol.fragment.MyPageFragment;
import com.hojong.meokgol.fragment.NoticeListFragment;

import java.io.Serializable;
import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener
{
	public BottomNavigationView navigation;
	private Bundle locationListBundle;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		locationListBundle = new Bundle();
        Serializable locationList = getIntent().getSerializableExtra(Location.INTENT_KEY);
        if (locationList == null)
            Log.d(toString(), "NullLocationList");
		locationListBundle.putSerializable(Location.INTENT_KEY, locationList);

//		Drawable actionBarBg = ResourcesCompat.getDrawable(getResources(), R.drawable.action_bar_bg, null);
//		getSupportActionBar().setBackgroundDrawable(actionBarBg);

		navigation = findViewById(R.id.navigation);
		navigation.setOnNavigationItemSelectedListener(this);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        BottomNavigationViewHelper.centerMenuIcon(navigation);

        Fragment defaultFragment = new LocationListFragment();
        defaultFragment.setArguments(locationListBundle); // TODO : 중복(id=5)
        Log.d(toString(), "bundle=" + defaultFragment.getArguments());
		enableNavIcon(0, R.drawable.nav_location_list_selected);
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
                fragment.setArguments(locationListBundle); // TODO : 중복(id=5)
                Log.d(toString(), "bundle=" + fragment.getArguments());
				enableNavIcon(0, R.drawable.nav_location_list_selected);
				break;
			case R.id.nav_favorites:
				fragment = new FavoriteShopListFragment();
				enableNavIcon(1, R.drawable.nav_favorites_selected);
				break;
			case R.id.nav_notices:
				fragment = new NoticeListFragment();
				enableNavIcon(2, R.drawable.nav_notices_events_selected);
				break;
			case R.id.nav_my_page:
				fragment = new MyPageFragment();
				enableNavIcon(3, R.drawable.nav_my_page_selected);
				break;
		}

		return loadFragment(fragment);
	}

	private void enableNavIcon(int index, int drawableID)
	{
		int[] iconList = {
				R.drawable.nav_location_list,
				R.drawable.nav_favorites,
				R.drawable.nav_notices_events,
				R.drawable.nav_my_page,
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

    private static class BottomNavigationViewHelper {
        static void centerMenuIcon(BottomNavigationView view) {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
            if (menuView != null) {
                for (int i = 0; i < menuView.getChildCount(); i++) {
                    BottomNavigationItemView menuItemView = (BottomNavigationItemView) menuView.getChildAt(i);
                    TextView smallText = (TextView) menuItemView.findViewById(R.id.smallLabel);
                    smallText.setVisibility(View.INVISIBLE);
                    //TextView largeText = (TextView) menuItemView.findViewById(R.id.largeLabel);
                    ImageView icon = (ImageView) menuItemView.findViewById(R.id.icon);
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) icon.getLayoutParams();
                    params.gravity = Gravity.CENTER;
                    menuItemView.setShiftingMode(true);
                }
            }
        }

        static void disableShiftMode(BottomNavigationView view) {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
            try {
                Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
                shiftingMode.setAccessible(true);
                shiftingMode.setBoolean(menuView, false);
                shiftingMode.setAccessible(false);
                for (int i = 0; i < menuView.getChildCount(); i++) {
                    BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                    //noinspection RestrictedApi
                    item.setShiftingMode(false);
                    // set once again checked value, so view will be updated
                    //noinspection RestrictedApi
                    item.setChecked(item.getItemData().isChecked());
                }
            } catch (NoSuchFieldException e) {
                Log.e("BNVHelper", "Unable to get shift mode field", e);
            } catch (IllegalAccessException e) {
                Log.e("BNVHelper", "Unable to change value of shift mode", e);
            }
        }
    }
}
