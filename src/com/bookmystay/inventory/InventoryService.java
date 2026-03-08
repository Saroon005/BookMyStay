/*
 * InventoryService manages the hotel room inventory.
 * It stores room types, counts and prices using HashMaps.
 */
package com.bookmystay.inventory;

import java.util.HashMap;

public class InventoryService {
	private static final String SINGLE = "Single";
	private static final String DOUBLE = "Double";
	private static final String SUITE = "Suite";

	private final HashMap<String, Integer> roomCountsByType;
	private final HashMap<String, Double> roomPricesByType;

	public InventoryService() {
		this.roomCountsByType = new HashMap<>();
		this.roomPricesByType = new HashMap<>();
		initializeInventory(0, 0.0, 0, 0.0, 0, 0.0);
	}

	public void initializeInventory(int singleCount, double singlePrice, int doubleCount, double doublePrice, int suiteCount,
			double suitePrice) {
		roomCountsByType.put(SINGLE, singleCount);
		roomCountsByType.put(DOUBLE, doubleCount);
		roomCountsByType.put(SUITE, suiteCount);

		roomPricesByType.put(SINGLE, singlePrice);
		roomPricesByType.put(DOUBLE, doublePrice);
		roomPricesByType.put(SUITE, suitePrice);
	}

	public boolean updateRoomCount(String roomType, int newCount) {
		if (!isValidRoomType(roomType)) {
			System.out.println("Unknown room type: " + roomType);
			return false;
		}
		if (newCount < 0) {
			System.out.println("Room count cannot be negative.");
			return false;
		}

		roomCountsByType.put(roomType, newCount);
		return true;
	}

	public boolean updatePrice(String roomType, double newPrice) {
		if (!isValidRoomType(roomType)) {
			System.out.println("Unknown room type: " + roomType);
			return false;
		}
		if (newPrice < 0) {
			System.out.println("Price cannot be negative.");
			return false;
		}

		roomPricesByType.put(roomType, newPrice);
		return true;
	}

	public void displayInventory() {
		System.out.println();
		System.out.println("=== Current Inventory ===");
		printRow(SINGLE);
		printRow(DOUBLE);
		printRow(SUITE);
	}

	public Integer getRoomCount(String roomType) {
		if (!isValidRoomType(roomType)) {
			return null;
		}
		Integer count = roomCountsByType.get(roomType);
		return (count == null) ? 0 : count;
	}

	public Double getRoomPrice(String roomType) {
		if (!isValidRoomType(roomType)) {
			return null;
		}
		Double price = roomPricesByType.get(roomType);
		return (price == null) ? 0.0 : price;
	}

	private void printRow(String roomType) {
		Integer count = roomCountsByType.get(roomType);
		Double price = roomPricesByType.get(roomType);

		if (count == null) {
			count = 0;
		}
		if (price == null) {
			price = 0.0;
		}

		System.out.printf("%-6s | Available: %-4d | Price/night: %.2f%n", roomType, count, price);
	}

	private boolean isValidRoomType(String roomType) {
		return SINGLE.equals(roomType) || DOUBLE.equals(roomType) || SUITE.equals(roomType);
	}
}
