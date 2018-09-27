package com.hojong.meokgol.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.hojong.meokgol.R;
import com.hojong.meokgol.data_model.Shop;
import com.hojong.meokgol.fragment.MyFragment;
import com.hojong.meokgol.fragment.ShopInfoFragment;
import com.hojong.meokgol.fragment.ShopMapFragment;
import com.hojong.meokgol.fragment.ShopReviewListFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class ShopActivity extends AppCompatActivity implements OnTabSelectListener, View.OnClickListener
{
    private Bundle bundle;
    private Shop shop;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop);

        shop = (Shop) getIntent().getSerializableExtra(Shop.INTENT_KEY);
        Log.d(toString(), "shop=" + shop);
        if (shop == null)
            shop = new Shop("");
        bundle = new Bundle();
        bundle.putSerializable(Shop.INTENT_KEY, shop);

		// set visible back arrow button
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView shopImgView = findViewById(R.id.shop_img);
        shopImgView.setImageBitmap(shop.getBmp());

        ImageButton orderBtn = findViewById(R.id.order_btn);
        orderBtn.setOnClickListener(this);

        BottomBar bottomBar = findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(this);
        onTabSelected(R.id.nav_shop_info); // default fragment
    }

    @Override
    public void onClick(View v) {
        // order btn click
        Log.d(toString(), "put menuList=" + shop.menu_list);
        Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
        intent.putExtra(Shop.INTENT_KEY, shop);
        startActivity(intent);
    }

	private boolean loadFragment(Fragment fragment) {
		//switching fragment
		if (fragment != null) {
            fragment.setArguments(bundle);

			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.fragment_container, fragment)
					.commit();

			return true;
		}
		return false;
	}

	// 뒤로가기, 새로고침 버튼 onClick
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;
			case R.id.refresh:
				MyFragment fragment = (MyFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
				fragment.attemptData();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

    @Override
    public void onTabSelected(int tabId) {
        Fragment fragment = null;

        switch (tabId)
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

        loadFragment(fragment);
    }
}
