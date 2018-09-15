package com.hojong.a2018_seoulapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class LocationListFragment extends ListFragment
{
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		LocationListAdapter adapter = new LocationListAdapter();
		setListAdapter(adapter);

		adapter.addItem(new Location(ContextCompat.getDrawable(getActivity(), R.drawable.ic_dashboard_black_24dp)));
		adapter.addItem(new Location(ContextCompat.getDrawable(getActivity(), R.drawable.ic_home_black_24dp)));

		return inflater.inflate(R.layout.fragment_location_list, null);
	}

	@Override
	public void onListItemClick (ListView listView, View view, int position, long id) {
		Intent intent = new Intent(getContext(), ShopListActivity.class);
		// TODO : intent.putExtra("");
		startActivity(intent);
	}
}
