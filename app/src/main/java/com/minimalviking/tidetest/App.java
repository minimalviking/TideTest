package com.minimalviking.tidetest;

import android.app.Application;

import com.minimalviking.tidetest.api.ApiInterface;
import com.minimalviking.tidetest.api.RetrofitApi;

public class App extends Application {
	private ApiInterface api;
	private static App instance;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		this.api = RetrofitApi.buildApi();

	}

	public ApiInterface provideApi() {
		return api;
	}

	public static App getInstance() {
		return instance;
	}
}
