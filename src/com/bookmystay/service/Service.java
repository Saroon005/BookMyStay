/*
 * Service represents an optional add-on that can be attached to a reservation.
 * Examples include Breakfast, Airport Pickup, and Spa.
 */
package com.bookmystay.service;

public class Service {
	private final String name;

	public Service(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}
}
