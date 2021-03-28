package br.com.codechallenge.zap.listener;

import static br.com.codechallenge.zap.util.Utils.getDeepCauseMessage;

import javax.inject.Inject;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.com.codechallenge.zap.service.ItemAdService;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class ServiceInitializedEventListener {

	@Inject
	private ItemAdService service; 
	
	@EventListener(ApplicationReadyEvent.class)
	private void afterInicializacao() {
		log.info(">>> Iniciar carga de dados em memória...");
		try {
			service.retrieveSourceData();
		} catch (Exception e) {
			log.error(">>> Erro ao buscar dados iniciais: {}", getDeepCauseMessage(e));
		}
		log.info(">>> Carga de dados finalizada com sucesso!");
	}
	
	
}
