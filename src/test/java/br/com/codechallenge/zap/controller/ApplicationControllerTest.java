package br.com.codechallenge.zap.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import br.com.codechallenge.zap.model.ItemsWrapper;
import br.com.codechallenge.zap.service.RetrieveDataService;

@ExtendWith(MockitoExtension.class)
public class ApplicationControllerTest {

	@InjectMocks
	private ApplicationController controller;
	
	@Mock(name = "zapService")
	private RetrieveDataService zapService;
	
	@Mock(name = "vivaRealService")	
	private RetrieveDataService vivaRealService;
	
	
	@Test
	void testRetrieveVivaRealItems() {
		Pageable pageable = Mockito.mock(Pageable.class);
		ItemsWrapper items = Mockito.mock(ItemsWrapper.class);
		
		Mockito.when(vivaRealService.retrieveFilteredData(pageable))
			.thenReturn(items);
		
		ResponseEntity<ItemsWrapper> response = controller.retrieveVivaRealItems(pageable);
		
		assertNotNull(response);
		assertTrue(response.getStatusCode().is2xxSuccessful());
	}
	
	@Test
	void testRetrieveZapItems() {
		Pageable pageable = Mockito.mock(Pageable.class);
		ItemsWrapper items = Mockito.mock(ItemsWrapper.class);
		
		Mockito.when(zapService.retrieveFilteredData(pageable))
			.thenReturn(items);
		
		ResponseEntity<ItemsWrapper> response = controller.retrieveZapItems(pageable);
		
		assertNotNull(response);
		assertTrue(response.getStatusCode().is2xxSuccessful());
	}
}
