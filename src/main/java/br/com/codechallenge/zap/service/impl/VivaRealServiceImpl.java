package br.com.codechallenge.zap.service.impl;

import static br.com.codechallenge.zap.util.Utils.isValidNumber;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.codechallenge.zap.config.VivaRealConfigs;
import br.com.codechallenge.zap.constants.ApplicationConstans;
import br.com.codechallenge.zap.model.Address;
import br.com.codechallenge.zap.model.GeoLocation;
import br.com.codechallenge.zap.model.ItemAd;
import br.com.codechallenge.zap.model.Location;
import br.com.codechallenge.zap.model.PricingInfos;
import br.com.codechallenge.zap.service.BaseRetrieveDataService;


@Service
@Qualifier("vivaRealService")
public class VivaRealServiceImpl extends BaseRetrieveDataService {
	
	@Inject
	private VivaRealConfigs configs;
	
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
					
					result = validateSalePrice(pricingInfos.getPrice(), new BigDecimal(1));
					break;
				case ApplicationConstans.RENTAL:
					
					BigDecimal boundingBoxMultiplier = retrieveBoundingBoxMultiplier(location, configs.getBoundingBoxMultiplier()); 
					
					result = isValidNumber(pricingInfos.getMonthlyCondoFee())
					      && validateRentPrice(pricingInfos.getRentalTotalPrice(), boundingBoxMultiplier)
					      && validateMonthCondoFeeValue(pricingInfos.getMonthlyCondoFee(), pricingInfos.getRentalTotalPrice(), boundingBoxMultiplier);
					break;									
			}
		}
		
		return result;
	}
	
	private boolean validateMonthCondoFeeValue(String monthlyCondoFee, BigDecimal rentalTotalPrice, BigDecimal boundingBoxMultiplier) {
		
		BigDecimal monthlyCondo = new BigDecimal(monthlyCondoFee);
		BigDecimal maxRentTotalPrice = configs.getMaxRentPrice().multiply(boundingBoxMultiplier);
		
		return monthlyCondo.compareTo(maxRentTotalPrice) < 0;
	}

	@Override
	protected boolean validateSalePrice(BigDecimal price, BigDecimal boundingBoxMultiplier) {
		
		BigDecimal maxValue = configs.getMaxSalePrice().multiply(boundingBoxMultiplier);
		
		return price.compareTo(maxValue) < 0;
	}

	@Override
	protected boolean validateRentPrice(BigDecimal price, BigDecimal boundingBoxMultiplier) {
		
		BigDecimal maxValue = configs.getMaxRentPrice().multiply(boundingBoxMultiplier);
		
		return price.compareTo(maxValue) <= 0;
	}

}
