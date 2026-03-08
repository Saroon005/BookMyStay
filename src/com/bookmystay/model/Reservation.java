/*
 * Reservation represents a guest booking request.
 * It stores the reservation id, guest name, and requested room type.
 */
package com.bookmystay.model;

public class Reservation {
	private final String reservationId;
	private final String guestName;
	private final String roomType;

	public Reservation(String reservationId, String guestName, String roomType) {
		this.reservationId = reservationId;
		this.guestName = guestName;
		this.roomType = roomType;
	}

	public String getReservationId() {
		return reservationId;
	}

	public String getGuestName() {
		return guestName;
	}

	public String getRoomType() {
		return roomType;
	}

	@Override
	public String toString() {
		return reservationId + " | " + guestName + " | " + roomType;
	}
}
