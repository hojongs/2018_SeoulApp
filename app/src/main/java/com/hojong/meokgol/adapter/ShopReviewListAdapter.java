package com.hojong.meokgol.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hojong.meokgol.R;
import com.hojong.meokgol.data_model.Location;
import com.hojong.meokgol.data_model.ShopReview;

import java.util.ArrayList;
import java.util.List;

public class ShopReviewListAdapter extends BaseAdapter
{
	private List<ShopReview> shopReviewDataList = new ArrayList<>();

	@Override
	public int getCount() {
		return shopReviewDataList.size() ;
	}

	@Override
	public View getView(int position, View shopReviewView, ViewGroup parent) {
		final Context context = parent.getContext();

		if (shopReviewView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			shopReviewView = inflater.inflate(R.layout.layout_shop_review_list_item, parent, false);
		}

		// TODO : ShopReview
//		// get view
//		ImageView imageView = shopReviewView.findViewById(R.id.shop_img);
//		// get data
//		Location location = shopReviewDataList.get(position);
//		// set data to view
//		imageView.setImageDrawable(location.bmp);

		return shopReviewView;
	}

	@Override
	public long getItemId(int position) {
		return position ;
	}
	
	@Override
	public Object getItem(int position) {
		return shopReviewDataList.get(position) ;
	}

	public void addItem(ShopReview shopReview) {
		shopReviewDataList.add(shopReview);
	}
}
