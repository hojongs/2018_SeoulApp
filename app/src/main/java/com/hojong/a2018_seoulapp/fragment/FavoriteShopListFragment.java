package com.hojong.a2018_seoulapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hojong.a2018_seoulapp.R;
import com.hojong.a2018_seoulapp.adapter.ShopListAdapter;
import com.hojong.a2018_seoulapp.ShopListItemClickListener;
import com.hojong.a2018_seoulapp.data_model.Shop;

public class FavoriteShopListFragment extends Fragment
{
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_favorite_shop_list, null);

		ListView listView = rootView.findViewById(R.id.favorite_list);

		ShopListAdapter adapter = new ShopListAdapter();
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new ShopListItemClickListener());

		adapter.addItem(new Shop(ContextCompat.getDrawable(getActivity(), R.drawable.ic_home_black_24dp), "즐겨찾기 가게1"));
		adapter.addItem(new Shop(ContextCompat.getDrawable(getActivity(), R.drawable.ic_notifications_black_24dp), "즐겨찾기 가게2"));

		return rootView;
	}
}
