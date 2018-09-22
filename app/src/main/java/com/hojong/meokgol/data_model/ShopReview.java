package com.hojong.meokgol.data_model;

import android.graphics.drawable.Drawable;

import java.sql.Timestamp;

public class ShopReview
{
//	public Drawable img;
//	public String name;
	public int review_idx;
	public String review_title;
	public String review_content;
	public int review_star;
	public Timestamp review_date;
	public int user_idx;
	public int shop_idx;

//	public ShopReview(Drawable img, String name)
//	{
//		this.img = img;
//		this.name = name;
//	}

	@Override
	public String toString()
	{
		return review_idx + ":" + review_title;
	}
}
