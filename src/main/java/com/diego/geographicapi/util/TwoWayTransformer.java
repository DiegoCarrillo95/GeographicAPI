package com.diego.geographicapi.util;

public interface TwoWayTransformer<M,D> {
	
	D transformToDto(M source);
	
	M transformToModel(D source);

}
