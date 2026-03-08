/*
 * SearchService allows guests to view room availability and pricing.
 * It reads inventory data without modifying counts or prices.
 */
package com.bookmystay.search;

import com.bookmystay.inventory.InventoryService;

public class SearchService {
	private final InventoryService inventoryService;

	public SearchService(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}

	public void displayAllRooms() {
		System.out.println();
		System.out.println("=== Room Search Results ===");
		displayRoom("Single");
		displayRoom("Double");
		displayRoom("Suite");
	}

	public void displayRoom(String roomType) {
		Integer count = inventoryService.getRoomCount(roomType);
		Double price = inventoryService.getRoomPrice(roomType);

		if (count == null || price == null) {
			System.out.println("Unknown room type: " + roomType);
			return;
		}

		String availability = (count == 0) ? "Not Available" : ("Available: " + count);
		System.out.printf("%-6s | %-13s | Price/night: %.2f%n", roomType, availability, price);
	}
}
