package br.com.codechallenge.zap.service;

import static br.com.codechallenge.zap.util.Utils.getDeepCauseMessage;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.data.domain.Pageable;

import br.com.codechallenge.zap.config.BoundingBoxConfigs;
import br.com.codechallenge.zap.model.ItemAd;
import br.com.codechallenge.zap.model.ItemsWrapper;
import br.com.codechallenge.zap.model.Location;
import br.com.codechallenge.zap.util.PaginationUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseRetrieveDataService implements RetrieveDataService {

	@Inject
	private ItemAdService service;
	
	@Inject
	private BoundingBoxConfigs boundingBoxConfigs;
	
	public abstract boolean validateItem(ItemAd item);
	protected abstract boolean validateSalePrice(BigDecimal price, BigDecimal boundingBoxMultiplier);
	protected abstract boolean validateRentPrice(BigDecimal price, BigDecimal boundingBoxMultiplier); 
	
	@Override
	public ItemsWrapper retrieveFilteredData(Pageable pageable) {
		
		List<ItemAd> items = retrieveFilteredData();
		Integer totalCount = items.size();
		
		List<ItemAd> pageItems = PaginationUtils.getPage(items, pageable.getPageNumber(), pageable.getPageSize());
		
		return ItemsWrapper.builder()
					.pageNumber(pageable.getPageNumber())
					.pageSize(pageable.getPageSize())
					.totalCount(totalCount)					
					.listItems(pageItems)
					.totalPage(pageItems.size())
					.build();
	}

	protected List<ItemAd> retrieveFilteredData() {
	
		try {		
			List<ItemAd> allItemsAd = service.retrieveSourceData();
			return allItemsAd.stream()
							 .filter(item -> validateItem(item))
							 .collect(Collectors.toList());
		} catch (Exception e) {
			log.error(">>> Erro ao buscar anúncios: {}", getDeepCauseMessage(e));
			return Collections.emptyList();
		}			
	}
	
	protected boolean validateLocation(Location location) {
		
		return !(BigDecimal.ZERO.equals(location.getLat()) && BigDecimal.ZERO.equals(location.getLon()));
	}
	
	protected boolean verifyApplyBoudingBoxMultiplier(Location location) {
		
		return (location.getLat().compareTo(boundingBoxConfigs.getMinLat()) >= 0 && location.getLat().compareTo(boundingBoxConfigs.getMaxLat()) <= 0)
			&& (location.getLon().compareTo(boundingBoxConfigs.getMinLon()) >= 0 && location.getLon().compareTo(boundingBoxConfigs.getMaxLon()) <= 0);	
	}
	
	protected BigDecimal retrieveBoundingBoxMultiplier(Location location, BigDecimal boundingBoxMultiplier) {
		
		return verifyApplyBoudingBoxMultiplier(location) ? boundingBoxMultiplier : new BigDecimal(1);
	}
}
