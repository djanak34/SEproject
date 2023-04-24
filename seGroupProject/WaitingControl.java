package seGroupProject;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JPanel;

public class WaitingControl implements ActionListener {
	private JPanel container;
	private GameClient client;
	
  
	public WaitingControl(JPanel container, GameClient client) {
		this.container = container;
		this.client = client;
		}
	
	public void actionPerformed(ActionEvent ae) {
		String command = ae.getActionCommand();
		String c;
		if(command == "New Game") 
		{
				
			CardLayout cardLayout = (CardLayout)container.getLayout();
			cardLayout.show(container, "5");
				
			//displayLabel("Waiting for Player 2 to Join...");
			
			
		}
		else if(command == "Join Game")
		{
			c = "start game";
			CardLayout cardLayout = (CardLayout)container.getLayout();
			cardLayout.show(container, "5");
			
			try {
				client.sendToServer(c);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	public void displayLabel(String label)
	  {
	    //GamePanel gamePanel = (GamePanel)container.getComponent(3);
	    //gamePanel.setwaitingLabel(label);
	  }
}