package com.ttulka.exercise;

/**
 * Persistence can be very simple, for example an in memory hash map.
 * 
 */
public interface PersistenceInterface {

	public void save(Account account);

	public Account findById(long id);

	public Account findByName(String name);

	public void delete(Account account);

}
