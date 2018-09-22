package com.hojong.meokgol;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.hojong.meokgol.activity.ShopActivity;
import com.hojong.meokgol.activity.ShopListActivity;

public class LocationClickListener implements AdapterView.OnItemClickListener {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Context context = adapterView.getContext();
        Intent intent = new Intent(context, ShopListActivity.class);
        // TODO : intent.putExtra(""); // REST API
        context.startActivity(intent);
    }
}
