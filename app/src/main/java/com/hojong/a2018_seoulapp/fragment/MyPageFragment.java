package com.hojong.a2018_seoulapp.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hojong.a2018_seoulapp.activity.LoginActivity;
import com.hojong.a2018_seoulapp.R;

public class MyPageFragment extends Fragment {
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_mypage, null);

		View loginBtn = rootView.findViewById(R.id.login_btn);
		loginBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getContext(), LoginActivity.class);
				startActivityForResult(intent, Activity.RESULT_OK);
			}
		});

		return rootView;
	}
}
