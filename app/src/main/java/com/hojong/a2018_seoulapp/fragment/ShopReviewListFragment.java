package com.hojong.a2018_seoulapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.hojong.a2018_seoulapp.R;
import com.hojong.a2018_seoulapp.adapter.ShopReviewListAdapter;
import com.hojong.a2018_seoulapp.data_model.Location;

public class ShopReviewListFragment extends Fragment {
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_shop_review_list, null);

		ImageButton reviewWriteBtn = rootView.findViewById(R.id.review_write_btn);
		reviewWriteBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

			}
		});

		ListView locationListView = rootView.findViewById(R.id.shop_review_list);
		ShopReviewListAdapter adapter = new ShopReviewListAdapter();
		locationListView.setAdapter(adapter);
		locationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				// TODO: need?
			}
		});

		adapter.addItem(new Location(ContextCompat.getDrawable(getActivity(), R.drawable.ic_dashboard_black_24dp)));
		adapter.addItem(new Location(ContextCompat.getDrawable(getActivity(), R.drawable.ic_home_black_24dp)));

		return rootView;
	}
}
