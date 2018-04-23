package com.amazonlite.View;

import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import com.amazonlite.model.ItemType;
import com.amazonlite.model.Book;
import com.amazonlite.model.CD;
import com.amazonlite.model.DVD;
import com.amazonlite.model.InventoryItem;
import com.amazonlite.model.Item;
import com.amazonlite.Controller.Controller;
import com.amazonlite.model.Model;

public class View implements Observer {
	
	private Controller controller;
	private ItemType itemType;
	private InventoryItem item;
	private Model model;
	private Scanner input;
	
	public View () { }
	
	public View (Controller controller) {
		this.controller = controller;
	}
	
	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public InventoryItem getItem() {
		return item;
	}
	
	public void setInventoryItem(InventoryItem item) {
		this.item = item;
	}

	public void setItemType(int selectedMenu) {
		itemType = ItemType.values()[selectedMenu];
	}
	
	public ItemType getItemType() {
 		return itemType;
	}
	
	public void displayOnScreen(String str) {
		System.out.println(str);
	}
	
	public void displayOnScreen(ArrayList<String> objToDisplay) {
		for (String str : objToDisplay) {
			displayOnScreen(str);
		}
	}

	/**
	 * Start method. Entry point for the application.
	 * Displays a menu with all product categories
	 */
	public void start() {
		int selected = 0;
		System.out.println("Select an item type");

		displayInitialMenu();
		input = new Scanner(System.in);
		
		while (selected <= 0 || selected > ItemType.values().length) {
			System.out.print("Please select an item from the list: ");
			selected = input.nextInt();
		}
		
		// This gets the item type from the enum
		controller.setSelectedItem(Integer.valueOf(selected - 1));
		// This creates an object based on the selected enum
		controller.createNewInventoryItem(getItemType());
		// This displays the item action menu
		controller.displayActionMenu();
	}
	
	/**
	 * Item add menu. This method prompts the user for values
	 * needed to create an object
	 */
	public void displayAddMenu() {
		InventoryItem item = getItem();
		
		input = new Scanner(System.in);
		
		System.out.println("Add Title: ");
		String title = input.nextLine();
		item.setTitle(title);
		
		System.out.println("Add Author: ");
		String author = input.nextLine();
		item.setAuthor(author);
		
		System.out.println("Add Release Date (mm/dd/yyyy): ");
		String dateInput = input.nextLine();
		Date date = null;
		try {
			date = new SimpleDateFormat("MM/dd/yyyy").parse(dateInput);
			item.setReleaseDate(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		System.out.println("Add Length: ");
		double length = input.nextDouble();
		item.setLength(length);	
		
		input.nextLine();
		
		// Add an additional property depending on the type of item
		if (item instanceof CD) {
			System.out.println("Add hit single: ");
			String hitSingle = input.nextLine();
			((CD) item).setHitSingle(hitSingle);
			item.setItemType(ItemType.CD);
		} else if (item instanceof DVD) {
			System.out.println("Add bonus scenes (yes/no): ");
			String in = input.nextLine();
			boolean bonusScenes = (in.toLowerCase() == "yes" ? true : false);
			((DVD) item).setBonusScenes(bonusScenes);
			item.setItemType(ItemType.DVD);
		} else {
			System.out.println("Add publisher: ");
			String publisher = input.nextLine();
			((Book) item).setPublisher(publisher);
			item.setItemType(ItemType.BOOK);
		}

		// Add item to the properties file
		controller.addItem(item);
	}
	
	/**
	 * Method that displays the update menu
	 */
	public void displayUpdateMenu() {
		
		System.out.println("Find item to update");
		ArrayList<String> itemToUpdate = displaySearchMenu();

		input = new Scanner(System.in);
		
		System.out.printf("%s: %n", "Property to modify");
		String propertyToUpdate = input.nextLine();
		
		System.out.printf("%s: %n", "Old value to update");
		String oldValueToUpdate = input.nextLine();
		
		System.out.printf("%s: %n", "New value to update");
		String newValueToUpdate = input.nextLine();
		
		for (String str : itemToUpdate) {
			System.out.println(str);
			controller.updateItem(str, propertyToUpdate, oldValueToUpdate, newValueToUpdate);
		}
		
	}
	
	/**
	 * Method to display the inventory for specific item
	 */
	public void displayInventory() {
		controller.displayInventory(getItem());
	}
	
	/**
	 * Method to return list of object attributes
	 * @param o the object which attributes to return
	 * @return ArrayList<String> of attribute names
	 */
	private ArrayList<String> showFields(Object o) {
	   Class<?> clazz = o.getClass();
	   ArrayList<String> fields = new ArrayList<String>();
	   
	   for(Field field : clazz.getDeclaredFields()) {
		   if (!java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
			   fields.add(field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1));
		   }
	   }
	   return fields;
	}
	
	/**
	 * Method to search property files for matching entries
	 * @return ArrayList<String> of all search matches found
	 */
	public ArrayList<String> displaySearchMenu() {
		int selected = 0;
	
		// Get all attributes of the current object and the superclass
		ArrayList<String> searchMenus = new ArrayList<String>();
		searchMenus.addAll(showFields(new InventoryItem()));
		searchMenus.addAll(showFields(getItem()));
		
		input = new Scanner(System.in);
		
		while (selected <= 0 || selected > searchMenus.size()) {
			for (int i = 0; i < searchMenus.size(); i++) {
				System.out.printf("%d. Search by: %s%n", i + 1, searchMenus.get(i));
			}
			selected = input.nextInt();		
		}
		
		// Dummy advance to next line
		input.nextLine();
		
		String propertyToSearch = searchMenus.get(--selected);
		
		System.out.printf("%s: ", propertyToSearch);
		String valueToSearch = input.nextLine();

		// Search the properties file for matches and print to screen
		ArrayList<String> res = controller.searchItem(propertyToSearch, valueToSearch, getItemType());
		for (String str : res) {
			System.out.println(str);
		}
		return res;
	}
	
	/**
	 * Method that displays a menu with actions to perform on an item
	 */
	public void displayActionMenu() {
		int selected = 0;
		String[] actionMenus = {"Add", "Update", "Search", "Display"};
		input = new Scanner(System.in);
		
		while (selected <= 0 || selected > actionMenus.length) {
			for (int i = 0; i < actionMenus.length; i++) {
				System.out.printf("%d. %s%n", i + 1, actionMenus[i]);
			}
			selected = input.nextInt();
		}
		
		controller.selectActionMenu(selected);
	}
	
	/**
	 * Displays initial menu with inventory items available to select
	 * Reads the values of an Enum
	 */
	public void displayInitialMenu() {
		int counter = 1;
		for (ItemType it : ItemType.values()) {
			System.out.println(String.format("%d. %s", counter, it));
			counter++;
		}
		System.out.println();
	}

	@Override
	public void update(Observable obs, Object arg1) {
		if (obs == model) {
			System.out.println("Update called with Arguments: "+arg1);
		}
		
	}

}
