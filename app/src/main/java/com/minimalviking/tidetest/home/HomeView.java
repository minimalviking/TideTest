package com.minimalviking.tidetest.home;

import android.content.Context;

import com.minimalviking.tidetest.data.Result;

public interface HomeView {
	void onItemSelected(Result result);

	void setViewPagerPageSelected(int index);

	void showProgress(boolean show);

	Context getContext();

	void showErrorMessage(String msg);

}
