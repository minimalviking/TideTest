package com.minimalviking.tidetest.api;

import com.minimalviking.tidetest.data.LatLng;
import com.minimalviking.tidetest.data.NearbySearchResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiInterface {

	@GET("/maps/api/place/nearbysearch/json")
	Observable<NearbySearchResponse> getNearbyBars(@Query("location") LatLng location, @Query("radius") int radius, @Query("type") String type);

}