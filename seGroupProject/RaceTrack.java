package seGroupProject;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RaceTrack extends JFrame 
{
    
   public static int assignedCar; 
   private static GameClient client;
   public static RaceTrack window;
   private GameControl gc;
 
    
   public static void LoadTrack() 
   {
      try 
      {
            // Set window 
            window = new RaceTrack();

            // Set window size 850 pixels by 650 pixels
            window.setSize(850, 650);
            window.setResizable(false);
            window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            window.addWindowListener(new WindowAdapter() {
               public void windowClosing(WindowEvent e) {
                  try {
                     client.sendToServer("exit"); // send exit message
                  } catch (Exception e1) {
                     e1.printStackTrace(); // catch exception 
                  } 
               }
   
           });
              
      } catch (Exception e) {
         e.printStackTrace(); // catch exception 
      }
   }

   // Constructor
   public RaceTrack() throws Exception 
	{  
       
      GamePanel panel = new GamePanel(gc);
         
      // Display panel 
      panel.setFocusable(true);
         
      // Fill JFrame with panel
		panel.setSize( WIDTH, HEIGHT );
		this.add( panel );
      setVisible(true);

	}
} 