package seGroupProject;

import java.io.IOException;

import ocsf.client.AbstractClient;

public class GameClient extends AbstractClient {
	// Private data fields for storing the GUI controllers.
	private LoginControl loginControl;
	private CreateAccountControl createAccountControl;
	private WaitingControl waitingControl;
	private ViewResultsControl viewResultsControl;
	private Car car;
	private GameControl gameControl;

	// Setters for the GUI controllers.
	public void setLoginControl(LoginControl loginControl) {
		this.loginControl = loginControl;
	}
	public void setCreateAccountControl(CreateAccountControl createAccountControl) {
		this.createAccountControl = createAccountControl;
	}
	
	public void setCar (Car car) {
		this.car = car;
	}
  
	public void setWaitingControl (WaitingControl waitingControl) {
		this.waitingControl = waitingControl;
	}
	
	public void setViewResultsControl(ViewResultsControl viewResultsControl) {
		this.viewResultsControl = viewResultsControl;
	}
	
	public void setGameControl(GameControl gameControl) {
		this.gameControl = gameControl;
	}
	// Constructor for initializing the client with default settings.
	public GameClient() {
		super("localhost", 8300);
	}
  
	// Method that handles messages from the server.
	public void handleMessageFromServer(Object arg0) {
		// If we received a String, figure out what this event is.
		if (arg0 instanceof String)
		{
			// Get the text of the message.
			String message = (String)arg0;
			
			// If we successfully logged in, tell the login controller.
			if (message.equals("LoginSuccessful")) { 
				loginControl.loginSuccess();
			}
      
			// If we successfully created an account, tell the create account controller.
			else if (message.equals("CreateAccountSuccessful")) {
				createAccountControl.createAccountSuccess();
			}
			
			// If the user chooses to join a game
			else if (message.equals("WaitingSuccessful")) {
				
			}
			
			// If the user chooses to view the results of their race
			else if (message.equals("ViewResultsSuccessful")) {
				
			}
			
			else if (message.equals("GameSuccessful")) {
				
			}
			
			else if(message.equals("0")) {
		    	  RaceTrack.assignedCar = 0;
		    	  System.out.println("Allocated player 1 car");
		          RaceTrack.LoadTrack();
		      }
		      else if(message.equals("1")) {
		    	  RaceTrack.assignedCar = 1;
		    	  System.out.println("Allocated player 2 car");
		          RaceTrack.LoadTrack();
		          
		          try {
					sendToServer("start game");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		          RaceTrack.LoadTrack();
		      }
		      else if(message.equals("exit")) {
		    	
		          System.out.println("Server closing...");
		          RaceTrack.window.dispose();
		          System.exit(0); 
		      }
		}
    
		// If we received an Error, figure out where to display it.
		else if (arg0 instanceof Error) {
			// Get the Error object.
			Error error = (Error)arg0;
      
			// Display login errors using the login controller.
			if (error.getType().equals("Login")) {
				loginControl.displayError(error.getMessage());
			}
      
			// Display account creation errors using the create account controller.
			else if (error.getType().equals("CreateAccount")) {
				createAccountControl.displayError(error.getMessage());
			}
		}
	}  
}
