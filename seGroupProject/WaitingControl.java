package seGroupProject;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		
		CardLayout cardLayout = (CardLayout)container.getLayout();
		cardLayout.show(container, "6");
	}
}
