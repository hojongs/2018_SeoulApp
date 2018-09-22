package com.hojong.meokgol.data_model;

import android.graphics.Bitmap;

public class Shop
{
    public Bitmap bmp;
	public int shop_idx;
	public String shop_name;
	public String shop_info;
	public String shop_img;
	public int location_idx;


	public Shop (String shop_name)
	{
        this.shop_name = shop_name;
    }
}
