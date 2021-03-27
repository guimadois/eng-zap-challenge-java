package br.com.codechallenge.zap.controller;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.codechallenge.zap.model.ItemsWrapper;
import br.com.codechallenge.zap.service.RetrieveDataService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/buscar")
public class ApplicationController {
	
	@Inject
	@Qualifier("zapService")
	private RetrieveDataService zapService;
	
    @Inject
	@Qualifier("vivaRealService")
	private RetrieveDataService vivaRealService;
	
	@GetMapping(value = "/viva-real")
	public ResponseEntity<ItemsWrapper> retrieveVivaRealItems(@PageableDefault(page = 1, size = 10)Pageable pageable) {
				
		ItemsWrapper dataWrapper = null;
		
		try {			
			dataWrapper = vivaRealService.retrieveFilteredData(pageable);
		} catch (Exception e) {
			log.error(">>> Falha ao recuperar informações");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(dataWrapper);
	}
	
	@GetMapping(value = "/zap")
	public ResponseEntity<ItemsWrapper> retrieveZapItems(@PageableDefault(page = 1, size = 10)Pageable pageable) {
		
		ItemsWrapper dataWrapper = null;
		try {
			dataWrapper = zapService.retrieveFilteredData(pageable);
		} catch (Exception e) {
			log.error(">>> Falha ao recuperar informações");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(dataWrapper);
	}
}
