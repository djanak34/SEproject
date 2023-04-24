package seGroupProject;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RaceTrack extends JFrame
{
    
   public static int assignedCar;
   public static RaceTrack window;
   private GameControl gc;
 
    
   public static void LoadTrack(GameControl gc) 
   {
      try 
      {
            // Set window 
            window = new RaceTrack();

            // Set window size 850 pixels by 650 pixels
            window.setSize(850, 650);
            window.setResizable(false);
            window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            window.addWindowListener(gc);
              
      } catch (Exception e) {
         e.printStackTrace(); // catch exception 
      }
   }
   

   // Constructor
   public RaceTrack() throws Exception 
	{  
       
      GamePanel panel = new GamePanel(gc);
         
      panel.setFocusable(true);
         
      
		panel.setSize( WIDTH, HEIGHT );
		this.add( panel );
      setVisible(true);

	}
} 