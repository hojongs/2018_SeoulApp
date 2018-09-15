package com.hojong.a2018_seoulapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIService
{
	@GET("market_regions")
	Call<List<MarketRegion>> listMarketRegions();

	@GET("{market_region}/stores")
	Call<List<Store>> listStores(@Path("market_region") String marketRegion);
}
