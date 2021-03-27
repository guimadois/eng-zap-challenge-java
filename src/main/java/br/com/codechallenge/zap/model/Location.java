package br.com.codechallenge.zap.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Location {
	
	private BigDecimal lon;
	private BigDecimal lat;	
}
