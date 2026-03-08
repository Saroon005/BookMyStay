/*
 * BookingService confirms booking requests by allocating unique room IDs.
 * It tracks allocated rooms to prevent duplicates and updates inventory counts.
 */
package com.bookmystay.booking;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.bookmystay.inventory.InventoryService;
import com.bookmystay.model.Reservation;

public class BookingService {
	private final Set<String> bookedRooms;
	private final Map<String, Set<String>> roomTypeToAssignedRooms;
	private int nextRoomSequence;

	public BookingService() {
		this.bookedRooms = new HashSet<>();
		this.roomTypeToAssignedRooms = new HashMap<>();
		this.nextRoomSequence = 1;
	}

	public String confirmReservation(Reservation reservation, InventoryService inventoryService) {
		if (reservation == null || inventoryService == null) {
			return null;
		}

		String roomType = reservation.getRoomType();
		Integer available = inventoryService.getRoomCount(roomType);
		if (available == null || available <= 0) {
			return null;
		}

		String roomId = generateUniqueRoomId(roomType);
		bookedRooms.add(roomId);
		roomTypeToAssignedRooms.computeIfAbsent(roomType, key -> new HashSet<>()).add(roomId);

		boolean updated = inventoryService.updateRoomCount(roomType, available - 1);
		if (!updated) {
			bookedRooms.remove(roomId);
			Set<String> assigned = roomTypeToAssignedRooms.get(roomType);
			if (assigned != null) {
				assigned.remove(roomId);
				if (assigned.isEmpty()) {
					roomTypeToAssignedRooms.remove(roomType);
				}
			}
			return null;
		}

		return roomId;
	}

	public Set<String> getBookedRooms() {
		return bookedRooms;
	}

	public Map<String, Set<String>> getRoomTypeToAssignedRooms() {
		return roomTypeToAssignedRooms;
	}

	private String generateUniqueRoomId(String roomType) {
		String normalizedType = normalizeRoomType(roomType);
		while (true) {
			String roomId = normalizedType + "-" + String.format("%04d", nextRoomSequence);
			nextRoomSequence++;
			if (!bookedRooms.contains(roomId)) {
				return roomId;
			}
		}
	}

	private String normalizeRoomType(String roomType) {
		if (roomType == null) {
			return "ROOM";
		}
		return roomType.trim().toUpperCase().replace(" ", "");
	}
}
