/*
 * ReportService stores confirmed reservations and provides simple reporting.
 * It maintains booking history, supports searching by guest name, and prints summary reports.
 */
package com.bookmystay.reporting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bookmystay.model.Reservation;

public class ReportService {
	private final List<Reservation> bookingHistory;

	public ReportService() {
		this.bookingHistory = new ArrayList<>();
	}

	public void storeConfirmedReservation(Reservation reservation) {
		if (reservation == null) {
			return;
		}
		bookingHistory.add(reservation);
	}

	public void displayBookingHistory() {
		System.out.println();
		System.out.println("=== Booking History ===");

		if (bookingHistory.isEmpty()) {
			System.out.println("No confirmed reservations yet.");
			return;
		}

		System.out.println("ReservationId | GuestName | RoomType");
		for (Reservation reservation : bookingHistory) {
			System.out.println(reservation);
		}
	}

	public List<Reservation> searchBookingsByGuestName(String guestName) {
		List<Reservation> results = new ArrayList<>();
		if (guestName == null) {
			return results;
		}

		String needle = guestName.trim().toLowerCase();
		if (needle.isEmpty()) {
			return results;
		}

		for (Reservation reservation : bookingHistory) {
			String name = reservation.getGuestName();
			if (name != null && name.toLowerCase().contains(needle)) {
				results.add(reservation);
			}
		}
		return results;
	}

	public void generateSimpleBookingReport() {
		System.out.println();
		System.out.println("=== Simple Booking Report ===");
		System.out.println("Total confirmed reservations: " + bookingHistory.size());

		if (bookingHistory.isEmpty()) {
			return;
		}

		Map<String, Integer> countByRoomType = new HashMap<>();
		for (Reservation reservation : bookingHistory) {
			String roomType = reservation.getRoomType();
			if (roomType == null) {
				roomType = "Unknown";
			}
			Integer count = countByRoomType.get(roomType);
			countByRoomType.put(roomType, (count == null) ? 1 : (count + 1));
		}

		System.out.println("Bookings by room type:");
		for (Map.Entry<String, Integer> entry : countByRoomType.entrySet()) {
			System.out.println("- " + entry.getKey() + ": " + entry.getValue());
		}
	}
}
