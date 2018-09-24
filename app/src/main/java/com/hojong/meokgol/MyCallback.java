package com.hojong.meokgol;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Adapter;
import android.widget.Toast;

import com.hojong.meokgol.activity.ShopListActivity;
import com.hojong.meokgol.adapter.ShopListAdapter;
import com.hojong.meokgol.data_model.Shop;
import com.hojong.meokgol.fragment.MyFragment;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCallback {
    public static Callback<List<Shop>> callbackShopList(final ShopListActivity activity, final MyFragment fragment, final List<Call> callList, final ShopListAdapter adapter, final String failureMsg)
    {
        return new Callback<List<Shop>>() {
            @Override
            public void onResponse(Call<List<Shop>> call, Response<List<Shop>> response) {
                Log.d(this.toString(), "response " + response.body());
                callList.remove(call);
                adapter.clear();
                for (Shop i : response.body()) {
                    Log.d(this.toString(), "shop_img="+i.shop_img);
                    Call call2 = APIClient.getService().loadImage(i.shop_img);
                    callList.add(call2);
                    call2.enqueue(callbackLoadImage(i, activity, fragment, callList, adapter));
                    adapter.addItem(i);
                }
                adapter.notifyDataSetChanged();
//				showProgress(false);
            }

            @Override
            public void onFailure(Call<List<Shop>> call, Throwable t) {
                Log.d(this.toString(), failureMsg + t.toString());
                callList.remove(call);

                boolean show = false;
                if (activity != null) {
                    Toast.makeText(activity, failureMsg, Toast.LENGTH_SHORT).show();
                    activity.showProgress(show);
                }
                else {
                    if (fragment.getActivity() == null)
                        return;

                    Toast.makeText(fragment.getActivity(), failureMsg, Toast.LENGTH_SHORT).show();
                    fragment.showProgress(show);
                }
            }
        };
    }

    public static Callback<ResponseBody> callbackLoadImage(final Shop i, final ShopListActivity activity, final MyFragment fragment, final List<Call> callList, final ShopListAdapter adapter)
    {
        return new Callback<ResponseBody>() {
            Shop obj = i;

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(this.toString(), "response " + response.body());
                callList.remove(call);
                Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                bmp = Bitmap.createScaledBitmap(bmp, 200, 200, true);

                obj.bmp = bmp;
                adapter.notifyDataSetChanged();

                boolean show = false;
                if (activity != null) {
                    activity.showProgress(show);
                }
                else {
                    fragment.showProgress(show);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(this.toString(), "이미지 가져오기 실패"+t.toString());
                callList.remove(call);

                boolean show = false;
                if (activity != null) {
                    Toast.makeText(activity, "이미지 가져오기 실패", Toast.LENGTH_SHORT).show();
                    activity.showProgress(show);
                }
                else {
                    if (fragment.getActivity() == null)
                        return;

                    Toast.makeText(fragment.getActivity(), "이미지 가져오기 실패", Toast.LENGTH_SHORT).show();
                    fragment.showProgress(show);
                }
            }
        };
    }
}
