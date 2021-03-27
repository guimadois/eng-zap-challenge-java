package br.com.codechallenge.zap.model;

import lombok.Data;

@Data
public class Address {
	
	private String city;
	private String neighborhood;
	private GeoLocation geoLocation;
}
