package br.com.codechallenge.zap.config;

import java.math.BigDecimal;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties("zap")
@Data
public class ZapConfigs {

	private BigDecimal minRentPrice;
	private BigDecimal minSalePrice;
	private BigDecimal minBuiltSquareMeterValue;
	private BigDecimal boundingBoxMultiplier;

}
