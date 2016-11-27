package com.minimalviking.tidetest.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.minimalviking.tidetest.R;
import com.minimalviking.tidetest.data.Result;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class HomeActivity extends AppCompatActivity implements HomeView {

	private HomePagerAdapter sectionsPagerAdapter;
	private ViewPager viewPager;
	ProgressDialog progressDialog;
	private HomePresenter presenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		sectionsPagerAdapter = new HomePagerAdapter(getSupportFragmentManager());

		viewPager = (ViewPager) findViewById(R.id.container);
		viewPager.setAdapter(sectionsPagerAdapter);

		TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
		tabLayout.setupWithViewPager(viewPager);

		presenter = new HomePresenterImpl(this);
		presenter.onLoadInstanceState(savedInstanceState);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		presenter.onSaveInstanceState(outState);
		super.onSaveInstanceState(outState);
	}

	@Override
	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onItemSelected(Result result) {
		presenter.onItemSelected(result);
	}

	@Override
	public void setViewPagerPageSelected(int index) {
		viewPager.setCurrentItem(index,true);
	}

	@Override
	public void showProgress(boolean show) {
		if (show) {
			if (progressDialog == null) {
				progressDialog = ProgressDialog.show(this, null, "Please wait...");
			} else {
				progressDialog.show();
			}
		} else if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}

	@Override
	public Context getContext() {
		return this;
	}

	@Override
	public void showErrorMessage(String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(msg);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();

			}
		});
		builder.setCancelable(false);
		builder.show();
	}

	public void onStart() {
		super.onStart();
		EventBus.getDefault().register(this);
	}

	@Override
	public void onStop() {
		super.onStop();
		EventBus.getDefault().unregister(this);
	}
}
