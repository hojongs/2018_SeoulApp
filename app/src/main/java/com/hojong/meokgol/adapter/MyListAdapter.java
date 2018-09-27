package com.hojong.meokgol.adapter;

import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.hojong.meokgol.IShowableProgress;
import com.hojong.meokgol.data_model.BmpModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class MyListAdapter extends BaseAdapter
{
    public abstract void clear();
    public abstract void addItem(Object i);

    public Callback<ResponseBody> callbackLoadImage(final BmpModel i, final IShowableProgress fragment, final List<Call> callList)
    {
        return new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(this.toString(), "callbackLoadImage " + response.body());
                callList.remove(call);
                if (response.code() == 200) {
                    String url = call.request().url().toString();
                    i.setBmp(response.body().byteStream(), url.substring(url.length() - 3));
                }
                notifyDataSetChanged();
                fragment.showProgress(false);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(this.toString(), "이미지 가져오기 실패"+t.toString());
                callList.remove(call);

                if (fragment.getActivity() == null)
                    return;

                Toast.makeText(fragment.getContext(), "이미지 가져오기 실패", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
                fragment.showProgress(false);
            }
        };
    }
}
