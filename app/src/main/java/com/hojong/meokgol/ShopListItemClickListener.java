package com.hojong.meokgol;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.hojong.meokgol.activity.ShopActivity;

public class ShopListItemClickListener implements AdapterView.OnItemClickListener {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Context context = adapterView.getContext();
        Intent intent = new Intent(context, ShopActivity.class);
        // TODO : intent.putExtra("");
        context.startActivity(intent);
    }
}
