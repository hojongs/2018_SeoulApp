package com.hojong.meokgol.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hojong.meokgol.R;
import com.hojong.meokgol.data_model.Shop;

public class ShopInfoFragment extends Fragment {
    @Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_shop_info, null);

		Shop shop = (Shop) getArguments().getSerializable(Shop.INTENT_KEY);
        Log.d(this.toString(), "shopInfo=" + shop.shop_info);

        TextView shopInfoView = rootView.findViewById(R.id.shop_info_view);
        shopInfoView.setText(shop.shop_info); // TODO : 가독성 (lazy)

		return rootView;
	}
}
