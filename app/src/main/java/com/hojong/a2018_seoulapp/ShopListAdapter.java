package com.hojong.a2018_seoulapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hojong.a2018_seoulapp.data_model.Shop;

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
			shopView = inflater.inflate(R.layout.layout_shop_list_item, parent, false);
		}

		Shop shop = shopDataList.get(position);

		// set shop image
		ImageView shopImageView = shopView.findViewById(R.id.shop_img);
		shopImageView.setImageDrawable(shop.img);
		// set shop name
		TextView shopNameView = shopView.findViewById(R.id.shop_name);
		shopNameView.setText(shop.name);

		// set click listener
		ImageButton shopFavoriteBtn = shopView.findViewById(R.id.favorite_btn);
		shopFavoriteBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Log.d("Shop Favorite", "onClick");
			}
		});

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
