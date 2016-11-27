package com.minimalviking.tidetest.api;

import com.minimalviking.tidetest.App;
import com.minimalviking.tidetest.R;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

public class RetrofitApi {
	public static final String BASE_URL = "https://maps.googleapis.com/";

	public static ApiInterface buildApi() {
		RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

		OkHttpClient.Builder okhttpBuilder = new OkHttpClient().newBuilder();
		okhttpBuilder.addInterceptor(new Interceptor() {
			@Override
			public Response intercept(Chain chain) throws IOException {
				Request original = chain.request();
				HttpUrl originalHttpUrl = original.url();

				HttpUrl url = originalHttpUrl.newBuilder()
						.addQueryParameter("key", App.getInstance().getString(R.string.google_maps_key))
						.build();

				Request.Builder requestBuilder = original.newBuilder()
						.url(url);

				Request request = requestBuilder.build();
				return chain.proceed(request);
			}
		});

		HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
		okhttpBuilder.addInterceptor(logging);


		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(BASE_URL)
				.client(okhttpBuilder.build())
				.addCallAdapterFactory(rxAdapter)
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		ApiInterface apiService =
				retrofit.create(ApiInterface.class);
		return apiService;
	}
}
