package com.minimalviking.tidetest.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Result implements Serializable {

	@SerializedName("geometry")
	@Expose
	private Geometry geometry;
	@SerializedName("id")
	@Expose
	private String id;
	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("place_id")
	@Expose
	private String placeId;
	@SerializedName("reference")
	@Expose
	private String reference;
	@SerializedName("types")
	@Expose
	private List<String> types = new ArrayList<String>();
	@SerializedName("vicinity")
	@Expose
	private String vicinity;

	private Float distanceToTheUser;

	public void setDistanceToTheUser(Float distanceToTheUser) {
		this.distanceToTheUser = distanceToTheUser;
	}

	public String getFormattedDistanceToTheUser() {
		return new DecimalFormat("#").format(distanceToTheUser) + " m";

	}

	public Geometry getGeometry() {
		return geometry;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPlaceId() {
		return placeId;
	}

	public String getReference() {
		return reference;
	}

	public String getVicinity() {
		return vicinity;
	}

	public List<String> getTypes() {
		return types;
	}
}
