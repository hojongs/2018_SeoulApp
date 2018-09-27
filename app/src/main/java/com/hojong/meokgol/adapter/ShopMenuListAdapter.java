package com.hojong.meokgol.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hojong.meokgol.R;
import com.hojong.meokgol.data_model.ShopMenu;

import java.util.ArrayList;
import java.util.List;

public class ShopMenuListAdapter extends BaseAdapter
{
	private List<ShopMenu> menuDataList = new ArrayList<>();

	@Override
	public int getCount() {
		return menuDataList.size() ;
	}

	@Override
	public View getView(int position, View noticeView, ViewGroup parent) {
		final Context context = parent.getContext();

		if (noticeView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			noticeView = inflater.inflate(R.layout.layout_menu_list_item, parent, false);
		}
        ShopMenu menu = menuDataList.get(position);

		TextView nameView = noticeView.findViewById(R.id.menu_name_view);
		nameView.setText(menu.menu_name);

		// TODO : menu list view

		return noticeView;
	}

	@Override
	public long getItemId(int position) {
		return position ;
	}
	
	@Override
	public Object getItem(int position) {
		return menuDataList.get(position) ;
	}

	public void addItem(ShopMenu menu) {
		menuDataList.add(menu);
	}

	public void clear() {
		menuDataList.clear();
	}
}
