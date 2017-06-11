package io.jirsis.drzewo.mapper;

import java.util.Optional;

public abstract class CustomMapper<S, O> {

	public O from(S source){
		return null;
	}
	
	public Optional<O> from(Optional<S> source){
		return Optional.ofNullable(null);
	}
}
