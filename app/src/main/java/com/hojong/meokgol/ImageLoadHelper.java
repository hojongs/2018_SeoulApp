package com.hojong.meokgol;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.hojong.meokgol.adapter.MyListAdapter;
import com.hojong.meokgol.data_model.BmpModel;
import com.hojong.meokgol.data_model.Location;
import com.hojong.meokgol.data_model.Shop;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageLoadHelper {
    public static Callback<ResponseBody> callbackLoadImage(final BmpModel i, final IShowProgress fragment, final List<Call> callList, final MyListAdapter adapter)
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
                adapter.notifyDataSetChanged();
                fragment.showProgress(false);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(this.toString(), "이미지 가져오기 실패"+t.toString());
                callList.remove(call);

                if (fragment.getActivity() == null)
                    return;

                Toast.makeText(fragment.getContext(), "이미지 가져오기 실패", Toast.LENGTH_SHORT).show();
                fragment.showProgress(false);
            }
        };
    }
}
