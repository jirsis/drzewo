package io.jirsis.drzewo.mapper;

import java.util.Optional;

import org.springframework.core.convert.converter.Converter;

public abstract class CustomMapper<S, O> implements Converter<S, O>{

	public O from(S source){
		return null;
	}
	
	public Optional<O> from(Optional<S> source){
		return Optional.ofNullable(null);
	}
	
	@Override
	public O convert(S source) {
		return from(source);
	}
}
