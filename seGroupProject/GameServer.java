package seGroupProject;

import java.awt.*;
import javax.swing.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class GameServer extends AbstractServer
{
  // Data fields for this chat server.
  private JTextArea log;
  private JLabel status;
  private boolean running = false;
  private DatabaseFile database = new DatabaseFile();
  private int gameID;
  private ArrayList<String> list = new ArrayList<String>();

  // Constructor for initializing the server with default settings.
  public GameServer()
  {
    super(12345);
    this.setTimeout(500);
    gameID = 0;
  }

  // Getter that returns whether the server is currently running.
  public boolean isRunning()
  {
    return running;
  }
  
  // Setters for the data fields corresponding to the GUI elements.
  public void setLog(JTextArea log)
  {
    this.log = log;
  }
  
  public void setStatus(JLabel status)
  {
    this.status = status;
  }
  
  	public void setDatabase(DatabaseFile database) {
  		this.database = database;
  	}
  	
  	public int getGameID() {
  		return gameID;
  	}
  // When the server starts, update the GUI.
  public void serverStarted()
  {
    running = true;
    status.setText("Listening");
    status.setForeground(Color.GREEN);
    log.append("Server started\n");
  }
  
  // When the server stops listening, update the GUI.
   public void serverStopped()
   {
     status.setText("Stopped");
     status.setForeground(Color.RED);
     log.append("Server stopped accepting new clients - press Listen to start accepting new clients\n");
   }
 
  // When the server closes completely, update the GUI.
  public void serverClosed()
  {
    running = false;
    status.setText("Close");
    status.setForeground(Color.RED);
    log.append("Server and all current clients are closed - press Listen to restart\n");
  }

  // When a client connects or disconnects, display a message in the log.
  public void clientConnected(ConnectionToClient client)
  {
    log.append("Client " + client.getId() + " connected\n");
  }

  // When a message is received from a client, handle it.
  public void handleMessageFromClient(Object arg0, ConnectionToClient arg1)
  {
	  // If we received LoginData, verify the account information.
	  if (arg0 instanceof LoginData) {
		  // Check the username and password with the database.
		  LoginData data = (LoginData)arg0;
		  Object result;
		  list = this.database.query("select * from user");
		  if (database.verifyCreds(data.getUsername(), data.getPassword(), list)) {
			  try {
				  database.executeDML("insert into user values ('" + data.getUsername() +"', '" + data.getPassword() + "')");
			  } catch (SQLException e) {
				  // TODO Auto-generated catch block
				  e.printStackTrace();
			  }
		  result = "LoginSuccessful";
		  log.append("Client " + arg1.getId() + " successfully logged in as " + data.getUsername() + "\n");
		  }
		  else {
			  result = new Error("The username and password are incorrect.", "Login");
			  log.append("Client " + arg1.getId() + " failed to log in\n");
		  }
      
		  // Send the result to the client.
		  try
		  {
			  arg1.sendToClient(result);
		  }
		  catch (IOException e)
		  {
			  return;
		  }
	  }
    
    // If we received CreateAccountData, create a new account.
    else if (arg0 instanceof CreateAccountData)
    {
      // Try to create the account.
      CreateAccountData data = (CreateAccountData)arg0;
      Object result;
      list = this.database.query("select username from user");
      if (database.addAccount(data.getUsername(), data.getPassword(), list))
      {
        result = "CreateAccountSuccessful";
        log.append("Client " + arg1.getId() + " created a new account called " + data.getUsername() + "\n");
      }
      else
      {
        result = new Error("The username is already in use.", "CreateAccount");
        log.append("Client " + arg1.getId() + " failed to create a new account\n");
      }
      
      // Send the result to the client.
      try
      {
        arg1.sendToClient(result);
      }
      catch (IOException e)
      {
        return;
      }
    }
    else if(arg0 instanceof String) {
    	String message = (String)arg0;
    	if(message.equals("exit")) 
    	{
    		System.out.println("Game is closing ");
            try {
				arg1.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            Car.serverActive = false;
    	}
    else if(arg0 instanceof String) {
    	gameID++;
		System.out.println("Both clients have joined the game ");
        try {
			arg1.sendToClient(message);
			arg1.sendToClient("GameSuccessful");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	else
	{
        
        try {
			arg1.sendToClient(String.valueOf(arg1.getId()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
}

  // Method that handles listening exceptions by displaying exception information.
  public void listeningException(Throwable exception) 
  {
    running = false;
    status.setText("Exception occurred while listening");
    status.setForeground(Color.RED);
    log.append("Listening exception: " + exception.getMessage() + "\n");
    log.append("Press Listen to restart server\n");
  }
}