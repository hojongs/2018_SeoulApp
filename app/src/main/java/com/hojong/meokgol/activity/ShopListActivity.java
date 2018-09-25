package com.hojong.meokgol.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.hojong.meokgol.APIClient;
import com.hojong.meokgol.IShowProgress;
import com.hojong.meokgol.MyCallback;
import com.hojong.meokgol.R;
import com.hojong.meokgol.adapter.ShopListAdapter;
import com.hojong.meokgol.data_model.Location;

import retrofit2.Call;

public class ShopListActivity extends MyAppCompatActivity implements IShowProgress
{
    protected ListView listView;
    protected View mProgressView;
	private DrawerLayout mDrawerLayout;
	private Location location;
	private ShopListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_list);

        location = (Location) getIntent().getSerializableExtra(Location.INTENT_KEY);
        Log.d(this.toString(), "locationIdx=" + location.location_idx);

		// set visible back arrow button
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		adapter = new ShopListAdapter();
        listView = findViewById(R.id.shop_list);
		listView.setAdapter(adapter);
        listView.setOnItemClickListener(adapter);

        mProgressView = findViewById(R.id.progress_bar);

		initDrawer();
        attemptData(null);
	}

    public void showProgress(final boolean show)
    {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        listView.setVisibility(show ? View.GONE : View.VISIBLE);
        listView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation) { listView.setVisibility(show ? View.GONE : View.VISIBLE); }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    // 필터 버튼 생성
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_shop_filter, menu);
		return true;
	}

	// 뒤로가기 버튼, 필터 버튼 onClick
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;
			case R.id.shop_filter:
                if (!mDrawerLayout.isDrawerOpen(GravityCompat.END))
				    mDrawerLayout.openDrawer(GravityCompat.END);
				else
				    mDrawerLayout.closeDrawer(GravityCompat.END);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	// 필터 drawer
	protected void initDrawer()
	{
		mDrawerLayout = findViewById(R.id.drawer_layout);

		NavigationView navigationView = findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(
				new NavigationView.OnNavigationItemSelectedListener() {
					@Override
					public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
					    String menu = menuItem.getTitle().toString();
                        Log.d(this.toString(), menu);
                        attemptData(menu);
						menuItem.setChecked(true);
						mDrawerLayout.closeDrawers();

						return true;
					}
				});
	}

	public void attemptData(String menu)
    {
        if (callList.size() > 0)
            return;

        Call call = APIClient.getService().listShop(location.location_idx, menu);
        callList.add(call);
        call.enqueue(MyCallback.callbackShopList(this, callList, adapter, "가게 정보 가져오기 실패"));
    }
}
