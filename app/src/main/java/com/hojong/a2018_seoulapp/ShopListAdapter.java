package com.hojong.a2018_seoulapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class ShopListAdapter extends BaseAdapter
{
	private List<Shop> shopDataList = new ArrayList<>();

	@Override
	public int getCount() {
		return shopDataList.size() ;
	}

	@Override
	public View getView(int position, View shopView, ViewGroup parent) {
		final Context context = parent.getContext();

		// "listview_item" Layout을 inflate하여 convertView 참조 획득.
		if (shopView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			shopView = inflater.inflate(R.layout.shop, parent, false);
		}

		// get view
		ImageView imageView = shopView.findViewById(R.id.imageView1);
		// get data
		Shop shop = shopDataList.get(position);
		// set data to view
		imageView.setImageDrawable(shop.icon);

		return shopView;
	}

	@Override
	public long getItemId(int position) {
		return position ;
	}
	
	@Override
	public Object getItem(int position) {
		return shopDataList.get(position) ;
	}

	public void addItem(Shop shop) {
		shopDataList.add(shop);
	}
}
