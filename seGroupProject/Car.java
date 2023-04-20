package seGroupProject;

import java.awt.*;
import javax.swing.*;

import java.awt.image.*;
import java.lang.String;
import java.time.LocalTime;

public class Car extends JPanel
{
    BufferedImage track; 
    protected ImageIcon[] carImages; 
    private final int TOTAL_IMAGES = 16; 

    private int car = 0;
    public int[] carLocation; 
    public int carDirection; 
    public int carSpeed; 
    private GameClient client;
    private JPanel container;
    private ViewResultsPanel vrp;
    private long startedAt;

    public static Car p1car;
    public static Car p2car;
    public static Car[] cars;

    public int currLap; 

    private boolean hasPassedcheckPoint = false; 

    private Rectangle checkPoint = new Rectangle(380, 95, 200, 107); 

    

    
    public String CarDetails()
    {
        return String.valueOf(RaceTrack.assignedCar) + " " 
        + String.valueOf(cars[RaceTrack.assignedCar].carSpeed) + " " 
        + String.valueOf(cars[RaceTrack.assignedCar].carDirection) + " " 
        + String.valueOf(cars[RaceTrack.assignedCar].carLocation[0]) + " " 
        + String.valueOf(cars[RaceTrack.assignedCar].carLocation[1]) + " "
        + String.valueOf(cars[RaceTrack.assignedCar].currLap);   
    }
    
    public Car(JPanel container, GameClient client)
    {
      this.container = container;
      this.client = client;
    }
    
    public Rectangle getCarRectangle()
    {   
        return new Rectangle( carLocation[ 0 ], carLocation[ 1 ], 35, 20 );
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
    public static void RetreiveImages() throws Exception
    {
        p1car = new Car("p1Car", 0);
        p2car = new Car("p2Car", 1);
        cars = new Car[]{p1car, p2car};
    }

    public Car( String path, int car ) throws Exception
    {
        carImages = new ImageIcon[ TOTAL_IMAGES ];

        this.car = car;
        StartPosition(); 

        
        for ( int count = 0; count < carImages.length; count++ ) 
        {            
            carImages[ count ] = new ImageIcon( getClass().getResource( path + count + ".png" ) );         
        }

       
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

    public void SendKartDetails() throws Exception
    {
        client.sendToServer(CarDetails());
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

        if(p1car.currLap == 3  && GamePanel.timer.isRunning())
        {
            GamePanel.timer.stop();                        
            
            String winMessage = "Player 1 Wins";
        
            JOptionPane.showMessageDialog( null, winMessage + " press 'Play Again' to restart", "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            hasPassedcheckPoint= false;
            
            vrp.setWinner(winMessage);
            CardLayout cardLayout = (CardLayout)container.getLayout();
  	      	cardLayout.show(container, "6");    
        }
        else if(p2car.currLap == 3  && GamePanel.timer.isRunning())
        {
        	GamePanel.timer.stop();                         
            
            String winMessage = "Player 2 Wins";
        
            JOptionPane.showMessageDialog( null, winMessage + " press 'Play Again' to restart", "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            hasPassedcheckPoint= false;
            
            vrp.setWinner(winMessage);
            CardLayout cardLayout = (CardLayout)container.getLayout();
  	      	cardLayout.show(container, "6");   
        }
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
    
    public void paint( Graphics g, GamePanel panel )
    {
        carImages[ carDirection ].paintIcon( panel, g, carLocation[0], carLocation[1]);
    }
    
}