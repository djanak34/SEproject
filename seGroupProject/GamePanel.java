package seGroupProject;

import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.lang.String;
import java.time.LocalTime;

public class GamePanel extends JPanel
{
    BufferedImage track; 
    BufferedImage carLogo;
    private JLabel statusLabelOne;
    private JLabel statusLabelTwo;
    public static Timer timer;
    protected ImageIcon[] carImages; 
    public static boolean serverActive = false;
    

    // Setter for the error text.
    public void setLabelone(JLabel statusLabelOne)
    {
      this.statusLabelOne = statusLabelOne;
    }
    
    public void setLabeltwo(JLabel statusLabelTwo)
    {
      this.statusLabelTwo = statusLabelTwo;
    }
    public GamePanel(GameControl gc)
    {   
    	try {
			track = ImageIO.read(getClass().getResource("/seGroupProject/track.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			carLogo = ImageIO.read(getClass().getResource("/seGroupProject/carlogo.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			GameControl.RetreiveImages();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        statusLabelOne = new JLabel();
        add(statusLabelOne);
        statusLabelTwo = new JLabel();
        add(statusLabelTwo);
                
        addKeyListener(gc); 
        
        timer = new Timer( 100, gc );
        timer.start();

        updateFeed();
        // Create a panel for the buttons.
        JPanel buttonPanel = new JPanel();
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(gc);   
        buttonPanel.add(quitButton);
    } 
    public void updateFeed() {
    	
    	 statusLabelOne = new JLabel("", JLabel.LEFT);
         statusLabelOne.setText("Player 1 \n" + "Laps Remaining : "+(3-GameControl.p1car.currLap)+"\n"  +
                               "  Current Speed : "+GameControl.p1car.carSpeed*30+"\n");
          statusLabelOne.setFont( new Font( "Monospaced", 1, 15 ) );
          statusLabelOne.setLocation(121, 15);
          
          statusLabelTwo = new JLabel("", JLabel.LEFT);                     
          statusLabelTwo.setText("Player 2 \n" + 
                               "Laps Remaining : "+(3-GameControl.p2car.currLap)+"\n"  +
                               "  Current Speed : "+GameControl.p2car.carSpeed*30+"\n");             
          statusLabelTwo.setFont( new Font( "Monospaced", 1, 15 ) );
          statusLabelTwo.setLocation(530, 15);
    }
    
}