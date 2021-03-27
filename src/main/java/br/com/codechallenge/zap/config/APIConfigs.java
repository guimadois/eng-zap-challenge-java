package br.com.codechallenge.zap.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties("app")
@Data
public class APIConfigs {

	private String name;
	private String description;
	private String urlSource;
}
