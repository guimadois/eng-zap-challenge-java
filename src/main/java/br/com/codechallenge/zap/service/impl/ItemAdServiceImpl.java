package br.com.codechallenge.zap.service.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.codechallenge.zap.config.APIConfigs;
import br.com.codechallenge.zap.model.ItemAd;
import br.com.codechallenge.zap.service.ItemAdService;
import br.com.codechallenge.zap.util.Utils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ItemAdServiceImpl implements ItemAdService {

	@Inject
	private APIConfigs config;
	
	private List<ItemAd> listItemsAd = Collections.emptyList();

	@Override
	@Cacheable("dataInitializer")
	public List<ItemAd> retrieveSourceData() throws Exception {
		
		if (Utils.isEmpty(listItemsAd)) {
			log.info(">>> Requisitando carga inicial dos dados...");
			
			ResponseEntity<ItemAd[]> response = new RestTemplate()
													.getForEntity(config.getUrlSource(), ItemAd[].class);
			
			if (!response.getStatusCode().is2xxSuccessful()) {
				String errMsg = "Erro " + response.getStatusCodeValue() + " ao buscar dados iniciais!";
				log.error(errMsg);
				throw new Exception(errMsg);
			}
			
			listItemsAd = Arrays.asList(response.getBody());
		} else
			log.info(">>> Dados requisitados em cache!");
		
		return listItemsAd;
	}
}
