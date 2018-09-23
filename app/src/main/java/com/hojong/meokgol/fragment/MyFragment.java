package com.hojong.meokgol.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.hojong.meokgol.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;

public abstract class MyFragment extends Fragment {
    protected View mProgressView;
    protected List<Call> callList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        callList = new ArrayList<>();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_refresh, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        attemptData();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public abstract void showProgress(final boolean show);

    @CallSuper
    public void attemptData()
    {
        Log.d(this.toString(), "attemptData");
        if (callList.size() > 0)
            return;

        showProgress(true);
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        for (Call call : callList)
            call.cancel();
    }
}
