package com.hojong.meokgol;

import com.hojong.meokgol.data_model.Location;
import com.hojong.meokgol.data_model.LoginInfo;
import com.hojong.meokgol.data_model.Shop;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService
{
	@GET("market_regions")
	Call<List<Location>> listMarketRegions();

	@GET("stores/{market_region}")
	Call<List<Shop>> listStores(@Path("market_region") String marketRegion);

	@POST("login")
	Call<ResponseBody> loginWithCredential(@Body LoginInfo data);
}
