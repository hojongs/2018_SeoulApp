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
import com.hojong.meokgol.R;
import com.hojong.meokgol.adapter.ShopListAdapter;
import com.hojong.meokgol.ShopClickListener;
import com.hojong.meokgol.data_model.Shop;

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

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_list);

        Intent intent = new Intent();
        locationIdx = intent.getIntExtra("locationIdx", -1);

		// set visible back arrow button
		ActionBar actionbar = getSupportActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);

		listView = findViewById(R.id.shop_list);

		// TODO : get data from REST API
		adapter = new ShopListAdapter();
		listView.setAdapter(adapter);
        listView.setOnItemClickListener(new ShopClickListener());
//		adapter.addItem(new Shop(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_home_black_24dp), "가게1"));
//		adapter.addItem(new Shop(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_notifications_black_24dp), "가게2"));
        APIClient.getService().listShop(locationIdx).enqueue(callbackShopList());

		initDrawer();
	}

	private Callback<List<Shop>> callbackShopList()
    {
        return new Callback<List<Shop>>() {
            @Override
            public void onResponse(Call<List<Shop>> call, Response<List<Shop>> response) {
                Log.d(this.toString(), "response " + response.body());
                adapter.clear();
		        adapter.addItem(new Shop("가게1")); // TODO : for develop
                for (Shop i : response.body()) {
                    Log.d(this.toString(), "shop_img="+i.shop_img);
                    APIClient.getService().loadImage(i.shop_img).enqueue(callbackLoadImage(i));
                    adapter.addItem(i);
                }
                adapter.notifyDataSetChanged();
//				showProgress(false);
            }

            @Override
            public void onFailure(Call<List<Shop>> call, Throwable t) {
                Log.d(this.toString(), "가게 가져오기 실패");
                Toast.makeText(getApplicationContext(), "가게 가져오기 실패", Toast.LENGTH_SHORT).show();
                showProgress(false);
            }
        };
    }

    private Callback<ResponseBody> callbackLoadImage(final Shop i)
    {
        return new Callback<ResponseBody>() {
            Shop obj = i;

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                bmp = Bitmap.createScaledBitmap(bmp, 200, 200, true);
                Log.d(this.toString(), "response " + response.body() + "," + bmp.getWidth() + "," + bmp.getHeight());

                obj.bmp = bmp;
                adapter.notifyDataSetChanged();
                showProgress(false);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(this.toString(), "이미지 가져오기 실패"+t.toString());
                Toast.makeText(getApplicationContext(), "이미지 가져오기 실패", Toast.LENGTH_SHORT).show();
                showProgress(false);
            }
        };
    }

    protected void showProgress(final boolean show)
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
						menuItem.setChecked(true);
						mDrawerLayout.closeDrawers();

						return true;
					}
				});
	}
}
