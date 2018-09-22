package com.hojong.meokgol.data_model;

import android.graphics.Bitmap;

public class Location
{
	public Bitmap bmp;
	public int location_idx;
	public String location_name;
	public String location_info;
	public String location_img;

	@Override
	public String toString()
	{
		return location_idx+":"+location_name;
	}
}
