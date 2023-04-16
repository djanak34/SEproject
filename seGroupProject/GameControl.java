package seGroupProject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class GameControl implements ActionListener{
	
	private JPanel container;
	private GameClient client;
	
	public GameControl(JPanel container, GameClient client) {
		this.container = container;
		this.client = client;
		}
	
	public void actionPerformed(ActionEvent ae) {
		String command = ae.getActionCommand();
		
	}
}
