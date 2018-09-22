package com.hojong.meokgol.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.hojong.meokgol.APIClient;
import com.hojong.meokgol.LoginSharedPreference;
import com.hojong.meokgol.R;
import com.hojong.meokgol.adapter.ShopListAdapter;
import com.hojong.meokgol.ShopClickListener;
import com.hojong.meokgol.data_model.Shop;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
		listView.setOnItemClickListener(new ShopClickListener());

		return rootView;
	}

    @Override
    protected void showProgress(final boolean show)
    {
        // TODO
        if (true) // (getActivity() == null)
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
        // TODO : fav shop list
        int userIdx = LoginSharedPreference.getUserIdx(getContext());
        APIClient.getService().listFavoriteShop(userIdx).enqueue(callbackShopList());
//        adapter.addItem(new Shop(ContextCompat.getDrawable(getActivity(), R.drawable.ic_home_black_24dp), "즐겨찾기 가게1"));
//        adapter.addItem(new Shop(ContextCompat.getDrawable(getActivity(), R.drawable.ic_notifications_black_24dp), "즐겨찾기 가게2"));
//        APIClient.getService().listLocation().enqueue(callbackListLocation());
    }

    // TODO : 중복코드(id=4)
    private Callback<List<Shop>> callbackShopList()
    {
        return new Callback<List<Shop>>() {
            @Override
            public void onResponse(Call<List<Shop>> call, Response<List<Shop>> response) {
                Log.d(this.toString(), "response " + response.body());
                adapter.clear();
                for (Shop i : response.body()) {
                    Log.d(this.toString(), "shop_img="+i.shop_img);
                    APIClient.getService().loadImage(i.shop_img).enqueue(callbackLoadImage(i));
                    adapter.addItem(i);
                }
                adapter.notifyDataSetChanged();
//				showProgress(false);
            }

            @Override
            public void onFailure(Call<List<Shop>> call, Throwable t) {
                Log.d(this.toString(), "가게 가져오기 실패");
                if (getActivity() != null)
                    Toast.makeText(getContext(), "가게 가져오기 실패", Toast.LENGTH_SHORT).show();
                showProgress(false);
            }
        };
    }

    // TODO : 중복코드(id=4)
    private Callback<ResponseBody> callbackLoadImage(final Shop i)
    {
        return new Callback<ResponseBody>() {
            Shop obj = i;

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                bmp = Bitmap.createScaledBitmap(bmp, 200, 200, true);
                Log.d(this.toString(), "response " + response.body() + "," + bmp.getWidth() + "," + bmp.getHeight());

                obj.bmp = bmp;
                adapter.notifyDataSetChanged();
                showProgress(false);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(this.toString(), "이미지 가져오기 실패"+t.toString());
                if (getActivity() != null)
                    Toast.makeText(getContext(), "이미지 가져오기 실패", Toast.LENGTH_SHORT).show();
                showProgress(false);
            }
        };
    }
}
