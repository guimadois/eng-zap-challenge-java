package br.com.codechallenge.zap.model;

import java.util.Collections;
import java.util.List;

import lombok.Data;

@Data
public class ItemAd {

	private Integer usableAreas;
	private String listingType;
	private String createdAt;
	private String listingStatus;
	private String id;
	private Integer parkingSpaces;
	private String updatedAt;
	private boolean owner;
	private List<String> images = Collections.emptyList();
	private Address address;
	private Integer bathrooms;
	private Integer bedrooms;
	private PricingInfos pricingInfos;
}
