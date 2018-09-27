package com.hojong.meokgol.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.hojong.meokgol.APIClient;
import com.hojong.meokgol.IShowableProgress;
import com.hojong.meokgol.R;
import com.hojong.meokgol.adapter.LocationListAdapter;
import com.hojong.meokgol.data_model.Location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class LogoActivity extends MyAppCompatActivity implements IShowableProgress
{
	private List<Location> locationList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logo);

        locationList = new ArrayList<>();
        attemptData();

//        LottieAnimationView logoView = findViewById(R.id.logo_view);
	}

    public void attemptData()
    {
        Call call = APIClient.getService().listLocation();
        callList.add(call);

        LocationListAdapter adapter = new LocationListAdapter() {
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
                Log.d(toString(), String.format("notifyDataSetChanged callList.size=%s", callList.size()));
                if (callList.size() > 0)
                    return;

                // is first check, init intent
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

                // start activity
                intent.putExtra(Location.INTENT_KEY, (Serializable) locationList);
                Log.d(this.toString(), "LocationList=" + intent.getSerializableExtra(Location.INTENT_KEY).toString());
                startActivity(intent);
                finish();

//                Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.logo_bg);
//                Log.d(this.toString(), "bmp width=" + bmp.getWidth() + ", height=" + bmp.getHeight());

//                ImageView logoView = findViewById(R.id.logo_view);
//                Log.d(this.toString(), "view width=" + logoView.getWidth() + ", height=" + logoView.getHeight());
            }
        };

        call.enqueue(adapter.callbackListLocation(this, callList));
    }

    @Override
    public void showProgress(boolean show) { }
    @Override
    public Activity getActivity() { return this; }
    @Override
    public Context getContext() { return getApplicationContext(); }
}
