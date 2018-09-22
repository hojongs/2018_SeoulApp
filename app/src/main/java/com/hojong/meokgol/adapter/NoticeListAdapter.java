package com.hojong.meokgol.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hojong.meokgol.R;
import com.hojong.meokgol.data_model.Location;
import com.hojong.meokgol.data_model.Notice;

import java.util.ArrayList;
import java.util.List;

public class NoticeListAdapter extends BaseAdapter
{
	private List<Notice> noticeDataList = new ArrayList<>();

	@Override
	public int getCount() {
		return noticeDataList.size() ;
	}

	@Override
	public View getView(int position, View noticeView, ViewGroup parent) {
		final Context context = parent.getContext();

		if (noticeView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			noticeView = inflater.inflate(R.layout.layout_notice_list_item, parent, false);
		}

		// get view
		TextView titleView = noticeView.findViewById(R.id.view_notice_title);
		// get data
		Notice notice = noticeDataList.get(position);
		// set data to view
		titleView.setText(notice.notice_title);

		return noticeView;
	}

	@Override
	public long getItemId(int position) {
		return position ;
	}
	
	@Override
	public Object getItem(int position) {
		return noticeDataList.get(position) ;
	}

	public void addItem(Notice notice) {
		noticeDataList.add(notice);
	}

	public void clear() {
		noticeDataList.clear();
	}
}
