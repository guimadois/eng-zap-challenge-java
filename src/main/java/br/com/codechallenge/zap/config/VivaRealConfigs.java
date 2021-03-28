package br.com.codechallenge.zap.config;

import java.math.BigDecimal;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties("viva-real")
@Data
public class VivaRealConfigs {

	private BigDecimal maxRentPrice;
	private BigDecimal maxSalePrice;
	private BigDecimal boundingBoxMultiplier;
	private BigDecimal percentTotalPriceMonthlyCondo;

}
