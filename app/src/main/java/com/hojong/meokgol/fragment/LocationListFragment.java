package com.hojong.meokgol.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
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
	private ListView locationListView;
	private View mProgressView;
	private RequestListTask mRequestTask;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_location_list, null);
		locationListView = rootView.findViewById(R.id.location_list);
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

		mProgressView = rootView.findViewById(R.id.location_list_progress);

		return rootView;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show)
	{
		int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

		locationListView.setVisibility(show ? View.GONE : View.VISIBLE);
		locationListView.animate().setDuration(shortAnimTime).alpha(
				show ? 0 : 1).setListener(new AnimatorListenerAdapter()
		{
			@Override
			public void onAnimationEnd(Animator animation)
			{
				locationListView.setVisibility(show ? View.GONE : View.VISIBLE);
			}
		});

		mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
		mProgressView.animate().setDuration(shortAnimTime).alpha(
				show ? 1 : 0).setListener(new AnimatorListenerAdapter()
		{
			@Override
			public void onAnimationEnd(Animator animation)
			{
				mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			}
		});
	}

	private void attemptRequestList()
	{
		if (mRequestTask != null) {
			return;
		}

		// Show a progress spinner, and kick off a background task to
		// perform the user login attempt.
		showProgress(true);
		mRequestTask = new RequestListTask();
		mRequestTask.execute((Void) null);
	}

	@Override
	public void onResume()
	{
		super.onResume();

		attemptRequestList();
	}

	public class RequestListTask extends AsyncTask<Void, Void, Boolean>
	{
//		private final String mUserId;
//		private final String mPassword;

		RequestListTask()
		{
		}

		@Override
		protected Boolean doInBackground(Void... params)
		{
			try {
				// Simulate network access.
				// TODO: REST API
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				return false;
			}

			return true;
		}

		@Override
		protected void onPostExecute(final Boolean success)
		{
			if (getActivity() == null)
				return;

			mRequestTask = null;
			showProgress(false);

			if (success) {
				// TODO : REST API
			}
			else { }
		}

		@Override
		protected void onCancelled()
		{
			mRequestTask = null;
			showProgress(false);
		}
	}
}
