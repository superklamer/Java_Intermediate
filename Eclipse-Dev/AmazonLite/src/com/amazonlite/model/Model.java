package com.amazonlite.model;

import com.amazonlite.View.View;
import com.amazonlite.interfaces.Actionable;
import com.amazonlite.interfaces.Observable;
import com.amazonlite.interfaces.Observer;
import com.amazonlite.props.InitializeProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Model implements Actionable, Observable {
	
	private ItemType itemType;
	private Item item;
	private View view;
	private ArrayList<Observer> views = new ArrayList<Observer>();
	
	public Model() { }
	
	public Model(View view) {
		this.view = view;
	}	
	
	public Model(Item item) {
		this.item = item;
		this.itemType = item.getItemType();
	}
	
	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	/**
	 * Method to create new Inventory Item object
	 * based on the selected enum
	 * @param itemType the enum object selected
	 */
	public void createNewInventoryItem(ItemType itemType) {
		if (itemType.name().equals("CD")) {
			view.setInventoryItem(new CD());
		} else if (itemType.name().equals("DVD")) {
			view.setInventoryItem(new DVD());
		} else {
			view.setInventoryItem(new Book());
		}
	}
	

	/**
	 * Method to read a Properites argument and display all 
	 * properties in the comamnd line
	 * @param prop the properties to display
	 */
	public void displayProperties(Properties prop) {
		Map<String, String> propMap = new HashMap<String, String>();
		propMap.putAll(prop.entrySet().stream()
				.collect(Collectors.toMap(e -> e.getKey().toString(), e -> e.getValue().toString())));
		
		Iterator<Entry<String, String>> propMapIterator = propMap.entrySet().iterator();
		while (propMapIterator.hasNext()) {
			Map.Entry<String, String> property = (Map.Entry<String, String>) propMapIterator.next();
			notifyObserver(view, property.getKey() + " = " + property.getValue());
		}
	}
	
	/**
	 * Helper method that reads all properties from a properties file
	 * @param fileName the name of the properties file to read
	 * @return Properties object or null if no properties are loaded or file is missing
	 */
	private Properties readProps(String fileName) {
		Properties props = new Properties();
		
		try (InputStream input = new FileInputStream(fileName)) {
			props.load(input);
			return props;
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Method that loads properties
	 * @param item InventoryItem object to load properties from file
	 * @return Properties object containing all properties from InventoryItem's properties file
	 */
	public Properties loadProperties(InventoryItem item) {
		String fileName = item.getClass().getCanonicalName().substring(item.getClass().getCanonicalName().lastIndexOf(".") + 1) + ".properties";
		
		return readProps(fileName);
	}
	
	/**
	 * Method that loads properties
	 * @param itemType ItemType Enum to load properties from file
	 * @return Properties object containing all properties from InventoryItem's properties file
	 */
	public Properties loadProperties(ItemType itemType) {
		String fileName = itemType.name() + ".properties";
		
		return readProps(fileName);
	}
	
	/**
	 * Method that saves Properties to file
	 * @param properties Properties object to save
	 * @param itemType Type of item to save properties for
	 * @return true if file is saved successfully and false if file save was unsuccessful
	 */
	private boolean saveProperties(Properties properties, ItemType itemType) {
		String fileName = itemType.name() + ".properties";
		
		try (OutputStream output = new FileOutputStream(fileName)) {
			properties.store(output, null);
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Method to add item to properties file
	 * @param item item to add
	 */
	public void addItem(InventoryItem item) {
		Properties property = new Properties();
		property = loadProperties(item);

		property.setProperty(String.valueOf(property.size() + 1), item.toString());
		saveProperties(property, item.getItemType());
	}

	/**
	 * Method to update property value
	 * @param recrdIdToMofiy Id of the property to modify
	 * @param attributeToModify Name of attribute to modify
	 * @param oldValueToUpdate old value to be modified
	 * @param newValueToUpdate new value to replace old value
	 */
	public void updateRecord(String recrdIdToMofiy, String attributeToModify ,String oldValueToUpdate, String newValueToUpdate) {
		
		ItemType itemType = ItemType.valueOf(recrdIdToMofiy.substring(recrdIdToMofiy.indexOf("Type:") + 6));
		String key = recrdIdToMofiy.substring(0, recrdIdToMofiy.indexOf(" = "));
		String newPropValue1 = recrdIdToMofiy.substring(0, recrdIdToMofiy.indexOf(attributeToModify) + attributeToModify.length() + 2);
		String newPropValue2 = recrdIdToMofiy.substring(newPropValue1.length() + oldValueToUpdate.length());
		String newPropValueFull = newPropValue1 + newValueToUpdate + newPropValue2;
		
		Properties property = new Properties();
		property = loadProperties(itemType);
		
		property.setProperty(key, newPropValueFull.substring(newPropValueFull.indexOf("Title: ")));
		saveProperties(property, itemType);
	}
	
	/**
	 * Method to find a property
	 * @param attributeToFind Name of item record to find
	 * @param valueToSearch value to search for in property
	 * @param itemType type of item to search for
	 * @return ArrayList of Strings representing every property that matches the search
	 */
	public ArrayList<String> findRecord(String attributeToFind, String valueToSearch ,ItemType itemType) {

		Properties property = new Properties();
		property = loadProperties(itemType);

		ArrayList<String> res = new ArrayList<String>();
		
		Map<String, String> propMap = new HashMap<String, String>();
		propMap.putAll(property.entrySet().stream()
				.collect(Collectors.toMap(e -> e.getKey().toString(), e -> e.getValue().toString())));
		
		Iterator<Entry<String, String>> propMapIterator = propMap.entrySet().iterator();
		while (propMapIterator.hasNext()) {
			Map.Entry<String, String> prop = (Map.Entry<String, String>) propMapIterator.next();
			String[] splittedValue = prop.getValue().split(",");
			for (String string : splittedValue) {
				if (string.toLowerCase().startsWith(attributeToFind.toLowerCase()) 
						&& string.toLowerCase().contains(valueToSearch.toLowerCase())) {
					res.add(String.format("%s = %s", prop.getKey(), prop.getValue()));
				}
			}
		}

		if (res.isEmpty()) {
			res.add(String.format("%S", "no match found"));
		}
		return res;
	}
	
	/**
	 * Method to initialize default properties if none are existing
	 */
	public void initializeDefault() {
		InitializeProperties.init();
	}
	
		
	/**
	 * Method to check if properties file exists
	 * @param itemType the item type to check for
	 * @return boolean if file exists or not
	 */
	public boolean checkInventoryFilesExist(ItemType itemType) {
		if (new File(itemType.name() + ".properties").exists()) return true;
		else return false;
	}
	
	
	public ArrayList<String> outputToView() {
		return null;
	}

	@Override
	public void addObserver(Observer o) {
		views.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		views.remove(o);
	}

	@Override
	public void notifyObserver() {
		for (Observer view : views) {
			view.update();
		}
	}
	
	@Override
	public void notifyObserver(Observer o, String message) {
		o.update(message);
	}

}
