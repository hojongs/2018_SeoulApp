package com.hojong.meokgol.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hojong.meokgol.APIClient;
import com.hojong.meokgol.IShowProgress;
import com.hojong.meokgol.MyCallback;
import com.hojong.meokgol.R;
import com.hojong.meokgol.adapter.MyListAdapter;
import com.hojong.meokgol.data_model.Location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class LogoActivity extends MyAppCompatActivity implements IShowProgress
{
	private List<Location> locationList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logo);

        locationList = new ArrayList<>();
        attemptData();
	}

    public void attemptData()
    {
        Call call = APIClient.getService().listLocation();
        callList.add(call);
        call.enqueue(MyCallback.callbackListLocation(this, callList, new MyListAdapter() {
            @Override
            public void clear() { locationList.clear(); }
            @Override
            public void addItem(Object i) { locationList.add((Location)i); }
            @Override
            public int getCount() { return 0; }
            @Override
            public Object getItem(int position) { return null; }
            @Override
            public long getItemId(int position) { return 0; }
            @Override
            public View getView(int position, View convertView, ViewGroup parent) { return null; }
            @Override
            public void notifyDataSetChanged() {
                if (callList.size() > 0)
                    return;

                // 이미지까지 모두 로드되면 다음 액티비티로 전환
                Intent intent;
                SharedPreferences pref = getSharedPreferences("isFirst", Activity.MODE_PRIVATE);
                boolean isFirst = pref.getBoolean("isFirst", false);
                isFirst = false; // TODO : 마지막 구현완료 후 (lazy)

                if(isFirst)
                {
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("isFirst",true);
                    editor.apply();

                    intent = new Intent(getApplicationContext(), TutorialActivity.class);
                }
                else {
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                }

                Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.logo_bg);
                Log.d(this.toString(), "bmp width=" + bmp.getWidth() + ", height=" + bmp.getHeight());

                ImageView logoView = findViewById(R.id.logo_view);
                Log.d(this.toString(), "view width=" + logoView.getWidth() + ", height=" + logoView.getHeight());

                intent.putExtra(Location.INTENT_KEY, (Serializable) locationList);
                Log.d(LogoActivity.this.toString(), "LocationList=" + intent.getSerializableExtra(Location.INTENT_KEY).toString());
                startActivity(intent);
                finish();
            }
        }));
    }

    @Override
    public void showProgress(boolean show) { }
    @Override
    public Activity getActivity() { return this; }
    @Override
    public Context getContext() { return getApplicationContext(); }
}
