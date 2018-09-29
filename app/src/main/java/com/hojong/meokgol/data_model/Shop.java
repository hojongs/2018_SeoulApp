package com.hojong.meokgol.data_model;

import java.io.Serializable;
import java.util.List;

public class Shop implements Serializable
{
    public static final String INTENT_KEY = "shop";
	public int shop_idx;
	public String shop_name;
	public String shop_info;
	public String shop_img;
	public int location_idx;
	public List<ShopMenu> menu_list;
	public String shop_phone;
	public double review_avg;
	public int review_count;
	public int favorite;
	public double latitude; // 위도
	public double longitude; // 경도

	public Shop (String shop_name)
	{
	    shop_idx = 1;
        this.shop_name = shop_name;
    }

    public void merge(Shop recv_shop)
	{
		review_avg = recv_shop.review_avg;
		review_count = recv_shop.review_count;

		menu_list = recv_shop.menu_list;
		shop_phone = recv_shop.shop_phone;

		latitude = recv_shop.latitude;
		longitude = recv_shop.longitude;
	}

    @Override
    public String toString()
    {
        return String.format("Shop(%s:%s/menuList=%s/favorite=%s)", shop_idx, shop_name, menu_list, favorite);
    }
}
