package seGroupProject;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WaitingPanel extends JPanel {
	private JLabel errorLabel;
	
	// Setter for the error text.
	public void setError(String error) {
		errorLabel.setText(error);
	}
	  	
	public WaitingPanel (WaitingControl wc) {
		JPanel labelPanel = new JPanel(new GridLayout(2, 1, 5, 5));
  		errorLabel = new JLabel("", JLabel.CENTER);
  		errorLabel.setForeground(Color.RED);
  		JLabel instructionLabel = new JLabel("Choose to start your own game or join another", JLabel.CENTER);
  		labelPanel.add(errorLabel);
  		labelPanel.add(instructionLabel);
  		
  		// Create a panel for the buttons.
    	JPanel buttonPanel = new JPanel();
    	JButton ngButton = new JButton("New Game");
    	ngButton.addActionListener(wc);
    	JButton jgButton = new JButton("Join Game");
    	jgButton.addActionListener(wc);    
    	buttonPanel.add(ngButton);
    	buttonPanel.add(jgButton);
    	
    	JPanel grid = new JPanel(new GridLayout(2, 1, 0, 10));
    	grid.add(labelPanel);
    	grid.add(buttonPanel);
    	this.add(grid);
	}
}