package seGroupProject;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.*;

public class LoadingPanel extends JPanel{
	// Constructor for the login panel.
	  	public LoadingPanel(LoginControl lc) { 
	        
	  		// Create a panel for the labels at the top of the GUI.
	  		JPanel labelPanel = new JPanel(new GridLayout(2, 1, 5, 5));
	  		JLabel instructionLabel = new JLabel("Waiting for second palyer to join.", JLabel.CENTER);
	  		labelPanel.add(instructionLabel);

	    	JPanel grid = new JPanel(new GridLayout(2, 1, 0, 10));
	    	grid.add(labelPanel);
	    	this.add(grid);
	  	}
}
