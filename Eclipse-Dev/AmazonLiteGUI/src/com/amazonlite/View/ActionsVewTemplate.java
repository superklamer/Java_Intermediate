package com.amazonlite.View;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;

import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public abstract class ActionsVewTemplate extends JPanel {
	
	private final JPanel mainJPanel;
	private final GroupLayout layout;
	
	private final JLabel titleLabel;
	private final JLabel authorLabel;
	private final JLabel releaseDateLabel;
	private final JLabel lengthLabel;
	private final JLabel specialFieldLabel;
	
	private final JTextField titleTextField;
	private final JTextField authorTextField;
	private final JTextField releaseDateTextField;
	private final JTextField lengthTextField;
	private final JTextField specialFieldTextField;
	
	private final String DATEFORMAT = "MM-DD-YYYY";
	
	public ActionsVewTemplate() {
			
		mainJPanel = new JPanel();
		layout = new GroupLayout(mainJPanel);
		mainJPanel.setLayout(layout);
	
		titleLabel = new JLabel("Title");
		authorLabel = new JLabel("Author");
		releaseDateLabel = new JLabel("Release Date");
		lengthLabel = new JLabel("Length");
		specialFieldLabel = new JLabel("Special Field");
		
		titleTextField = new JTextField(20);
		authorTextField = new JTextField(20);
		releaseDateTextField = new JTextField(20);
		releaseDateTextField.setForeground(Color.GRAY);
		releaseDateTextField.setText(DATEFORMAT);
		releaseDateTextField.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				if (releaseDateTextField.getText().isEmpty()) {
					releaseDateTextField.setForeground(Color.GRAY);
					releaseDateTextField.setText(DATEFORMAT);
				}
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				if (releaseDateTextField.getText().equals(DATEFORMAT)) {
					releaseDateTextField.setText("");
					releaseDateTextField.setForeground(Color.BLACK);
				}
			}
		});
		
		lengthTextField = new JTextField(20);
		specialFieldTextField = new JTextField(20);
		
		
		/*  CREATE GUI   */
		
		// Automatically add gaps between components
		layout.setAutoCreateGaps(true);
		
		// Automatically add gaps between frame edge components
		layout.setAutoCreateContainerGaps(true);
		
		// Sequential group for horizontal axis
		GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
		hGroup.addGroup(layout.createParallelGroup()
				.addComponent(titleLabel)
				.addComponent(authorLabel)
				.addComponent(releaseDateLabel)
				.addComponent(lengthLabel)
				.addComponent(specialFieldLabel));
		hGroup.addGroup(layout.createParallelGroup()
				.addComponent(titleTextField)
				.addComponent(authorTextField)
				.addComponent(releaseDateTextField)
				.addComponent(lengthTextField)
				.addComponent(specialFieldTextField));
		layout.setHorizontalGroup(hGroup);
		
		// Sequential group for the vertical axis
		GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
				.addComponent(titleLabel)
				.addComponent(titleTextField));
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
				.addComponent(authorLabel)
				.addComponent(authorTextField));
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
				.addComponent(releaseDateLabel)
				.addComponent(releaseDateTextField));
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
				.addComponent(lengthLabel)
				.addComponent(lengthTextField));
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
				.addComponent(specialFieldLabel)
				.addComponent(specialFieldTextField));
		layout.setVerticalGroup(vGroup);
		
		add(mainJPanel);
	}
	
	public void setSpecialFieldLabel (String specialFieldLabel) {
		this.specialFieldLabel.setText(specialFieldLabel);;
	}
	
	public GroupLayout getLayout() {
		return layout;
	}
	
	public JTextField getTitleTextField() {
		return titleTextField;
	}
	
	public void setTextFieldText(JTextField textField, String text) {
		textField.setText(text);
	}

	public JTextField getAuthorTextField() {
		return authorTextField;
	}
	
	public JTextField getReleaseDateTextField() {
		return releaseDateTextField;
	}

	public JTextField getLengthTextField() {
		return lengthTextField;
	}

	public JTextField getSpecialFieldTextField() {
		return specialFieldTextField;
	}
	

	private class TextFieldsActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == titleTextField) {
				
			}
		}
		
	}	
}