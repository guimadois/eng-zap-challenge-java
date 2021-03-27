package br.com.codechallenge.zap.service;

import java.util.List;

import br.com.codechallenge.zap.model.ItemAd;

/**
 * Interface que define o serviço de carga inicial dos dados dos anúncios em memória
 * 
 * @author guimadois
 *
 */
public interface ItemAdService {

	List<ItemAd> retrieveSourceData() throws Exception;
}
