package com.hojong.meokgol.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.hojong.meokgol.APIClient;
import com.hojong.meokgol.NoticeClickListener;
import com.hojong.meokgol.R;
import com.hojong.meokgol.adapter.NoticeListAdapter;
import com.hojong.meokgol.ShopClickListener;
import com.hojong.meokgol.data_model.Notice;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// TODO (LAZY) 데이터 유지
public class NoticeListFragment extends MyFragment
{
	ListView listView;
	NoticeListAdapter adapter;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_notice_list, null);

		mProgressView = rootView.findViewById(R.id.progress_bar);
		listView = rootView.findViewById(R.id.notice_list);

		adapter = new NoticeListAdapter();
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new NoticeClickListener());

		return rootView;
	}

	private Callback<List<Notice>> callbackListNotice() {
		return new Callback<List<Notice>>() {
			@Override
			public void onResponse(Call<List<Notice>> call, Response<List<Notice>> response) {
				Log.d(this.toString(), "response "+response.body());
				adapter.clear();
				for (Notice notice : response.body())
					adapter.addItem(notice);
				adapter.notifyDataSetChanged();
				showProgress(false);
			}

			@Override
			public void onFailure(Call<List<Notice>> call, Throwable t) {
				Log.d(this.toString(), "공지사항 가져오기 실패");
				if (getActivity() != null)
					Toast.makeText(getContext(), "공지사항 가져오기 실패", Toast.LENGTH_SHORT).show();
				showProgress(false);
			}
		};
	}

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
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
		APIClient.getService().listNotice().enqueue(callbackListNotice());
	}
}
