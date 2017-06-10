package io.jirsis.drzewo.mapper;

public abstract class AbstractMapper<S, O> {

	public O from(S source){
		return null;
	}
	
	public S to(O out){
		return null;
	}
}
