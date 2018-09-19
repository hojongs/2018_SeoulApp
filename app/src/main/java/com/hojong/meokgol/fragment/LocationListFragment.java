package com.hojong.meokgol.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hojong.meokgol.adapter.LocationListAdapter;
import com.hojong.meokgol.R;
import com.hojong.meokgol.activity.ShopListActivity;
import com.hojong.meokgol.data_model.Location;

public class LocationListFragment extends Fragment
{
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_location_list, null);
		ListView locationListView = rootView.findViewById(R.id.location_list);
		LocationListAdapter adapter = new LocationListAdapter();
		locationListView.setAdapter(adapter);
		locationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				Intent intent = new Intent(getContext(), ShopListActivity.class);
				// TODO : intent.putExtra(""); // REST API
				startActivity(intent);
			}
		});

		adapter.addItem(new Location(ContextCompat.getDrawable(getActivity(), R.drawable.ic_dashboard_black_24dp)));
		adapter.addItem(new Location(ContextCompat.getDrawable(getActivity(), R.drawable.ic_home_black_24dp)));

		return rootView;
	}
}
