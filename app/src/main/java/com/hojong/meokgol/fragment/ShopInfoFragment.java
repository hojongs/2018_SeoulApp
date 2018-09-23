package com.hojong.meokgol.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hojong.meokgol.R;

public class ShopInfoFragment extends Fragment {
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_shop_info, null);

		Intent intent = new Intent();
        String shopInfo = intent.getStringExtra("shopInfo");
        Log.d(this.toString(), "shopInfo=" + shopInfo); // TODO : 더미 데이터 후 (Frst)

		return rootView;
	}
}
