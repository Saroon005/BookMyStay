/*
 * ServiceManager stores services attached to reservations.
 * It maintains a Map from reservationId to a list of selected services.
 */
package com.bookmystay.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceManager {
	private final Map<String, List<Service>> reservationIdToServices;

	public ServiceManager() {
		this.reservationIdToServices = new HashMap<>();
	}

	public void addService(String reservationId, Service service) {
		if (reservationId == null || reservationId.trim().isEmpty()) {
			return;
		}
		if (service == null) {
			return;
		}

		List<Service> services = reservationIdToServices.computeIfAbsent(reservationId, key -> new ArrayList<>());
		services.add(service);
	}

	public List<Service> getServices(String reservationId) {
		List<Service> services = reservationIdToServices.get(reservationId);
		if (services == null) {
			return new ArrayList<>();
		}
		return new ArrayList<>(services);
	}

	public void displayServices(String reservationId) {
		System.out.println();
		System.out.println("=== Services for Reservation: " + reservationId + " ===");
		List<Service> services = reservationIdToServices.get(reservationId);
		if (services == null || services.isEmpty()) {
			System.out.println("No services attached.");
			return;
		}

		for (int i = 0; i < services.size(); i++) {
			System.out.println((i + 1) + ". " + services.get(i));
		}
	}
}
