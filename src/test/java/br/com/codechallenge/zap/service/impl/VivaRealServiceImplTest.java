package br.com.codechallenge.zap.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.codechallenge.zap.config.BoundingBoxConfigs;
import br.com.codechallenge.zap.config.VivaRealConfigs;
import br.com.codechallenge.zap.model.Address;
import br.com.codechallenge.zap.model.GeoLocation;
import br.com.codechallenge.zap.model.ItemAd;
import br.com.codechallenge.zap.model.Location;
import br.com.codechallenge.zap.model.PricingInfos;

@ExtendWith(MockitoExtension.class)
public class VivaRealServiceImplTest {

	@InjectMocks
	private VivaRealServiceImpl service;
	
	@Mock
	private VivaRealConfigs configs;
	
	@Mock
	private BoundingBoxConfigs boundingBoxConfigs;
	
	private Location location;
	
	private GeoLocation geo;
	
	private Address address;
	
	private PricingInfos price;
	
	@BeforeEach
	private void setup() {
		location = Location.builder()
							.lat(new BigDecimal("-23.502555"))
							.lon(new BigDecimal("-46.716542"))
							.build();
		
		geo = GeoLocation.builder()
						 .location(location)
						 .precision("ROOFTOP")
						 .build();
		
		address = Address.builder()
						 .geoLocation(geo)
						 .build();
		
		price = PricingInfos.builder()
							.price(BigDecimal.valueOf(405000l))
							.businessType("SALE")
							.monthlyCondoFee("2000")
							.build();
	}

	@Test
	void testValidateItem_InvalidLatLon() {
		
		location.setLat(BigDecimal.ZERO);
		location.setLon(BigDecimal.ZERO);
		
		ItemAd item = ItemAd.builder()
							.address(address)
							.build();
		
		assertFalse(service.validateItem(item));
	}
	
	@Test
	void testValidateItem_SalePriceOverMaxValue() {
				
		price.setPrice(BigDecimal.valueOf(99999999l));
		ItemAd item = ItemAd.builder()
							.address(address)
							.pricingInfos(price)
							.build();
		Mockito.when(configs.getMaxSalePrice())
			.thenReturn(BigDecimal.valueOf(700000l));
		
		assertFalse(service.validateItem(item));
	}
	
	@Test
	void testValidateItem_SalePrice() {
		ItemAd item = ItemAd.builder()
							.address(address)
							.pricingInfos(price)
							.build();
		Mockito.when(configs.getMaxSalePrice())
		.thenReturn(BigDecimal.valueOf(700000l));
		
		assertTrue(service.validateItem(item));
	}
	
	@Test
	void testValidateItem_RentalMonthlyCondoFeeInvalid() {
		price.setBusinessType("RENTAL");
		price.setMonthlyCondoFee("oiaspakspd");
		ItemAd item = ItemAd.builder()
				.address(address)
				.pricingInfos(price)				
				.build();
		
		Mockito.when(configs.getBoundingBoxMultiplier())
			.thenReturn(BigDecimal.valueOf(1.5d));

		mockBoundingBoxLat();
		
		assertFalse(service.validateItem(item));
	}
	
	@Test
	void testValidateItem_OverMaxRentalPrice() {
		price.setBusinessType("RENTAL");
		price.setMonthlyCondoFee("90");
		price.setRentalTotalPrice(BigDecimal.valueOf(900000d));
		
		ItemAd item = ItemAd.builder()
				.address(address)
				.pricingInfos(price)				
				.build();

		mockBoundingBoxLat();

		Mockito.when(configs.getMaxRentPrice())
			.thenReturn(BigDecimal.valueOf(4000l));
		
		assertFalse(service.validateItem(item));
	}

	@Test
	void testValidateItem_OverMaxMonthCondoFee() {
		price.setBusinessType("RENTAL");
		price.setMonthlyCondoFee("90000");
		price.setRentalTotalPrice(BigDecimal.valueOf(90000d));
		
		ItemAd item = ItemAd.builder()
				.address(address)
				.pricingInfos(price)				
				.build();

		mockBoundingBoxLat();

		Mockito.when(configs.getMaxRentPrice())
			.thenReturn(BigDecimal.valueOf(4000l));
		
		assertFalse(service.validateItem(item));
	}
	
	private void mockBoundingBoxLat() {
		
		Mockito.when(boundingBoxConfigs.getMinLat())
			.thenReturn(new BigDecimal("-23.568704"));
		Mockito.when(boundingBoxConfigs.getMaxLat())
			.thenReturn(new BigDecimal("-23.546686"));
	}

}
