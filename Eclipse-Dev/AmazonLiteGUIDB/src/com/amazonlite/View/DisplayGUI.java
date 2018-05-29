package com.amazonlite.View;

import java.awt.BorderLayout;
import java.sql.SQLException;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.TableRowSorter;

import com.amazonlite.model.ResultSetTableModel;

import javax.swing.table.TableModel;

public class DisplayGUI extends JPanel {
	
	private static String searchPattern;
	private static ResultSetTableModel tableModel;
	
	public void setSearchPattern(String searchPattern) {
		this.searchPattern = searchPattern;
	}
	
	private String getSearchPattern() {
		return searchPattern;
	}
	
	public void displayResults() {
		setLayout(new BorderLayout());
		// create ResultSetTableModel and display database table
		try {
			
			if (getSearchPattern().equals("") || getSearchPattern().equals(null)) {
				throw new IllegalArgumentException("No search pattern provided");
			}
			
			// create TableModel for results of query 
			tableModel = new ResultSetTableModel(getSearchPattern());
						
			// create JTable based on the tableModel
			JTable resultTable = new JTable(tableModel);
			resultTable.setAutoResizeMode( JTable.AUTO_RESIZE_ALL_COLUMNS);
			
			// place GUI components on JFrame's content pane
			add(new JScrollPane(resultTable), BorderLayout.CENTER);
				
			final TableRowSorter<TableModel> sorter = 
					new TableRowSorter<TableModel>(tableModel);
			resultTable.setRowSorter(sorter);
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
			//System.exit(1);
		} catch (IllegalArgumentException iae) {
			JOptionPane.showMessageDialog(null, iae.getMessage(), "Search Pattern Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}





























