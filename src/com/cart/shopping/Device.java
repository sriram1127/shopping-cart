package com.cart.shopping;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Device {

	static Map<String, Double> itemtoPrice = new HashMap<String, Double>();;
	static Map<String, ItemGroup> itemGroupMap = new HashMap<String, ItemGroup>();;
	static Map<String, Integer> cart = new HashMap<String, Integer>();
	static Scanner scanner = null;
	static double total = 0;

	public static void main(String[] args) {
		System.out.println("Hi, Welcome to the Scanner Device");
		displayInfo();
		scanner = new Scanner(System.in);
		String entry = null;
		while ((entry = scanner.nextLine()) != null) {
			switch (entry.trim()) {
			case "1":
				pricing();
				break;
			case "2":
				if (itemtoPrice.size() == 0) {
					System.err.println("Please Enter the Items' Price First");
					displayInfo();
					break;
				}
				total = 0;
				cart.clear();
				cart();
				displayInfo();
				break;
			default:
				System.out.println("Please Enter valid inputs");
				displayInfo();
			}
		}
	}

	private static void displayTotal() {
		System.out.println("--------------------------------------------------");
		System.out.println("--------------------CART -------------------------");
		System.out.println("--------------------------------------------------");
		System.out.println("Item Name | count");
		int count = 0;
		for (Map.Entry<String, Integer> i : cart.entrySet()) {
			count = i.getValue();
			System.out.println(i.getKey() + " | " + count);
			ItemGroup itemGroup = itemGroupMap.get(i.getKey());
			if (itemGroup != null) {
				total += count / itemGroup.number * itemGroup.price;
				count = count % itemGroup.number;
			}
			total += count * itemtoPrice.get(i.getKey());
		}

		System.out.println("--------------------------------------------------");
		System.out.println("Total : $ " + total);
		System.out.println("--------------------------------------------------");

	}

	private static void cart() {
		System.out.println("Please Enter the Items(i.e A B A B B A)");
		String order = null;

		if ((order = scanner.nextLine()) != null) {
			for (String item : order.trim().toLowerCase().split(" ")) {
				if (!itemtoPrice.containsKey(item)) {
					System.err.println("Pricing not available for item " + item);
					return;
				}
				if (cart.containsKey(item)) {
					cart.put(item, cart.get(item) + 1);
				} else {
					cart.put(item, 1);
				}
			}
			displayTotal();
		} else {
			System.out.println("Please Enter valid inputs");
		}
	}

	private static void pricing() {
		System.out.println("Please Enter the product name: price (i.e A : 5.5, 4 A : 20, B : 6)");
		try {
			String input = validateInput(scanner.nextLine());
			String[] info = null;
			String[] groupInfo = null;
			String itemName = null;
			double itemPrice = 0;
			int groupNo = 0;
			if (input != null) {
				for (String s : input.split(",")) {
					info = s.split(":");

					itemPrice = Double.parseDouble(info[1]);
					info[0] = info[0].trim();
					if (info[0].contains(" ")) {
						groupInfo = info[0].split(" ");
						groupNo = Integer.parseInt(groupInfo[0]);
						itemName = groupInfo[1].trim().toLowerCase();
						itemGroupMap.put(itemName, new ItemGroup(groupNo, itemPrice));
					} else {
						itemName = info[0].trim().toLowerCase();
						itemtoPrice.put(itemName, Double.parseDouble(info[1]));
					}
				}
				System.out.println("Item Pricing is successfully completed");
			} else {
				System.out.println("Please Enter valid inputs");
			}
		} catch (Exception e) {
			System.out.println("Please Enter valid inputs");
		} finally {
			displayInfo();
		}
	}

	private static String validateInput(String input) {
		if (input != null && input.trim().length() > 0) {
			return input;
		}
		return null;
	}

	private static void displayInfo() {
		System.out.println("Press 1 to Enter Item Name and Price");
		System.out.println("Press 2 to Bill the items");
	}
}
