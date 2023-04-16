package seGroupProject;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class ViewResultsControl implements ActionListener
{

	// Private data fields for the container and chat client.
	  private JPanel container;
	  private GameClient client;
	  
	  // Constructor for the create account controller.
	  public ViewResultsControl(JPanel container, GameClient client)
	  {
	    this.container = container;
	    this.client = client;
	  }
	  
	  // Handle button clicks.
	  public void actionPerformed(ActionEvent ae)
	  {
	    // Get the name of the button clicked.
	    String command = ae.getActionCommand();

	    // The Logout button takes the user back to the initial panel.
	    if (command == "Logout")
	    {
	      CardLayout cardLayout = (CardLayout)container.getLayout();
	      cardLayout.show(container, "1");
	    }

	    // The Play Again button takes the user back to waiting panel.
	    else if (command == "Play Again")
	    {
	    	CardLayout cardLayout = (CardLayout)container.getLayout();
	    	cardLayout.show(container, "4");
	     
	    }
	  }
	  

}