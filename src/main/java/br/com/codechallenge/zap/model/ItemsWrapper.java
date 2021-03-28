package br.com.codechallenge.zap.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemsWrapper {

	private List<ItemAd> listItems;
	private Integer pageNumber;
	private Integer pageSize;
	private Integer totalCount;
	private Integer totalPage;
	
}
