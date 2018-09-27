package com.hojong.meokgol.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.hojong.meokgol.APIClient;
import com.hojong.meokgol.APIService;
import com.hojong.meokgol.IShowProgress;
import com.hojong.meokgol.ImageLoadHelper;
import com.hojong.meokgol.R;
import com.hojong.meokgol.activity.ShopActivity;
import com.hojong.meokgol.data_model.Shop;
import com.hojong.meokgol.fragment.MyPageFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopListAdapter extends MyListAdapter implements AdapterView.OnItemClickListener, View.OnClickListener
{
	private List<Shop> shopDataList = new ArrayList<>();

	@Override
	public int getCount() {
		return shopDataList.size() ;
	}

	@Override
	public View getView(int position, View shopView, ViewGroup parent) {
		final Context context = parent.getContext();

		// "listview_item" Layout을 inflate하여 convertView 참조 획득.
		if (shopView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			shopView = inflater.inflate(R.layout.layout_shop_list_item, parent, false);
		}

		Shop shop = shopDataList.get(position);

		// shop bmp
		ImageView shopImageView = shopView.findViewById(R.id.location_img);
		shopImageView.setImageBitmap(shop.getBmp());
		// shop name
		TextView shopNameView = shopView.findViewById(R.id.shop_name);
		shopNameView.setText(shop.shop_name);

		// TODO : shop score
        // TODO : review count
        // TODO : favorite checked

		// click listener
		ImageButton shopFavoriteBtn = shopView.findViewById(R.id.favorite_btn);
		shopFavoriteBtn.setOnClickListener(this);

		return shopView;
	}

	@Override
	public long getItemId(int position) {
		return position ;
	}
	
	@Override
	public Object getItem(int position) {
		return shopDataList.get(position) ;
	}

	public void addItem(Object shop) {
		shopDataList.add((Shop) shop);
	}

	public void clear()
    {
        shopDataList.clear();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
	    // list item click
        Context context = adapterView.getContext();
        Shop shop = shopDataList.get(i);

        // TODO : APIClient.getService().getShopInfo(shop.shop_idx).enqueue();

        Intent intent = new Intent(context, ShopActivity.class);
        Log.d(toString(), "shop="+shop);
        intent.putExtra(Shop.INTENT_KEY, shop);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
	    // add fav shop
        ViewGroup listView = (ViewGroup) view.getParent().getParent();
        View itemView = (View) view.getParent();

        int userIdx = MyPageFragment.user.user_idx; // TODO : dirty code
        Shop shop = shopDataList.get(listView.indexOfChild(itemView));
        Log.d(toString(), "onClick.item="+shop);

        Log.d(toString(), String.format("addFavorite userIdx=%s,shopIdx=%s", userIdx, shop.shop_idx));
        APIClient.getService().addFavoriteShop(userIdx, shop.shop_idx).enqueue(callbackAddFavoriteShop(listView.getContext()));
    }

    public static Callback<List<Shop>> callbackShopList(final IShowProgress fragment, final List<Call> callList, final MyListAdapter adapter, final String failureMsg)
    {
        return new Callback<List<Shop>>() {
            @Override
            public void onResponse(Call<List<Shop>> call, Response<List<Shop>> response) {
                Log.d(this.toString(), String.format("callbackShopList msg=%s,shopList=%s", response.message(), response.body()));
                if (response.code() == 200) {
                    callList.remove(call);
                    adapter.clear();
                    for (Shop i : response.body()) {
                        Log.d(this.toString(), "shop_img=" + i.shop_img);
                        Call call2 = APIClient.getService().loadImage(i.shop_img);
                        callList.add(call2);
                        call2.enqueue(ImageLoadHelper.callbackLoadImage(i, fragment, callList, adapter));
                        adapter.addItem(i);
                    }
                    adapter.notifyDataSetChanged();
//				    showProgress(false);
                }
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

    public static Callback<JsonObject> callbackAddFavoriteShop(final Context context)
    {
        return new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.code() == 200 && response.body().get("success").getAsBoolean())
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
