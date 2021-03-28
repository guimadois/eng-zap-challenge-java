package br.com.codechallenge.zap.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {
	
	private String city;
	private String neighborhood;
	private GeoLocation geoLocation;
}
