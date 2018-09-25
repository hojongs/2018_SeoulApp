package com.hojong.meokgol.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hojong.meokgol.R;
import com.hojong.meokgol.data_model.Shop;
//import com.nhn.android.maps.NMapContext;
//import com.nhn.android.maps.NMapView;

public class ShopMapFragment extends Fragment {
//	private NMapContext mMapContext;
	private static final String CLIENT_ID = "xU8vQG7PePFCSD0Qr6M6";// 애플리케이션 클라이언트 아이디 값

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_shop_map, null);
		Shop shop = (Shop) getArguments().getSerializable(Shop.INTENT_KEY);
		// TODO : shop position

		return rootView;
	}

//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		mMapContext =  new NMapContext(super.getActivity());
//		mMapContext.onCreate();
//	}
//
//	@Override
//	public void onActivityCreated(Bundle savedInstanceState) {
//		super.onActivityCreated(savedInstanceState);
//		NMapView mapView = (NMapView)getView().findViewById(R.id.mapView);
//		mapView.setClientId(CLIENT_ID);// 클라이언트 아이디 설정
//		mMapContext.setupMapView(mapView);
//	}
//
//	@Override
//	public void onStart(){
//		super.onStart();
//		mMapContext.onStart();
//	}
//
//	@Override
//	public void onResume() {
//		super.onResume();
//		mMapContext.onResume();
//	}
//
//	@Override
//	public void onPause() {
//		super.onPause();
//		mMapContext.onPause();
//	}
//
//	@Override
//	public void onStop() {
//		mMapContext.onStop();
//		super.onStop();
//	}
//
//	@Override
//	public void onDestroyView() {
//		super.onDestroyView();
//	}
//
//	@Override
//	public void onDestroy() {
//		mMapContext.onDestroy();
//		super.onDestroy();
//	}
}