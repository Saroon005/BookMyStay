/**
 * BookMyStay – Reservation Confirmation
 * Booking requests are processed and unique room IDs are allocated.
 *
 * @author developer
 * @version 4.0
 */
package com.bookmystay;

import java.util.Scanner;

import com.bookmystay.booking.BookingQueueService;
import com.bookmystay.booking.BookingService;
import com.bookmystay.inventory.InventoryService;
import com.bookmystay.model.Reservation;
import com.bookmystay.search.SearchService;

public class Main {
	private static final String MANAGER_PASSWORD = "1234567890";

	public static void main(String[] args) {
		InventoryService inventoryService = new InventoryService();
		inventoryService.initializeInventory(5, 1000, 3, 2000, 0, 3000);
		SearchService searchService = new SearchService(inventoryService);
		BookingQueueService bookingQueueService = new BookingQueueService();
		BookingService bookingService = new BookingService();
		Scanner scanner = new Scanner(System.in);

		boolean appRunning = true;
		while (appRunning) {
			printRoleMenu();
			int roleChoice = readInt(scanner, "Select an option: ");

			switch (roleChoice) {
			case 1:
				if (authenticateManager(scanner)) {
					runManagerMenu(scanner, inventoryService);
					appRunning = false;
				}
				break;
			case 2:
				runGuestMenu(scanner, searchService, bookingQueueService, bookingService, inventoryService);
				appRunning = false;
				break;
			case 3:
				appRunning = false;
				System.out.println("Exiting BookMyStay. Goodbye!");
				break;
			default:
				System.out.println("Invalid option. Please try again.");
			}
		}

		scanner.close();
	}

	private static void printRoleMenu() {
		System.out.println();
		System.out.println("=== BookMyStay - Select Role ===");
		System.out.println("1. Manager");
		System.out.println("2. Guest");
		System.out.println("3. Exit");
	}

	private static boolean authenticateManager(Scanner scanner) {
		System.out.print("Enter manager password: ");
		String password = scanner.nextLine();
		if (!MANAGER_PASSWORD.equals(password)) {
			System.out.println("Incorrect password. Access denied.");
			return false;
		}
		return true;
	}

	private static void runGuestMenu(Scanner scanner, SearchService searchService, BookingQueueService bookingQueueService,
			BookingService bookingService, InventoryService inventoryService) {
		boolean running = true;
		while (running) {
			printGuestMenu();
			int choice = readInt(scanner, "Select an option: ");

			switch (choice) {
			case 1:
				searchService.displayAllRooms();
				break;
			case 2:
				handleSearchByRoomType(scanner, searchService);
				break;
			case 3:
				handleAddBookingRequest(scanner, bookingQueueService);
				break;
			case 4:
				bookingQueueService.displayQueue();
				break;
			case 5:
				handleCheckout(bookingQueueService, bookingService, inventoryService);
				break;
			case 6:
				running = false;
				System.out.println("Exiting BookMyStay. Goodbye!");
				break;
			default:
				System.out.println("Invalid option. Please try again.");
			}
		}
	}

	private static void printGuestMenu() {
		System.out.println();
		System.out.println("=== BookMyStay - Guest Menu ===");
		System.out.println("1. View all room types");
		System.out.println("2. Search by room type");
		System.out.println("3. Submit booking request");
		System.out.println("4. Display booking queue");
		System.out.println("5. Checkout (confirm and allocate rooms)");
		System.out.println("6. Exit");
	}

	private static void runManagerMenu(Scanner scanner, InventoryService inventoryService) {
		boolean running = true;
		while (running) {
			printManagerMenu();
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
	}

	private static void printManagerMenu() {
		System.out.println();
		System.out.println("=== BookMyStay - Manager Inventory Menu ===");
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

	private static void handleSearchByRoomType(Scanner scanner, SearchService searchService) {
		System.out.println();
		String roomType = readRoomType(scanner);
		searchService.displayRoom(roomType);
	}

	private static void handleAddBookingRequest(Scanner scanner, BookingQueueService bookingQueueService) {
		System.out.println();
		String guestName = readNonEmptyString(scanner, "Enter guest name: ");
		String roomType = readRoomType(scanner);

		Reservation reservation = bookingQueueService.acceptBookingRequest(guestName, roomType);
		System.out.println("Booking request added to queue: " + reservation.getReservationId());
		System.out.println("Current queue size: " + bookingQueueService.getQueueSize());
	}

	private static void handleCheckout(BookingQueueService bookingQueueService, BookingService bookingService,
			InventoryService inventoryService) {
		System.out.println();
		if (bookingQueueService.getQueueSize() == 0) {
			System.out.println("No requests to checkout. Queue is empty.");
			return;
		}

		System.out.println("Starting checkout... (confirming reservations in FIFO order)");
		while (bookingQueueService.getQueueSize() > 0) {
			Reservation next = bookingQueueService.peekNextRequest();
			if (next == null) {
				break;
			}

			Integer available = inventoryService.getRoomCount(next.getRoomType());
			if (available == null || available <= 0) {
				System.out.println("Cannot confirm next request. Not Available: " + next.getRoomType());
				System.out.println("Checkout stopped to preserve FIFO order.");
				break;
			}

			Reservation reservation = bookingQueueService.processNextRequest();
			String roomId = bookingService.confirmReservation(reservation, inventoryService);
			if (roomId == null) {
				System.out.println("Confirmation failed for: " + reservation);
				System.out.println("Checkout stopped.");
				break;
			}

			System.out.println("Confirmed: " + reservation.getReservationId() + " -> Room ID: " + roomId);
			System.out.println("Remaining in queue: " + bookingQueueService.getQueueSize());

			if (bookingQueueService.getQueueSize() > 0) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
					System.out.println("Checkout interrupted.");
					break;
				}
			}
		}

		System.out.println("Checkout complete.");
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

	private static String readNonEmptyString(Scanner scanner, String prompt) {
		while (true) {
			System.out.print(prompt);
			String line = scanner.nextLine().trim();
			if (!line.isEmpty()) {
				return line;
			}
			System.out.println("Value cannot be empty.");
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
