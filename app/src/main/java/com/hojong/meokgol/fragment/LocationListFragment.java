package com.hojong.meokgol.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.hojong.meokgol.APIClient;
import com.hojong.meokgol.MyCallback;
import com.hojong.meokgol.adapter.LocationListAdapter;
import com.hojong.meokgol.R;
import com.hojong.meokgol.data_model.Location;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationListFragment extends Fragment
{
	private ListView listView;
	private LocationListAdapter adapter;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_location_list, null);
		adapter = new LocationListAdapter();
		listView = rootView.findViewById(R.id.location_list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(adapter);

		Bundle bundle = getArguments();
		if (bundle == null)
			Log.d(toString(), "NullBundle");
		List<Location> locationList = (List<Location>) bundle.getSerializable(Location.INTENT_KEY);
		Log.d(toString(), "locationList=" + locationList);
		for (Location location : locationList)
			adapter.addItem(location);
		adapter.notifyDataSetChanged();

		return rootView;
	}
}
