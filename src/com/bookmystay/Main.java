/**
 * BookMyStay – Room Inventory Management
 * Use Case: Hotel Admin initializes and manages room inventory.
 *
 * @author developer
 * @version 1.0
 */
package com.bookmystay;

import java.util.Scanner;

import com.bookmystay.inventory.InventoryService;

public class Main {
	public static void main(String[] args) {
		InventoryService inventoryService = new InventoryService();
		Scanner scanner = new Scanner(System.in);

		boolean running = true;
		while (running) {
			printMenu();
			int choice = readInt(scanner, "Select an option: ");

			switch (choice) {
			case 1:
				handleInitializeInventory(scanner, inventoryService);
				break;
			case 2:
				handleUpdateRoomCount(scanner, inventoryService);
				break;
			case 3:
				handleUpdatePrice(scanner, inventoryService);
				break;
			case 4:
				inventoryService.displayInventory();
				break;
			case 5:
				running = false;
				System.out.println("Exiting BookMyStay. Goodbye!");
				break;
			default:
				System.out.println("Invalid option. Please try again.");
			}
		}

		scanner.close();
	}

	private static void printMenu() {
		System.out.println();
		System.out.println("=== BookMyStay - Admin Inventory Menu ===");
		System.out.println("1. Initialize inventory");
		System.out.println("2. Update room counts");
		System.out.println("3. Update prices");
		System.out.println("4. Display inventory");
		System.out.println("5. Exit");
	}

	private static void handleInitializeInventory(Scanner scanner, InventoryService inventoryService) {
		System.out.println();
		System.out.println("Initialize inventory for room types: Single, Double, Suite");

		int singleCount = readInt(scanner, "Enter available count for Single: ");
		double singlePrice = readDouble(scanner, "Enter price per night for Single: ");

		int doubleCount = readInt(scanner, "Enter available count for Double: ");
		double doublePrice = readDouble(scanner, "Enter price per night for Double: ");

		int suiteCount = readInt(scanner, "Enter available count for Suite: ");
		double suitePrice = readDouble(scanner, "Enter price per night for Suite: ");

		inventoryService.initializeInventory(singleCount, singlePrice, doubleCount, doublePrice, suiteCount, suitePrice);
		System.out.println("Inventory initialized.");
	}

	private static void handleUpdateRoomCount(Scanner scanner, InventoryService inventoryService) {
		System.out.println();
		String roomType = readRoomType(scanner);
		int newCount = readInt(scanner, "Enter new available count for " + roomType + ": ");

		boolean updated = inventoryService.updateRoomCount(roomType, newCount);
		if (updated) {
			System.out.println("Room count updated.");
		}
	}

	private static void handleUpdatePrice(Scanner scanner, InventoryService inventoryService) {
		System.out.println();
		String roomType = readRoomType(scanner);
		double newPrice = readDouble(scanner, "Enter new price per night for " + roomType + ": ");

		boolean updated = inventoryService.updatePrice(roomType, newPrice);
		if (updated) {
			System.out.println("Price updated.");
		}
	}

	private static String readRoomType(Scanner scanner) {
		while (true) {
			System.out.print("Enter room type (Single/Double/Suite): ");
			String input = scanner.nextLine().trim();

			if (input.equalsIgnoreCase("Single")) {
				return "Single";
			}
			if (input.equalsIgnoreCase("Double")) {
				return "Double";
			}
			if (input.equalsIgnoreCase("Suite")) {
				return "Suite";
			}

			System.out.println("Invalid room type. Please enter Single, Double, or Suite.");
		}
	}

	private static int readInt(Scanner scanner, String prompt) {
		while (true) {
			System.out.print(prompt);
			String line = scanner.nextLine().trim();
			try {
				return Integer.parseInt(line);
			} catch (NumberFormatException ex) {
				System.out.println("Please enter a valid integer.");
			}
		}
	}

	private static double readDouble(Scanner scanner, String prompt) {
		while (true) {
			System.out.print(prompt);
			String line = scanner.nextLine().trim();
			try {
				return Double.parseDouble(line);
			} catch (NumberFormatException ex) {
				System.out.println("Please enter a valid number.");
			}
		}
	}
}
