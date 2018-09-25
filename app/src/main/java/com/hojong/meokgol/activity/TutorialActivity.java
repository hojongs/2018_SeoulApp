package com.hojong.meokgol.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;

import com.hojong.meokgol.R;
import com.hojong.meokgol.data_model.Location;

import java.io.Serializable;
import java.util.List;

public class TutorialActivity extends AppCompatActivity
{

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link FragmentPagerAdapter} derivative, which will keep every
	 * loaded fragment in memory. If this becomes too memory intensive, it
	 * may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	private SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private ViewPager mViewPager;
	public Serializable locationList;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tutorial);

		locationList = getIntent().getSerializableExtra(Location.INTENT_KEY);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.container);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// Images left navigation_main
		View leftNav = findViewById(R.id.left_nav);
		leftNav.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int tab = mViewPager.getCurrentItem();
				if (tab > 0) {
					tab--;
					mViewPager.setCurrentItem(tab);
				} else if (tab == 0) {
					mViewPager.setCurrentItem(tab);
				}
			}
		});

		// Images right navigatin
		View rightNav = findViewById(R.id.right_nav);
		rightNav.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int tab = mViewPager.getCurrentItem();
				tab++;
				mViewPager.setCurrentItem(tab);
			}
		});
	}

//	@Override
//	public boolean onOptionsItemSelected(MenuItem item)
//	{
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//
//		//noinspection SimplifiableIfStatement
//		if (id == R.id.action_settings) {
//			return true;
//		}
//
//		return super.onOptionsItemSelected(item);
//	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment
	{
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		public PlaceholderFragment() { }

		/**
		 * Returns a new instance of this fragment for the given section
		 * number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber)
		{
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
								 Bundle savedInstanceState)
		{
			int imgId = R.drawable.ic_chevron_left_black_24dp;

			View rootView = inflater.inflate(R.layout.fragment_tutorial, container, false);

			View startBtn = rootView.findViewById(R.id.startButton);
			startBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(), MainActivity.class);

					Serializable locationList = getArguments().getSerializable(Location.INTENT_KEY);
					intent.putExtra(Location.INTENT_KEY, locationList);

					startActivity(intent);
					getActivity().finish();
				}
			});

			ImageView imageView = rootView.findViewById(R.id.location_img);

			switch (getArguments().getInt(ARG_SECTION_NUMBER))
			{
				case 1:
					startBtn.setVisibility(View.INVISIBLE);
					break;
				case 2:
					startBtn.setVisibility(View.INVISIBLE);
					imgId = R.drawable.ic_home_black_24dp;
					break;
				case 3:
					startBtn.setVisibility(View.VISIBLE);
					imgId = R.drawable.ic_chevron_right_black_24dp;
					break;
			}
			imageView.setImageResource(imgId);
//			textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
			return rootView;
		}
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter
	{

		public SectionsPagerAdapter(FragmentManager fm)
		{
			super(fm);
		}

		@Override
		public Fragment getItem(int position)
		{
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class below).
            Fragment fragment = PlaceholderFragment.newInstance(position + 1);

            Bundle bundle = new Bundle();
            bundle.putSerializable(Location.INTENT_KEY, locationList);
            fragment.setArguments(bundle);

			return fragment;
		}

		@Override
		public int getCount()
		{
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position)
		{
			switch (position) {
				case 0:
					return "SECTION 1";
				case 1:
					return "SECTION 2";
				case 2:
					return "SECTION 3";
			}
			return null;
		}
	}
}
