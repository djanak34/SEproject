package seGroupProject;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;

public class GameControl extends JPanel implements ActionListener, KeyListener, WindowListener
{	   
	
	  
	   BufferedImage track; 
	   public static Timer timer;
	   private JPanel container;
	   private GameClient client;
	   private Car c;
	   private boolean hasPassedcheckPoint = false; 
	   private Rectangle checkPoint = new Rectangle(380, 95, 200, 107); 
	  
    
    public GameControl(JPanel container, GameClient client) 
    {
 		this.container = container;
 		this.client = client;
 		
 	}
    public GameControl() 
    {
    	timer = new Timer( 100, this);
        timer.start();
        
        GamePanel gamePanel = (GamePanel)container.getComponent(4);
        gamePanel.updateFeed();
    }
    // KeyPressed method
   @Override
    public void keyPressed( KeyEvent e ) 
    {
	   GamePanel gamePanel = (GamePanel)container.getComponent(4);
	   
	    
        if( !timer.isRunning() && e.getKeyCode() != KeyEvent.VK_R ) return;     
        if(RaceTrack.assignedCar == 0 && Car.serverActive == false ) return;               
        switch ( e.getKeyCode() ) 
        {                
           
            case KeyEvent.VK_UP: Car.cars[RaceTrack.assignedCar].Accelerate(); 
               break;
            case KeyEvent.VK_DOWN: Car.cars[RaceTrack.assignedCar].Brake(); 
               break;
            case KeyEvent.VK_RIGHT: Car.cars[RaceTrack.assignedCar].turnRight(); 
               break;
            case KeyEvent.VK_LEFT: Car.cars[RaceTrack.assignedCar].turnLeft(); 
               break;         
        }	
        try
        {
            client.sendToServer(Car.p1car.CarDetails());
            client.sendToServer(Car.p2car.CarDetails()); 
        }
        catch (Exception ev)
        {
            ev.printStackTrace();  
        }	
    }   
   
    @Override
    public void actionPerformed( ActionEvent e )
    {
    	
        if( e.getSource() != timer ) return;
        for ( int i = 0; i < 2; i++ ) 
        {        
            try
            {
                Car.p1car.MoveCar(); 
                Car.p2car.MoveCar(); 
                
            }
            catch (Exception ev)
            {
                ev.printStackTrace();  
            }
        }      
       
        repaint();
        
        	String command = e.getActionCommand();

		
		if (command == "Quit Game") 
		{
			CardLayout cardLayout = (CardLayout)container.getLayout();
			cardLayout.show(container, "1");
		}
    }
    public void windowClosing(WindowEvent e) {
        try {
           client.sendToServer("exit"); // send exit message
        } catch (Exception e1) {
           e1.printStackTrace(); // catch exception 
        } 
     }
    public void CheckLap()
    {
        Rectangle[] r = GamePanel.r;
        Rectangle currentCar =  c.getCarRectangle();
       
        
        if( checkPoint.intersects( currentCar ) ) 
        {                
            hasPassedcheckPoint = true;
            return;
        }   
         
        
        if(GamePanel.finishLineRectangle.intersects(currentCar) && c.currLap < 3 && hasPassedcheckPoint == true)
        {
            c.currLap++;
            hasPassedcheckPoint = false; 
        }

        if(Car.p1car.currLap == 3  && timer.isRunning())
        {
            timer.stop();                        
            
            String winMessage = "Player 1 Wins";
            
            
            JOptionPane.showMessageDialog( null, winMessage + " press 'Play Again' to restart", "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            hasPassedcheckPoint= false;
            
            displayWinner(winMessage);
            CardLayout cardLayout = (CardLayout)container.getLayout();
  	      	cardLayout.show(container, "6");    
        }
        else if(Car.p2car.currLap == 3  && timer.isRunning())
        {
        	timer.stop();                         
            
            String winMessage = "Player 2 Wins";
        
            JOptionPane.showMessageDialog( null, winMessage + " press 'Play Again' to restart", "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            hasPassedcheckPoint= false;
            
            displayWinner(winMessage);
            
            CardLayout cardLayout = (CardLayout)container.getLayout();
  	      	cardLayout.show(container, "6");   
        }
    }
    public void Restart() 
    {
        try {

            Car.p1car.StartPosition(); 
            Car.p2car.StartPosition();  timer.start(); 
        
        } catch (Exception e) {
            e.printStackTrace(); // catch exception 
        }
    }
    public void SendCarDetails() throws Exception
    {
        client.sendToServer(c.CarDetails());
    }
    

    @Override
    public void keyReleased( KeyEvent e ) { }

    @Override
    public void keyTyped( KeyEvent e ) {}
	

    public void displayWinner(String winner) {
    
    	ViewResultsPanel viewResultsPanel = (ViewResultsPanel)container.getComponent(5);
        viewResultsPanel.setWinner(winner);
    }
    
    public void gameSuccess() {
		GamePanel gamePanel = (GamePanel)container.getComponent(4); 
		CardLayout cardLayout = (CardLayout)container.getLayout();
		cardLayout.show(container, "5");
	}
	
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
        
    }
 