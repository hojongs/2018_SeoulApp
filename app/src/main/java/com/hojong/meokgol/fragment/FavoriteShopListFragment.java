package com.hojong.meokgol.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hojong.meokgol.APIClient;
import com.hojong.meokgol.LoginSharedPreference;
import com.hojong.meokgol.MyCallback;
import com.hojong.meokgol.R;
import com.hojong.meokgol.adapter.ShopListAdapter;

import retrofit2.Call;

public class FavoriteShopListFragment extends MyFragment
{
    ListView listView;
    ShopListAdapter adapter;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_favorite_shop_list, null);

		listView = rootView.findViewById(R.id.favorite_list);
		mProgressView = rootView.findViewById(R.id.progress_bar);

		adapter = new ShopListAdapter();
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(adapter);

		return rootView;
	}

    @Override
    public void showProgress(final boolean show)
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
        int userIdx = LoginSharedPreference.getUserIdx(getContext());
        Call call = APIClient.getService().listFavoriteShop(userIdx); // TODO : 서버 측 미구현
        callList.add(call);
        call.enqueue(MyCallback.callbackShopList(this, callList, adapter, "즐겨찾기 가져오기 실패"));
    }
}
