package seGroupProject;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;

import javax.swing.*;
import javax.imageio.*;

public class GameControl extends JPanel implements ActionListener, KeyListener
{	   
    
	private JPanel container;
	private GameClient client;
    BufferedImage track;
    private BufferedImage carLogo; 
    public static Timer timer; 
    public static GameControl[] cars;
    public static GameControl p1car;
    public static GameControl p2car;
    protected ImageIcon[] carImages; 
    private final int TOTAL_IMAGES = 16; 
    private JLabel statusLabelOne;
    private JLabel statusLabelTwo;
    private GamePanel gp;

    private int car = 0;
    public int[] carLocation; 
    public int carDirection; 
    public int carSpeed; 
    private ViewResultsPanel vrp;
  //  private long startedAt;

    public int currLap; 

    private boolean hasPassedcheckPoint = false; 
    private Rectangle checkPoint = new Rectangle(380, 95, 200, 107);
   
    
    static Rectangle[] r = new Rectangle[]
    {        
        new Rectangle( 45, 482, 755, 102 ),       new Rectangle( 30, 344, 135, 238 ), 
        new Rectangle( 45, 344, 338, 101 ),      new Rectangle( 232, 232, 151, 213 ),    
        new Rectangle( 45, 225, 338, 106 ),       new Rectangle( 30, 90, 130, 241 ), 
        new Rectangle( 45, 90, 755, 97 ),        new Rectangle( 675, 90, 125, 208 ),
        new Rectangle( 383, 193, 417, 105 ),     new Rectangle( 375, 193, 166, 287 ),    
        new Rectangle( 383, 375, 417, 105),      new Rectangle( 680, 375, 120, 207) 
    };    
    
    public static Rectangle finishLineRectangle = new Rectangle( 580, 500, 28, 140);
    
    // Constructor
    public GameControl(JPanel container, GameClient client) 
    {
		this.container = container;
		this.client = client;
	}
    
    public GameControl( String path, int car ) throws Exception
    {
        carImages = new ImageIcon[ TOTAL_IMAGES ];

        this.car = car;
        StartPosition(); 

        
        for ( int count = 0; count < carImages.length; count++ ) 
        {            
            carImages[ count ] = new ImageIcon( getClass().getResource( path + count + ".png" ) );         
        }

       
    }
    public void UpdateCarDetails(String[] changes)
    {
        int index = Integer.parseInt(changes[0]);
        cars[index].carSpeed = Integer.parseInt(changes[1]);
        cars[index].carDirection = Integer.parseInt(changes[2]);
        cars[index].carLocation[0] = Integer.parseInt(changes[3]);
        cars[index].carLocation[1] = Integer.parseInt(changes[4]);
        cars[index].currLap = Integer.parseInt(changes[5]);
    }
 // Retrieve images
    public Rectangle getCarRectangle()
    {   
        return new Rectangle( carLocation[ 0 ], carLocation[ 1 ], 35, 20 );
    }
    public static void RetreiveImages() throws Exception
    {
        p1car = new GameControl("p1Car", 0);
        p2car = new GameControl("p2Car", 1);
        cars = new GameControl[]{p1car, p2car};
    }
    void Accelerate()
    {
        if (carSpeed < 5 ) 
        {
            carSpeed += 1;
        } 
    }

    
    void Brake()
    {
        if ( carSpeed > -5 ) 
        {
            carSpeed -= 1;    
        }            
    }
    
    void turnLeft()
    {
        
        carDirection--;
        if( carDirection == -1) carDirection = 15;
    }

