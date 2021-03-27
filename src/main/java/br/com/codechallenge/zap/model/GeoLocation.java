package br.com.codechallenge.zap.model;

import lombok.Data;

@Data
public class GeoLocation {

	private String precision;
	private Location location;
}
