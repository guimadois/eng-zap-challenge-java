package br.com.codechallenge.zap.constants;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.com.codechallenge.zap.model.ItemAd;

@Component
public class RestTemplateComponent {

	public ResponseEntity<ItemAd[]> retrieveData(String url) {
		 return new RestTemplate()
				 	.getForEntity(url, ItemAd[].class);
	}
}
