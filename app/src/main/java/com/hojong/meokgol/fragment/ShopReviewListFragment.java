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
import android.widget.ImageButton;
import android.widget.ListView;

import com.hojong.meokgol.R;
import com.hojong.meokgol.activity.ShopReviewWriteActivity;
import com.hojong.meokgol.adapter.ShopReviewListAdapter;
import com.hojong.meokgol.data_model.Location;
import com.hojong.meokgol.data_model.ShopReview;

import static android.app.Activity.RESULT_OK;

public class ShopReviewListFragment extends Fragment {
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_shop_review_list, null);

		ListView shopReviewListView = rootView.findViewById(R.id.shop_review_list);
		ShopReviewListAdapter adapter = new ShopReviewListAdapter();
		shopReviewListView.setAdapter(adapter);

		// TODO : ShopReview
//		adapter.addItem(new ShopReview(ContextCompat.getDrawable(getActivity(), R.drawable.ic_dashboard_black_24dp), "Review1"));
//		adapter.addItem(new ShopReview(ContextCompat.getDrawable(getActivity(), R.drawable.ic_home_black_24dp), "Review2"));
//		adapter.addItem(new ShopReview(ContextCompat.getDrawable(getActivity(), R.drawable.ic_home_black_24dp), "Review3"));
//		adapter.addItem(new ShopReview(ContextCompat.getDrawable(getActivity(), R.drawable.ic_home_black_24dp), "Review4"));
//		adapter.addItem(new ShopReview(ContextCompat.getDrawable(getActivity(), R.drawable.ic_home_black_24dp), "Review5"));

		ImageButton reviewWriteBtn = rootView.findViewById(R.id.review_write_btn);
		reviewWriteBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getContext(), ShopReviewWriteActivity.class);

				startActivityForResult(intent, RESULT_OK);
			}
		});

		return rootView;
	}
}
