package br.com.codechallenge.zap.service;

import org.springframework.data.domain.Pageable;

import br.com.codechallenge.zap.model.ItemsWrapper;

/**
 * Interface que define serviço de filtro dos dados
 * 
 * @author guimadois
 *
 */
public interface RetrieveDataService {


	public ItemsWrapper retrieveFilteredData(Pageable pageable);

}
