package com.hojong.meokgol.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.hojong.meokgol.APIClient;
import com.hojong.meokgol.LocationClickListener;
import com.hojong.meokgol.adapter.LocationListAdapter;
import com.hojong.meokgol.R;
import com.hojong.meokgol.data_model.Location;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// TODO (LAZY) refresh while refreshing (Favorite, Notice) (여러 번 누르면 느려짐)
public class LocationListFragment extends MyFragment
{
	private ListView listView;
	private LocationListAdapter adapter;
//	private Call<List<Location>> call;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		call = APIClient.getService().listLocation();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_location_list, null);
		adapter = new LocationListAdapter();
		listView = rootView.findViewById(R.id.location_list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new LocationClickListener());
		mProgressView = rootView.findViewById(R.id.progress_bar);

		return rootView;
	}

	private Callback<List<Location>> callbackListLocation() {
		return new Callback<List<Location>>() {
			@Override
			public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
				Log.d(this.toString(), "response " + response.body());
				adapter.clear();
				for (Location i : response.body()) {
					Log.d(this.toString(), "location_img="+i.location_img);
					APIClient.getService().loadImage(i.location_img).enqueue(callbackLoadImage(i));
					adapter.addItem(i);
				}
				adapter.notifyDataSetChanged();
//				showProgress(false);
			}

			@Override
			public void onFailure(Call<List<Location>> call, Throwable t) {
				Log.d(this.toString(), "지역 가져오기 실패");
				if (getActivity() != null)
					Toast.makeText(getContext(), "지역 가져오기 실패", Toast.LENGTH_SHORT).show();
				showProgress(false);
			}
		};
	}

	private Callback<ResponseBody> callbackLoadImage(final Location i)
	{
		return new Callback<ResponseBody>() {
			Location obj = i;

			@Override
			public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
				Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
				bmp = Bitmap.createScaledBitmap(bmp, 200, 200, true);
				Log.d(this.toString(), "response " + response.body() + "," + bmp.getWidth() + "," + bmp.getHeight());

				obj.bmp = bmp;
				adapter.notifyDataSetChanged();
				showProgress(false);
			}

			@Override
			public void onFailure(Call<ResponseBody> call, Throwable t) {
				Log.d(this.toString(), "이미지 가져오기 실패"+t.toString());
				if (getActivity() != null)
					Toast.makeText(getContext(), "이미지 가져오기 실패", Toast.LENGTH_SHORT).show();
				showProgress(false);
			}
		};
	}

	@Override
	protected void showProgress(final boolean show)
	{
		if (getActivity() == null)
			return;

		int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

		listView.setVisibility(show ? View.GONE : View.VISIBLE);
		listView.animate().setDuration(shortAnimTime).alpha(
				show ? 0 : 1).setListener(new AnimatorListenerAdapter()
		{
			@Override
			public void onAnimationEnd(Animator animation)
			{
				listView.setVisibility(show ? View.GONE : View.VISIBLE);
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

	public void attemptData()
	{
		super.attemptData();
		APIClient.getService().listLocation().enqueue(callbackListLocation());
	}
}
