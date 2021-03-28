package br.com.codechallenge.zap.model;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Location {
	
	private BigDecimal lon;
	private BigDecimal lat;	
}
