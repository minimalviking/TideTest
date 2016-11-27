package com.minimalviking.tidetest.home;

import android.os.Bundle;

import com.minimalviking.tidetest.data.Result;

public interface HomePresenter {
	void onSaveInstanceState(Bundle bundle);

	void onLoadInstanceState(Bundle bundle);

	void onItemSelected(Result result);
}
