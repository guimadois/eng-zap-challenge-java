package br.com.codechallenge.zap.service.impl;

import static org.junit.Assert.assertFalse;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.codechallenge.zap.config.BoundingBoxConfigs;
import br.com.codechallenge.zap.config.ZapConfigs;
import br.com.codechallenge.zap.model.Address;
import br.com.codechallenge.zap.model.GeoLocation;
import br.com.codechallenge.zap.model.ItemAd;
import br.com.codechallenge.zap.model.Location;
import br.com.codechallenge.zap.model.PricingInfos;

@ExtendWith(MockitoExtension.class)
public class ZapServiceImplTest {

	@InjectMocks
	private ZapServiceImpl service;
		
	@Mock
	private ZapConfigs configs;
	
	@Mock
	private BoundingBoxConfigs boundingBoxConfigs;
	
	private Location location;
	
	private GeoLocation geo;
	
	private Address address;
	
	private PricingInfos price;
	
	@BeforeEach
	private void setup() {
		location = Location.builder()
							.lat(new BigDecimal("-23.622739"))
							.lon(new BigDecimal("-46.672953"))
							.build();
		
		geo = GeoLocation.builder()
						 .location(location)
						 .precision("ROOFTOP")
						 .build();
		
		address = Address.builder()
						 .geoLocation(geo)
						 .city("São Paulo")
						 .neighborhood("Campo Belo")
						 .build();
		
		price = PricingInfos.builder()
							.price(BigDecimal.valueOf(405000l))
							.businessType("SALE")
							.price(new BigDecimal("50000"))							
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
	void testValidateItem_AboveMinRentalPrice() {
		price.setBusinessType("RENTAL");
		price.setMonthlyCondoFee("940");
		price.setRentalTotalPrice(new BigDecimal("4440"));
		price.setPrice(BigDecimal.valueOf(3000l));
		
		ItemAd item = ItemAd.builder()
				.address(address)
				.pricingInfos(price)
				.build();
		
		Mockito.when(configs.getMinRentPrice())
			.thenReturn(BigDecimal.valueOf(3500l));
		
		assertFalse(service.validateItem(item));
	}
	
	@Test
	void testValidateItem_InvalidUsableAreas() {
		
		ItemAd item = ItemAd.builder()
				.usableAreas(0)
				.address(address)
				.pricingInfos(price)
				.build();
		
		Mockito.when(configs.getBoundingBoxMultiplier())
			.thenReturn(BigDecimal.valueOf(0.9d));
		
		mockBoundingBoxLat();
		
		assertFalse(service.validateItem(item));
	}
	
	@Test
	void testValidateItem_AboveMinSalePrice() {
		
		ItemAd item = ItemAd.builder()
				.usableAreas(77)
				.address(address)
				.pricingInfos(price)
				.build();
		
		Mockito.when(configs.getBoundingBoxMultiplier())
			.thenReturn(BigDecimal.valueOf(0.9d));
		
		mockBoundingBoxLat();
		
		Mockito.when(configs.getMinSalePrice())
			.thenReturn(BigDecimal.valueOf(600000l));
		
		assertFalse(service.validateItem(item));
	}
	
	@Test
	void testValidateItem_AboceMinBuiltSquareMeterValue() {
		
		price.setPrice(BigDecimal.valueOf(600000));
		ItemAd item = ItemAd.builder()
				.usableAreas(500)
				.address(address)
				.pricingInfos(price)
				.build();
		
		Mockito.when(configs.getBoundingBoxMultiplier())
			.thenReturn(BigDecimal.valueOf(0.9d));
		
		mockBoundingBoxLat();
		
		Mockito.when(configs.getMinSalePrice())
			.thenReturn(BigDecimal.valueOf(600000l));
		Mockito.when(configs.getMinBuiltSquareMeterValue())
			.thenReturn(BigDecimal.valueOf(3500l));
		
		assertFalse(service.validateItem(item));
	}
	
	private void mockBoundingBoxLat() {
		
		Mockito.when(boundingBoxConfigs.getMinLat())
			.thenReturn(new BigDecimal("-23.568704"));		
	}
}
