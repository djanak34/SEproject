package seGroupProject;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;

public class GamePanel extends JPanel implements ActionListener, KeyListener
{	   
    
   
    BufferedImage track;
    private BufferedImage carLogo; 
    public static Timer timer; 
    private JLabel statusLabelOne;
    private JLabel statusLabelTwo;
   
    
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
    public GamePanel() throws Exception
    {   
        track = ImageIO.read( getClass().getResource( "track.png" ) ); 
        carLogo = ImageIO.read( getClass().getResource( "carLogo.png" ) ); 
        Car.RetreiveImages();

        statusLabelOne = new JLabel();
        add(statusLabelOne);
        statusLabelTwo = new JLabel();
        add(statusLabelTwo);
                
        addKeyListener( this ); 
        
        timer = new Timer( 100, this );
        timer.start();

        updateFeed();
    } 

    // KeyPressed method
   @Override
    public void keyPressed( KeyEvent e ) 
    {
        if( !timer.isRunning() && e.getKeyCode() != KeyEvent.VK_R ) return;     
        if(RaceTrack.assignedCar == 0 && RaceTrack.assignedCar == 1 ) return;               
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
            Car.p1car.SendKartDetails(); 
            Car.p2car.SendKartDetails(); 
        }
        catch (Exception ev)
        {
            ev.printStackTrace();  
        }	
    }   
    
    public static void Restart() 
    {
        try {

            Car.p1car.StartPosition(); 
            Car.p2car.StartPosition();  timer.start(); 
        
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
                Car.p1car.MoveCar(); 
                Car.p2car.MoveCar(); 
                
            }
            catch (Exception ev)
            {
                ev.printStackTrace();  
            }
        }      
       
        repaint();
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
        
        Car.p1car.paint(g, this); 
        Car.p2car.paint(g, this);  
        
        updateFeed();
    }

    private void updateFeed()
    {
         statusLabelOne.setText("<html><body align = 'left'>" + 
                              "<b><u>P1 Car</b></u><br>" +
                              "Laps Remaining : "+(3-Car.p1car.currLap)+"<br>"  +
                              "Current Speed : "+Car.p1car.carSpeed*30+" km/h <br>"   + 
                              "</body></html>");
         statusLabelOne.setFont( new Font( "Monospaced", 1, 15 ) );
         statusLabelOne.setLocation(121, 15);
                              
         statusLabelTwo.setText("<html><body align = 'left'>" + 
                              "<b><u>KART OPP</b></u><br>" + 
                              "Laps Remaining : "+(3-Car.p2car.currLap)+"<br>"  +
                              "Current Speed : "+Car.p2car.carSpeed*30+" km/h <br>"   +                      
                              "</body></html>");             
         statusLabelTwo.setFont( new Font( "Monospaced", 1, 15 ) );
         statusLabelTwo.setLocation(530, 15);
    }
} 