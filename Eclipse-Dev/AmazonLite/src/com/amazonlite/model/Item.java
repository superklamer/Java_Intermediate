package com.amazonlite.model;

import java.util.Date;

public final class Item {
	
	private String title;
	private String author;
	private double length;
	private Date releaseDate;
	private ItemType itemType;
	
	private Item() { }
	
	public static class Builder {
		private Item itemToBuild;
		
		Builder() {
			itemToBuild = new Item();
		}
		
		Item build() {
			Item builtItem = itemToBuild;
			itemToBuild = new Item();
			
			return builtItem;
		}
		
		public Builder setTitle(String title) {
			this.itemToBuild.title = title;
			return this;
		}
		
		public Builder setAuthor(String author) {
			this.itemToBuild.author = author;
			return this;
		}
		
		public Builder setLenght(double length) {
			this.itemToBuild.length = length;
			return this;
		}
		
		public Builder setReleaseDate(Date releaseDate) {
			this.itemToBuild.releaseDate = releaseDate;
			return this;
		}
		
		public Builder setItemType(ItemType itemType) {
			this.itemToBuild.itemType = itemType;
			return this;
		}
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public double getLength() {
		return length;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public ItemType getItemType() {
		return itemType;
	}
	
	@Override
	public String toString() {
		return String.format("Title: %s%nAuthor: %s%nLength: %2d%nRelease Date: %tD%nItem Type: %s%n", 
				title, author, length, releaseDate, itemType);
	}
}