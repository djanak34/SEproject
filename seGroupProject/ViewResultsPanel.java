package seGroupProject;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.time.LocalTime;

public class ViewResultsPanel extends JPanel 
{
	// Private data fields for the important GUI components.
	  private JLabel finalscore;
	  private JLabel winner;
	  
	  
	  // Setter for the error text.
	  public void setFinalScore(String fs)
	  {
	    finalscore.setText(fs);
	  }
	 
	  
	  public void setWinner(String w)
	  {
	    winner.setText(w);
	  }
	  
	  // Constructor for the create account panel.
	  public ViewResultsPanel(ViewResultsControl vrc)
	  {
	    
	    // Create a panel for the labels at the top of the GUI.
	    JPanel labelPanel = new JPanel(new GridLayout(3, 1, 5, 5));
	    JLabel gameLabel = new JLabel("Game Over", JLabel.CENTER); ;
	    labelPanel.add(gameLabel);
	    

	    // Create a panel for the account information form.
	    JPanel scorePanel = new JPanel(new GridLayout(3, 2, 5, 5));
	    finalscore = new JLabel("", JLabel.CENTER);
	    finalscore.setForeground(Color.BLUE);
	    winner = new JLabel("", JLabel.CENTER);
	    winner.setForeground(Color.GREEN);
	    scorePanel.add(finalscore);
	    scorePanel.add(winner);
	
	    
	    // Create a panel for the buttons.
	    JPanel buttonPanel = new JPanel();
	    JButton playagainButton = new JButton("Play Again");
	    playagainButton.addActionListener(vrc);
	    JButton logoutButton = new JButton("Logout");
	    logoutButton.addActionListener(vrc);    
	    buttonPanel.add(playagainButton);
	    buttonPanel.add(logoutButton);

	    // Arrange the three panels in a grid.
	    JPanel grid = new JPanel(new GridLayout(3, 1, 0, 10));
	    grid.add(labelPanel);
	    grid.add(scorePanel);
	    grid.add(buttonPanel);
	    this.add(grid);
	  }
}