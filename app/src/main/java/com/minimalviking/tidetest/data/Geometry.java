package com.minimalviking.tidetest.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Geometry {

	@SerializedName("location")
	@Expose
	private LatLng location;

	public LatLng getLocation() {
		return location;
	}

	public void setLocation(LatLng location) {
		this.location = location;
	}
}
