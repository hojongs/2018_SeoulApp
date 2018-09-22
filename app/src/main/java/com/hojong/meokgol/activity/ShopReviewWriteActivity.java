package com.hojong.meokgol.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.hojong.meokgol.APIClient;
import com.hojong.meokgol.LoginSharedPreference;
import com.hojong.meokgol.R;
import com.hojong.meokgol.data_model.ShopReview;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopReviewWriteActivity extends AppCompatActivity {
    EditText titleView;
    EditText contentsView;
    View writeView;
    View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_review_write);
        
        titleView = findViewById(R.id.review_title);
        contentsView = findViewById(R.id.review_contents);
        writeView = findViewById(R.id.write_form);
        mProgressView = findViewById(R.id.progress_bar);

        // set visible back arrow button
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        View writeBtn = findViewById(R.id.writeBtn);
        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptData();
            }
        });

        View cancelBtn = findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
    
    private Callback<JsonObject> callbackWriteReview()
    {
        return new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Toast.makeText(getApplicationContext(), "리뷰 작성 완료", Toast.LENGTH_SHORT).show();
                showProgress(false);
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "리뷰 작성 실패", Toast.LENGTH_SHORT).show();
            }
        };
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    protected void showProgress(final boolean show)
    {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        writeView.setVisibility(show ? View.GONE : View.VISIBLE);
        writeView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                writeView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    // 뒤로가기 버튼 onClick
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void attemptData()
    {
        Intent intent = new Intent();
        int shopIdx = intent.getIntExtra("shopIdx", -1);
        if (shopIdx == -1) {
            Log.d(this.toString(), "wrong shopIdx=-1");
            showProgress(false);
            return;
        }

        ShopReview review = new ShopReview(titleView.getText().toString(), contentsView.getText().toString(), 5, LoginSharedPreference.getUserIdx(getApplicationContext()), shopIdx);

        showProgress(true);
        APIClient.getService().writeReview(review).enqueue(callbackWriteReview());
    }
}
