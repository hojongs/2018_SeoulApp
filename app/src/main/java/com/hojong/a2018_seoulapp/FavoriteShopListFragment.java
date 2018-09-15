package com.hojong.a2018_seoulapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FavoriteShopListFragment extends ListFragment
{
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		ShopListAdapter adapter = new ShopListAdapter();
		setListAdapter(adapter);

		adapter.addItem(new Shop(ContextCompat.getDrawable(getActivity(), R.drawable.ic_home_black_24dp)));
		adapter.addItem(new Shop(ContextCompat.getDrawable(getActivity(), R.drawable.ic_notifications_black_24dp)));

		return inflater.inflate(R.layout.fragment_favorite_shop_list, null);
	}
}
