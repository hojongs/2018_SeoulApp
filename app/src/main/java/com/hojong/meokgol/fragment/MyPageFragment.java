package com.hojong.meokgol.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hojong.meokgol.LoginSharedPreference;
import com.hojong.meokgol.activity.LoginActivity;
import com.hojong.meokgol.R;
import com.hojong.meokgol.data_model.LoginInfo;

public class MyPageFragment extends Fragment implements View.OnClickListener{
	public static final int LOGIN_SUCCESS = 0;
	View loginBtn;
	View logoutBtn;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		LoginInfo loginInfo = LoginSharedPreference.getUserInfo(getContext());
		View rootView;

		Log.d(this.toString(), "userId : " + loginInfo.userId);

		if (loginInfo.userId.length() == 0) {
			rootView = inflater.inflate(R.layout.fragment_mypage_login, null);

			loginBtn = rootView.findViewById(R.id.login_btn);
			loginBtn.setOnClickListener(this);
		}
		else
		{
			rootView = inflater.inflate(R.layout.fragment_mypage, null);

			logoutBtn = rootView.findViewById(R.id.logout_btn);
			logoutBtn.setOnClickListener(this);
		}

		return rootView;
	}

	@Override
	public void onClick(View view) {
		if (view == loginBtn)
			startLoginActivity();
		else if (view == logoutBtn) {
			logout();
			refreshFragment();
		}
	}

	private void startLoginActivity()
	{
		Intent intent = new Intent(getContext(), LoginActivity.class);
		startActivityForResult(intent, LOGIN_SUCCESS);
	}

	private void logout()
	{
		// TODO : Progress bar
		LoginSharedPreference.putLoginInfo(getContext());
	}

	private void refreshFragment()
	{
		getFragmentManager().beginTransaction()
				.detach(this)
				.attach(this)
				.commit();
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.d(this.toString(), "resultCode: "+ resultCode);

		if (resultCode == requestCode)
		{
			refreshFragment();
		}
	}
}
