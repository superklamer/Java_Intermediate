package com.amazonlite.Controller;

import com.amazonlite.model.Item;
import com.amazonlite.model.ItemType;
import com.amazonlite.View.View;
import com.amazonlite.interfaces.Actionable;
import com.amazonlite.model.Model;
import com.amazonlite.model.InventoryItem;

import java.util.ArrayList;
import java.util.Properties;

public class Controller implements Actionable  {
	
	private View view;
	private ItemType itemType;
	private Item item;
	private Model model;
	
	// Default constructor
	public Controller() { }
	
	// Constructor accepting a View object
	public Controller(View view) {
		this.view = view;
	}
	
	// Getters and Setters
	
	public View getView() {
		return view;
	}
	
	public void setView(View view2) {
		this.view = view2;
	}
	
	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}
	
	public void setSelectedItem(int selectedItem) {
		view.setItemType(selectedItem);
	}


	/**
	 * Method to create new inventory object
	 */
	public void createNewInventoryItem(ItemType itemType) {
		model.createNewInventoryItem(itemType);
	}
	

	/**
	 * Method to add item
	 * @param item item to add
	 * @return boolean if item addition is successful or not
	 */
	@Override
	public boolean addItem(InventoryItem item) {
		return model.addItem(item);
	}
	
	/**
	 * Method to read properties, passed as argument and notify observers for each K,V pair as a String
	 * @param prop the properties to iterate through
	 */
	@Override
	public ArrayList<String> displayRecords(String inventoryItem, String searchString) {
		return model.displayRecords(inventoryItem, searchString);
	}

	/**
	 * Method to update record
	 * @param recordID the id of the record to update. Represented as a String
	 * @param attributeToModify the name of the to modify. Represented as a String
	 * @param newValueToUpdate the new value to be added updated
	 * @return boolean true or false if update is successful or not
	 */
	@Override
	public boolean updateRecord(String recrodID, String attributeToModify, String newValueToUpdate) {
		return model.updateRecord(recrodID, attributeToModify, newValueToUpdate);
		
	}

	/**
	 * Method to find an item record
	 * @param propertyToFind Name of property to find
	 * @param valueToSearch value to search for in property
	 * @param itemType type of item to search for
	 * @return ArrayList<Strings> representing every property that matches the search
	 */
	@Override
	public ArrayList<String> findRecord(String propertyToFind, String valueToSearch, ItemType itemType) {
		return model.findRecord(propertyToFind, valueToSearch, view.getItemType());
	}
	
	/**
	 * Method to consturct a SQL search query with provided attributes
	 * @param propertyToFind the table to search
	 * @param valueToSearch the value to search for
	 * @param itemType the type of item to search for
	 * @return a String representation of a SQL query
	 */
	public String constructSearchPattern(String propertyToFind, String valueToSearch, ItemType itemType) {
		return model.constructSearchPattern(propertyToFind, valueToSearch, itemType);
	}
}