    void turnRight()
    {
        
        carDirection++;
        if( carDirection == 16 ) carDirection = 0;
    }

   
    void MoveCar() throws Exception
    {        
        int deltaX = 0, deltaY = 0; 

        switch( carDirection )
        {          
            case 0: case 8:
                deltaX = carSpeed;             
                if( carDirection == 0 )
                    deltaX *= 2; 
                if( carDirection == 8 )
                    deltaX *= -2;               
                break;
                  
            case 4: case 12:
               deltaY = carSpeed;                
               if( carDirection == 4 ) 
               deltaY *= 2; 
               if( carDirection == 12 )
               deltaY *= -2; 
               break;  
            
            
            case 1: case 2: case 3: case 5: case 6: case 7:
            case 9: case 10: case 11: case 13: case 14: case 15:
                deltaX = carSpeed / 2; 
                deltaY = carSpeed / 2; 

                if ( carDirection == 1 || carDirection == 2 || carDirection == 14 || carDirection == 15  ) 
                {
                    deltaX *= 2;
                }
                if ( carDirection == 3 || carDirection == 13 ) 
                {
                    deltaX *= 1;
                }
                if ( carDirection == 5 || carDirection == 11 ) 
                {
                    deltaX *= -1;
                }
                if ( carDirection == 6 || carDirection == 7 || carDirection == 9 || carDirection == 10  ) 
                {
                    deltaX *= -2;
                }

                if ( carDirection == 2 || carDirection == 3 || carDirection == 5 || carDirection == 6  ) 
                {
                    deltaY *= 2;
                }
                if ( carDirection == 1 || carDirection == 7 ) 
                {
                    deltaY *= 1;
                }
                if ( carDirection == 9 || carDirection == 15 ) 
                {
                    deltaY *= -1;
                }
                if ( carDirection == 10 || carDirection == 11 || carDirection == 13 || carDirection == 14  ) 
                {
                    deltaY *= -2;
                }
                break;                     
        }
       
        handleCarCollision( deltaX, deltaY );     
        CheckLap();  
    }
    void handleCarCollision( int deltaX, int deltaY ) throws Exception
    {                
        int xValueCar = carLocation[ 0 ] + deltaX; 
        int yValueCar = carLocation[ 1 ] + deltaY; 

        boolean road = false;
        Rectangle[] r = GameControl.r;

   
        Rectangle currentCar =  new Rectangle( xValueCar, yValueCar, 35, 20 );
        for( int i = 0; i < r.length; i++ )
        {     
            if( r[ i ].contains( currentCar ) ) 
            {                
                road = true;
                break;
            } 
        }      
      
   
        if( road == false )
        {
            carSpeed = 0; 
        }
        else
        {
            Rectangle p1carRectangle = p1car.getCarRectangle();
            Rectangle p2carRectangle = p2car.getCarRectangle();

            
            if( p1carRectangle.intersects( p2carRectangle ))
            {  

                p1car.StartPosition();
                p2car.StartPosition();

               
                JOptionPane.showMessageDialog( null, "The cars crashed. The game will restart automatically", "CRASH", JOptionPane.WARNING_MESSAGE );
            }
            else
            {   
               
                carLocation[ 0 ] = xValueCar;
                carLocation[ 1 ] = yValueCar;
            }        
        }
    }
    public void CheckLap()
    {
        Rectangle[] r = GameControl.r;
        Rectangle currentCar =  getCarRectangle();
        
        
        if( checkPoint.intersects( currentCar ) ) 
        {                
            hasPassedcheckPoint = true;
            return;
        }   
         
        
        if(GameControl.finishLineRectangle.intersects(currentCar) && currLap < 3 && hasPassedcheckPoint == true)
        {
            currLap++;
            hasPassedcheckPoint = false; 
        }

        if(p1car.currLap == 3  && GameControl.timer.isRunning())
        {
            GameControl.timer.stop();                        
            
            String winMessage = "Player 1 Wins";
        
            JOptionPane.showMessageDialog( null, winMessage + " press 'Play Again' to restart", "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            hasPassedcheckPoint= false;
            
            vrp.setWinner(winMessage);
            CardLayout cardLayout = (CardLayout)container.getLayout();
  	      	cardLayout.show(container, "6");    
        }
        else if(p2car.currLap == 3  && GameControl.timer.isRunning())
        {
        	GameControl.timer.stop();                         
            
            String winMessage = "Player 2 Wins";
        
            JOptionPane.showMessageDialog( null, winMessage + " press 'Play Again' to restart", "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            hasPassedcheckPoint= false;
            
            vrp.setWinner(winMessage);
            CardLayout cardLayout = (CardLayout)container.getLayout();
  	      	cardLayout.show(container, "6");   
        }
    }
 

    // KeyPressed method
   @Override
    public void keyPressed( KeyEvent e ) 
    {
        if( !timer.isRunning() && e.getKeyCode() != KeyEvent.VK_R ) return;     
        if(RaceTrack.assignedCar == 0 && RaceTrack.assignedCar == 1 ) return;               
        switch ( e.getKeyCode() ) 
        {                
           
            case KeyEvent.VK_UP: cars[RaceTrack.assignedCar].Accelerate(); 
               break;
            case KeyEvent.VK_DOWN: cars[RaceTrack.assignedCar].Brake(); 
               break;
            case KeyEvent.VK_RIGHT: cars[RaceTrack.assignedCar].turnRight(); 
               break;
            case KeyEvent.VK_LEFT:cars[RaceTrack.assignedCar].turnLeft(); 
               break;         
        }	
        try
        {
            p1car.SendKartDetails(); 
            p2car.SendKartDetails(); 
        }
        catch (Exception ev)
        {
            ev.printStackTrace();  
        }	
    }   
    
    public static void Restart() 
    {
        try {

            p1car.StartPosition(); 
            p2car.StartPosition();  timer.start(); 
        
        } catch (Exception e) {
            e.printStackTrace(); // catch exception 
        }
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        // Event handler for timer tick
    	
        if( e.getSource() != timer ) return;
        for ( int i = 0; i < 2; i++ ) 
        {        
            try
            {
                p1car.MoveCar(); 
                p2car.MoveCar(); 
                
            }
            catch (Exception ev)
            {
                ev.printStackTrace();  
            }
        }      
       
        repaint();
        
        
    }
    public void buttonclick(ActionEvent ae)
    {
    	String command = ae.getActionCommand();

		// The Cancel button takes the user back to the initial panel.
		if (command == "Quit") {
			CardLayout cardLayout = (CardLayout)container.getLayout();
			cardLayout.show(container, "1");
		}

    }
    
    public String CarDetails()
    {
        return String.valueOf(RaceTrack.assignedCar) + " " 
        + String.valueOf(cars[RaceTrack.assignedCar].carSpeed) + " " 
        + String.valueOf(cars[RaceTrack.assignedCar].carDirection) + " " 
        + String.valueOf(cars[RaceTrack.assignedCar].carLocation[0]) + " " 
        + String.valueOf(cars[RaceTrack.assignedCar].carLocation[1]) + " "
        + String.valueOf(cars[RaceTrack.assignedCar].currLap);   
    }
    public void SendKartDetails() throws Exception
    {
        client.sendToServer(CarDetails());
    }

    @Override
    public void keyReleased( KeyEvent e ) { }

    @Override
    public void keyTyped( KeyEvent e ) {}
	
    @Override
    public void paintComponent(Graphics g) 
    {        
        super.paintComponent( g );
        
        g.drawImage( track, 50, 100, 746, 498, Color.white, this ); 
        g.drawImage( carLogo, 366, 23, this );
        
        p1car.paint(g, this); 
        p2car.paint(g, this);  
        
        gp.updateFeed();
    }
    public void StartPosition() throws Exception
    {
        if (car == 0)
        {
            carLocation = new int[] {645, 500};
        }
        else
        {
            carLocation = new int[] {645, 550};
        }
        hasPassedcheckPoint = false; 
        carDirection = 8; 
        carSpeed = 0; 
        currLap = 0;  
        
    }
    public void paint( Graphics g, GameControl panel )
    {
        carImages[ carDirection ].paintIcon( panel, g, carLocation[0], carLocation[1]);
    }
} 