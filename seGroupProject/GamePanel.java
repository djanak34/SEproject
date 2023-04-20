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
			track = ImageIO.read(new File ("track.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			carLogo = ImageIO.read( new File ("carlogo.png"));
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

        gc.updateFeed();
        // Create a panel for the buttons.
        JPanel buttonPanel = new JPanel();
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(gc);   
        buttonPanel.add(quitButton);
    } 
    
}