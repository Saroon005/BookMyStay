/*
 * BookingQueueService collects booking requests in FIFO order.
 * It uses a Queue implemented with a LinkedList to preserve request ordering.
 */
package com.bookmystay.booking;

import java.util.LinkedList;
import java.util.Queue;

import com.bookmystay.model.Reservation;

public class BookingQueueService {
	private final Queue<Reservation> bookingQueue;
	private int nextId;

	public BookingQueueService() {
		this.bookingQueue = new LinkedList<>();
		this.nextId = 1;
	}

	public Reservation acceptBookingRequest(String guestName, String roomType) {
		String reservationId = "R" + nextId;
		nextId++;

		Reservation reservation = new Reservation(reservationId, guestName, roomType);
		bookingQueue.add(reservation);
		return reservation;
	}

	public void displayQueue() {
		System.out.println();
		System.out.println("=== Booking Request Queue (FIFO) ===");

		if (bookingQueue.isEmpty()) {
			System.out.println("Queue is empty.");
			return;
		}

		System.out.println("ReservationId | GuestName | RoomType");
		for (Reservation reservation : bookingQueue) {
			System.out.println(reservation);
		}
	}

	public Reservation processNextRequest() {
		return bookingQueue.poll();
	}

	public int getQueueSize() {
		return bookingQueue.size();
	}
}
