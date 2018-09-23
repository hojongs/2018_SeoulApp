package com.hojong.meokgol.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.hojong.meokgol.APIClient;
import com.hojong.meokgol.MyCallback;
import com.hojong.meokgol.R;
import com.hojong.meokgol.adapter.ShopListAdapter;
import com.hojong.meokgol.data_model.Shop;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopListActivity extends AppCompatActivity
{
	private DrawerLayout mDrawerLayout;
	private int locationIdx;
	private ShopListAdapter adapter;
	private ListView listView;
	private View mProgressView;
	private List<Call> callList;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_list);

        locationIdx = getIntent().getIntExtra("locationIdx", -1);
        Log.d(this.toString(), "locationIdx=" + locationIdx);

		// set visible back arrow button
		ActionBar actionbar = getSupportActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);

		adapter = new ShopListAdapter();
        listView = findViewById(R.id.shop_list);
		listView.setAdapter(adapter);
        listView.setOnItemClickListener(adapter);
        mProgressView = findViewById(R.id.progress_bar);
        callList = new ArrayList<>();

		initDrawer();
	}

    public void showProgress(final boolean show)
    {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        listView.setVisibility(show ? View.GONE : View.VISIBLE);
        listView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                listView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
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
				mDrawerLayout.openDrawer(GravityCompat.END);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

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

        Call call = APIClient.getService().listShop(locationIdx, menu);
        callList.add(call);
        call.enqueue(MyCallback.callbackShopList(this, null, callList, adapter));
    }

    @Override
    public void onResume()
    {
        super.onResume();
        attemptData(null);
    }
}
