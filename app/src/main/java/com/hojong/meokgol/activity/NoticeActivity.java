package com.hojong.meokgol.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.hojong.meokgol.R;
import com.hojong.meokgol.data_model.Notice;

public class NoticeActivity extends AppCompatActivity {
    Notice notice;
    TextView titleView;
    TextView contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        notice = (Notice) getIntent().getSerializableExtra(Notice.INTENT_KEY);

        titleView = findViewById(R.id.notice_title_view);
        titleView.setText(notice.notice_title);

        contentView = findViewById(R.id.notice_content_view);
        contentView.setText(notice.notice_content);
    }
}
