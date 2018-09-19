package com.hojong.a2018_seoulapp;

import com.hojong.a2018_seoulapp.data_model.Location;
import com.hojong.a2018_seoulapp.data_model.LoginCredential;
import com.hojong.a2018_seoulapp.data_model.Shop;

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
	Call<ResponseBody> loginWithCredential(@Body LoginCredential data);
}
