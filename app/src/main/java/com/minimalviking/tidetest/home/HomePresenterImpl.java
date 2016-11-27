package com.minimalviking.tidetest.home;

import android.Manifest;
import android.app.Activity;
import android.location.Location;
import android.os.Bundle;

import com.minimalviking.tidetest.App;
import com.minimalviking.tidetest.data.LatLng;
import com.minimalviking.tidetest.data.NearbySearchResponse;
import com.minimalviking.tidetest.data.Result;
import com.minimalviking.tidetest.util.DistanceCalculator;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.greenrobot.eventbus.EventBus;

import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

public class HomePresenterImpl implements HomePresenter {
	private final HomeView view;
	private boolean isShowingProgress;
	private NearbySearchResponse cachedResponse;
	public HomePresenterImpl(HomeView view) {
		this.view = view;
	}

	@Override
	public void onSaveInstanceState(Bundle bundle) {
		bundle.putBoolean("isShowingProgress", isShowingProgress);
		bundle.putSerializable("cachedResponse", cachedResponse);
	}

	@Override
	public void onLoadInstanceState(Bundle bundle) {
		if (bundle == null) {
			getDataFromServer();
		} else {
			if (bundle.getBoolean("isShowingProgress")) {
				getDataFromServer();
			} else {
				cachedResponse = (NearbySearchResponse) bundle.getSerializable("cachedResponse");
				if (cachedResponse != null) {
					EventBus.getDefault().postSticky(cachedResponse);
				} else {
					getDataFromServer();
				}
			}
		}
	}

	private void getDataFromServer() {
		showProgress(true);
		new RxPermissions((Activity) view.getContext())
			.request(Manifest.permission.ACCESS_FINE_LOCATION)
			.subscribe(new Action1<Boolean>() {
				@Override
				@SuppressWarnings({"MissingPermission"})
				public void call(Boolean permissionGranted) {
					if (!permissionGranted) {
						showProgress(false);
						view.showErrorMessage("Did not receive valid permission");
						return;
					}

					new ReactiveLocationProvider(view.getContext())
						.getLastKnownLocation()
						.switchIfEmpty(Observable.<Location>empty().doOnCompleted(new Action0() {
							@Override
							public void call() {
								showProgress(false);
								view.showErrorMessage("Could retrieve location");
							}
						}))

						.subscribe(new Subscriber<Location>() {
							@Override
							public void onCompleted() {
								showProgress(false);
							}

							@Override
							public void onError(Throwable e) {
								showProgress(false);
							}

							@Override
							public void onNext(final Location location) {
								App.getInstance()
									.provideApi()
									.getNearbyBars(new LatLng(location), 5000, "bar")
									.map(new Func1<NearbySearchResponse, NearbySearchResponse>() {
										@Override
										public NearbySearchResponse call(NearbySearchResponse response) {
											for (Result result : response.getResults()) {
												result.setDistanceToTheUser(DistanceCalculator.calculate(result.getGeometry().getLocation(), location));
											}
											return response;
										}
									})
									.subscribe(new Subscriber<NearbySearchResponse>() {
										@Override
										public void onCompleted() {
											showProgress(false);
										}

										@Override
										public void onError(Throwable e) {
											showProgress(false);
											view.showErrorMessage("Error when connecting to Google Maps API");
										}

										@Override
										public void onNext(NearbySearchResponse response) {
											cachedResponse = response;
											EventBus.getDefault().post(response);
										}
									});
							}
						});
				}
			});
	}

	private void showProgress(boolean show) {
		isShowingProgress = show;
		view.showProgress(show);

	}

	@Override
	public void onItemSelected(Result result) {
		view.setViewPagerPageSelected(1);
	}
}
