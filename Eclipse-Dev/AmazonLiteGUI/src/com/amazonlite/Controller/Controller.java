package com.amazonlite.Controller;

import com.amazonlite.model.Item;
import com.amazonlite.model.ItemType;
import com.amazonlite.View.View;
import com.amazonlite.interfaces.Actionable;
import com.amazonlite.View.View;
import com.amazonlite.model.Model;
import com.amazonlite.props.Props;
import com.amazonlite.model.InventoryItem;

import java.util.ArrayList;
import java.util.Observer;
import java.util.Properties;

public class Controller implements Actionable  {
	
	private View view;
	private ItemType itemType;
	private Item item;
	private Model model;
	private Props props;
	
	public Controller() { }
	
	public Controller(View view) {
		this.view = view;
	}
	
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

	public void addObserver() {
		model.addObserver(view);
	}
	
	public void createNewInventoryItem(ItemType itemType) {
		model.createNewInventoryItem(itemType);
	}
	
	public void setSelectedItem(int selectedItem) {
		view.setItemType(selectedItem);
	}
	
	public void startMenu() {
		model.initializeDefaultProperties();
	}
	
//	public void displayMenu() {
//		view.displayAddMenu();
//	}
//	
//	public void displayActionMenu() {
//		view.displayActionMenu();
//	}
//	
//	public void closeApp() {
//		view.closeApp();
//	}
//
//	public void selectActionMenu(int menuIndex) {
//		switch (menuIndex) {
//		case 1:
//			view.displayAddMenu();
//			break;
//		case 2:
//			view.displayUpdateMenu();
//			break;
//		case 3:
//			view.displaySearchMenu();
//			break;
//		case 4:
//			view.displayInventory();
//			break;
//		default:
//			break;
//		}
//	}
	
	@Override
	public boolean addItem(InventoryItem item) {
		return model.addItem(item);
	}
	
	public void updateItem(String propertyToModify, String attributeToModify, String oldValueToUpdate, String newValueToUpdate) {
		model.updateRecord(propertyToModify, attributeToModify, oldValueToUpdate, newValueToUpdate);
	}
	
	public ArrayList<String> searchItem(String propertyToSearch, String valueToSearch, ItemType itemType) {
		return model.findRecord(propertyToSearch, valueToSearch, view.getItemType());
	}
	
	public void displayInventory(ItemType item) {
		model.displayRecords(model.loadRecords(item));
	}

	@Override
	public void displayRecords(Properties prop) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRecord(String propertyToModify, String attributeToModify, String oldValueToUpdate,
			String newValueToUpdate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<String> findRecord(String propertyToFind, String valueToSearch, ItemType itemType) {
		// TODO Auto-generated method stub
		return null;
	}
}
