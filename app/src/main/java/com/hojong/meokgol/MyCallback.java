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

public class MyCallback {
    public static Callback<List<Location>> callbackListLocation(final IShowProgress fragment, final List<Call> callList, @Nullable  final MyListAdapter adapter) {
        return new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                Log.d(this.toString(), "callbackListLocation " + response.body());
                callList.remove(call);
                adapter.clear();
                for (Location i : response.body()) {
                    Log.d(this.toString(), "location_img="+i.location_img);
                    Call call2 = APIClient.getService().loadImage(i.location_img);
                    callList.add(call2);
                    call2.enqueue(callbackLoadImage(i, fragment, callList, adapter));
                    adapter.addItem(i);
                }
                adapter.notifyDataSetChanged();
//				showProgress(false);
            }

            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                Log.d(this.toString(), "지역 가져오기 실패 " + t.toString());
                callList.remove(call);
                if (fragment.getActivity() != null)
                    Toast.makeText(fragment.getContext(), "지역 가져오기 실패", Toast.LENGTH_SHORT).show();
                fragment.showProgress(false);
            }
        };
    }

    public static Callback<List<Shop>> callbackShopList(final IShowProgress fragment, final List<Call> callList, final MyListAdapter adapter, final String failureMsg)
    {
        return new Callback<List<Shop>>() {
            @Override
            public void onResponse(Call<List<Shop>> call, Response<List<Shop>> response) {
                Log.d(this.toString(), "callbackShopList " + response.body());
                callList.remove(call);
                adapter.clear();
                for (Shop i : response.body()) {
                    Log.d(this.toString(), "shop_img="+i.shop_img);
                    Call call2 = APIClient.getService().loadImage(i.shop_img);
                    callList.add(call2);
                    call2.enqueue(callbackLoadImage(i, fragment, callList, adapter));
                    adapter.addItem(i);
                }
                adapter.notifyDataSetChanged();
//				showProgress(false);
            }

            @Override
            public void onFailure(Call<List<Shop>> call, Throwable t) {
                Log.d(this.toString(), failureMsg + t.toString());
                callList.remove(call);

                if (fragment.getActivity() == null)
                    return;

                Toast.makeText(fragment.getActivity(), failureMsg, Toast.LENGTH_SHORT).show();
                fragment.showProgress(false);
            }
        };
    }

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

    public static Callback<JsonObject> callbackAddFavoriteShop(final Context context)
    {
        return new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                // TODO : if success
                if (response.code() == 200) // && response.body().get("success"))
                    Toast.makeText(context, "즐겨찾기 추가", Toast.LENGTH_SHORT).show();
                else
                    Log.d(toString(), "callbackAddFavoriteShop.msg=" + response.message());
                    Toast.makeText(context, "즐겨찾기 추가 실패", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(context, "즐겨찾기 추가 실패", Toast.LENGTH_SHORT).show();
            }
        };
    }
}
