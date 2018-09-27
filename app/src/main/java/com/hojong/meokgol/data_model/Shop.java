package com.hojong.meokgol.data_model;

import java.io.Serializable;
import java.util.List;

public class Shop extends BmpModel implements Serializable
{
    public static final String INTENT_KEY = "shop";
	public int shop_idx;
	public String shop_name;
	public String shop_info;
	public String shop_img;
	public int location_idx;
	public List<Menu> menu_list;

	public Shop (String shop_name)
	{
	    shop_idx = 1;
        this.shop_name = shop_name;
    }

    @Override
    public String toString()
    {
        return String.format("%s:%s/menuList=%s", shop_idx, shop_name, menu_list);
    }
}
