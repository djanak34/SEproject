package seGroupProject;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import ocsf.client.AbstractClient;

public class WaitingControl implements ActionListener {
	private JPanel container;
	private GameClient client;
	private GameServer server;
  
	public WaitingControl(JPanel container, GameClient client) {
		this.container = container;
		this.client = client;
		}
	
	public void actionPerformed(ActionEvent ae) {
		// Get the name of the button clicked.
		String command = ae.getActionCommand();

		// The Cancel button takes the user back to the initial panel.
		if (command == "Start Game") {
			
		}
		else if (command == "Join Game") {
			
		}
	}
}
