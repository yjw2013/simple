package com.cache.redis;

public interface Function<T, E> {
	
	public T callback(E e);
	
}
