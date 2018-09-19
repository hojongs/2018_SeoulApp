package com.hojong.meokgol.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hojong.meokgol.R;
import com.hojong.meokgol.adapter.ShopListAdapter;
import com.hojong.meokgol.ShopListItemClickListener;
import com.hojong.meokgol.data_model.Shop;

public class NoticeListFragment extends Fragment
{
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_notice_list, null);

		ListView listView = rootView.findViewById(R.id.notice_list);

		// TODO
		ShopListAdapter adapter = new ShopListAdapter();
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new ShopListItemClickListener());

		adapter.addItem(new Shop(ContextCompat.getDrawable(getActivity(), R.drawable.ic_home_black_24dp), "공지1"));
		adapter.addItem(new Shop(ContextCompat.getDrawable(getActivity(), R.drawable.ic_notifications_black_24dp), "공지2"));

		return rootView;
	}
}
