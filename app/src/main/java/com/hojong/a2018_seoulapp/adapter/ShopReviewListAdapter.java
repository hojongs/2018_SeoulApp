package com.hojong.a2018_seoulapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hojong.a2018_seoulapp.R;
import com.hojong.a2018_seoulapp.data_model.Location;

import java.util.ArrayList;
import java.util.List;

public class ShopReviewListAdapter extends BaseAdapter
{
	private List<Location> locationDataList = new ArrayList<>();

	@Override
	public int getCount() {
		return locationDataList.size() ;
	}

	@Override
	public View getView(int position, View shopReviewView, ViewGroup parent) {
		final Context context = parent.getContext();

		if (shopReviewView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			shopReviewView = inflater.inflate(R.layout.layout_shop_review_list_item, parent, false);
		}

//		// get view
//		ImageView imageView = shopReviewView.findViewById(R.id.shop_img);
//		// get data
//		Location location = locationDataList.get(position);
//		// set data to view
//		imageView.setImageDrawable(location.image);

		return shopReviewView;
	}

	@Override
	public long getItemId(int position) {
		return position ;
	}
	
	@Override
	public Object getItem(int position) {
		return locationDataList.get(position) ;
	}

	public void addItem(Location location) {
		locationDataList.add(location);
	}
}
