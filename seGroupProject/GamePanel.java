package seGroupProject;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;

public class GamePanel extends JPanel
{
	   BufferedImage track;
	
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
  
    
    
      public void setLabelone(JLabel statusLabelOne)
    {
      this.statusLabelOne = statusLabelOne;
    }
    
    public void setLabeltwo(JLabel statusLabelTwo)
    {
      this.statusLabelTwo = statusLabelTwo;
    }
    public GamePanel(GameControl gc) throws Exception
    {   
    	
        track = ImageIO.read( getClass().getResource( "track.png" ) );
        Car.RetreiveImages();
        
       
        
        
        statusLabelOne = new JLabel();
        statusLabelOne.setSize(198, 63);
        statusLabelTwo = new JLabel();
        statusLabelTwo.setSize(198, 63);
        
        add(statusLabelOne);
        add(statusLabelTwo);
                
        addKeyListener(gc); 
        
        JButton quitButton = new JButton("Quit Game");
        quitButton.setBounds(634, 26, 113, 30);
        quitButton.addActionListener(gc);
        add(quitButton);
       
       
    } 
    @Override
    public void paintComponent(Graphics g) 
    {        
        super.paintComponent( g );
        
        g.drawImage( track, 50, 100, 746, 498, Color.white, this ); 
        
        Car.p1car.paint(g, this); 
        Car.p2car.paint(g, this);  
        
        updateFeed();
    }
    public void updateFeed() {
    	
    	statusLabelOne.setText("<html><body align = 'left'>" + 
                "<b><u>Player One</b></u><br>" +
                "Laps Remaining : "+(3-Car.p1car.currLap)+"<br>"  +
                "Current Speed : "+Car.p1car.carSpeed*30+" km/h <br>"   + 
                "</body></html>");
    	statusLabelOne.setFont( new Font( "Monospaced", 1, 15 ) );
    	statusLabelOne.setLocation(85, 5);
                
    	statusLabelTwo.setText("<html><body align = 'left'>" + 
                "<b><u>Player Two</b></u><br>" + 
                "Laps Remaining : "+(3-Car.p2car.currLap)+"<br>"  +
                "Current Speed : "+Car.p2car.carSpeed*30+" km/h <br>"   +                      
                "</body></html>");             
    	statusLabelTwo.setFont( new Font( "Monospaced", 1, 15 ) );
    	statusLabelTwo.setLocation(350, 5);
    }


}