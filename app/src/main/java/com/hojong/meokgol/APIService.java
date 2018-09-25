package com.hojong.meokgol;

import com.google.gson.JsonObject;
import com.hojong.meokgol.data_model.Location;
import com.hojong.meokgol.data_model.Notice;
import com.hojong.meokgol.data_model.Shop;
import com.hojong.meokgol.data_model.ShopReview;
import com.hojong.meokgol.data_model.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface APIService
{
	@POST("/login")
	Call<JsonObject> login(@Body User user);

	@GET("/{userIdx}/userinfo")
	Call<User> getUserInfo(@Path("userIdx") int userIdx);

	@GET("/location")
	Call<List<Location>> listLocation();

	@GET("/shops/{locationIdx}")
	Call<List<Shop>> listShop(@Path("locationIdx") int locationIdx, @Query("menu") String menu);

	@GET("/{userIdx}/favoriteshop")
	Call<List<Shop>> listFavoriteShop(@Path("userIdx") int userIdx);

	@POST("/{userIdx}/favoriteshop")
    Call<JsonObject> addFavoriteShop(@Query("shop_idx") int shopIdx);

	@GET("/review/test")
	Call<List<ShopReview>> listReview();

	@POST("/review/write")
	Call<JsonObject> writeReview(@Body ShopReview data);

	@POST("/review/delete") // TODO : delete review
	Call<JsonObject> deleteReview(@Body ShopReview data);

	@GET("/notice")
	Call<List<Notice>> listNotice();

	@GET
	Call<ResponseBody> loadImage(@Url String url);
}
