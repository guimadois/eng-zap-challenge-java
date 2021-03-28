package br.com.codechallenge.zap.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.codechallenge.zap.config.ZapConfigs;
import br.com.codechallenge.zap.constants.ApplicationConstans;
import br.com.codechallenge.zap.model.Address;
import br.com.codechallenge.zap.model.GeoLocation;
import br.com.codechallenge.zap.model.ItemAd;
import br.com.codechallenge.zap.model.Location;
import br.com.codechallenge.zap.model.PricingInfos;
import br.com.codechallenge.zap.service.BaseRetrieveDataService;

@Service
@Qualifier("zapService")
public class ZapServiceImpl extends BaseRetrieveDataService {

	@Inject
	private ZapConfigs configs;

	@Override
	public boolean validateItem(ItemAd item) {
		
		boolean result = false;
		Address address = item.getAddress();
		GeoLocation geo = address.getGeoLocation();
		Location location = geo.getLocation();
		
		if (super.validateLocation(location)) {
			
			PricingInfos pricingInfos = item.getPricingInfos();
			switch(pricingInfos.getBusinessType()) {
				case ApplicationConstans.SALE:
					
					BigDecimal boundingBoxMultiplier = retrieveBoundingBoxMultiplier(location, configs.getBoundingBoxMultiplier());
					
					result = validateUsableAreas(item.getUsableAreas())
					      && validateSalePrice(pricingInfos.getPrice(), boundingBoxMultiplier)
					      && validateBuiltSquareMeterValue(item.getUsableAreas(), pricingInfos.getPrice(), boundingBoxMultiplier);
					break;
				case ApplicationConstans.RENTAL:
					
					result = validateRentPrice(pricingInfos.getPrice(), new BigDecimal(1));
					break;									
			}
		}
		
		return result;
	}

	private boolean validateBuiltSquareMeterValue(Integer usableAreas, BigDecimal price, BigDecimal boundingBoxMultiplier) {
		
		return retrieveBuiltSquareMeter(usableAreas, price, boundingBoxMultiplier)
					.compareTo(configs.getMinBuiltSquareMeterValue()) == 1;
	}
	
	private BigDecimal retrieveBuiltSquareMeter(int usableAreas, BigDecimal price, BigDecimal boundingBoxMultiplier) {
		
		BigDecimal value = price.multiply(boundingBoxMultiplier);
		
		return value.divide(new BigDecimal(usableAreas), RoundingMode.HALF_UP);
	}

	private boolean validateUsableAreas(Integer usableAreas) {
		
		return usableAreas.compareTo(0) == 1;
	}

	@Override
	protected boolean validateSalePrice(BigDecimal price, BigDecimal boundingBoxMultiplier) {
		
		BigDecimal minValue = configs.getMinSalePrice().multiply(boundingBoxMultiplier);
					
		return price.compareTo(minValue) >= 0;
	}

	@Override
	protected boolean validateRentPrice(BigDecimal price, BigDecimal boundingBoxMultiplier) {
		
		BigDecimal minValue = configs.getMinRentPrice().multiply(boundingBoxMultiplier);
		
		return price.compareTo(minValue) >= 0;
	}
}
