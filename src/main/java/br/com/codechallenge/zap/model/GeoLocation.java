package br.com.codechallenge.zap.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GeoLocation {

	private String precision;
	private Location location;
}
