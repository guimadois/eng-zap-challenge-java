package br.com.codechallenge.zap.model;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PricingInfos {

	private String yearlyIptu;
	private BigDecimal price;
	private String businessType;
	private String monthlyCondoFee;
	private BigDecimal rentalTotalPrice;
}
