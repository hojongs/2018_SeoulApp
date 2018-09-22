package com.hojong.meokgol;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.gson.JsonObject;
import com.hojong.meokgol.data_model.Location;
import com.hojong.meokgol.data_model.Notice;
import com.hojong.meokgol.data_model.ShopReview;
import com.hojong.meokgol.data_model.User;

import org.junit.Test;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ReprofitUnitTest
{
	private APIService service;

	public ReprofitUnitTest()
	{
		service = APIClient.getService();
	}

	@Test
	public void login() throws Exception
	{
		Response<JsonObject> response = service.login(new User("guest", "guest")).execute();
		assertEquals(200, response.code());
		System.out.println(response.body());
	}

	@Test
	public void getUserInfo() throws Exception
	{
		Response<User> response = service.getUserInfo(1).execute();
		assertEquals(200, response.code());
		System.out.println(response.body());
	}

	@Test
	public void listLocation() throws Exception
	{
		Response<List<Location>> response = service.listLocation().execute();
		assertEquals(200, response.code());
		System.out.println(response.body());
	}

	@Test
	public void listReview() throws Exception
	{
		Response<List<ShopReview>> response = service.listReview().execute();
		assertEquals(200, response.code());
		System.out.println(response.body());
	}

	@Test
	public void writeReview() throws Exception
	{
		ShopReview review = new ShopReview("test_title1", "test_content1", 5, 1, 1);

		Response<JsonObject> response = service.writeReview(review).execute();
		assertEquals(200, response.code());
		assertEquals(true, response.body().get("success").getAsBoolean());
	}

	@Test
	public void deleteReview() throws Exception
	{
		ShopReview review = new ShopReview();
		review.user_idx = 1;
		review.review_idx = 1;

		Response<JsonObject> response = service.deleteReview(review).execute();
		assertEquals(200, response.code());
		System.out.println(response.body());
	}

	@Test
	public void listNotice() throws Exception
	{
		Response<List<Notice>> response = service.listNotice().execute();
		assertEquals(200, response.code());
		System.out.println(response.body());
	}

	@Test
	public void loadImage() throws Exception
	{
		APIClient.getService().loadImage("http://wwwns.akamai.com/media_resources/globe_emea.png").enqueue(new Callback<ResponseBody>() {
			@Override
			public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
				System.out.println(response);
				Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
			}

			@Override
			public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
				System.out.println(t.toString());
			}
		});
	}
}
