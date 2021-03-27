package br.com.codechallenge.zap.config;

import java.math.BigDecimal;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties("bounding-box")
@Data
public class BoundingBoxConfigs {

	private BigDecimal minLon;
	private BigDecimal minLat;
	private BigDecimal maxLon;
	private BigDecimal maxLat;
}
