package com.amazonlite.model;

public class DVD extends InventoryItem {
	
	private boolean bonusScenes;
	private static int instance;

	public DVD(boolean bonusScenes) {
		this.bonusScenes = bonusScenes;
		instance++;
	}
	
	public DVD() {
		instance++;
	}
	
	public static int getInstance() {
		return instance;
	}

	public Boolean getBonusScenes() {
		return bonusScenes;
	}

	public void setBonusScenes(Boolean bonusScenes) {
		this.bonusScenes = bonusScenes;
	}
	
	@Override
	public String toString() {
		return String.format("Title: %s, Author: %s, Length: %1f, Bonus Scenes: %s ,Release Date: %tD", 
				getTitle(),
				getAuthor(),
				getLength(),
				getBonusScenes() == true ? "Yes" : "No",
				getReleaseDate());
	}
}
