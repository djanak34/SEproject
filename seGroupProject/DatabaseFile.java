package seGroupProject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DatabaseFile {
	private String file = "seGroupProject/database.txt";
	private String [][] dataArr;
	
	// function to be called when username is to be checked for repeated usernames
	public boolean verifyAccount (LoginData data) {
		boolean choice = false;
		
		for (int i = 0; i < 100; i++) {
			if (data.getUsername().equals(dataArr[i][1]) && data.getPassword().equals(dataArr[i][2])) {
				choice = true;
				break;
			}
		}
		
		return choice;
	}
	
	// function to be called when user is creating a new account
	public boolean createNewAccount (CreateAccountData data) {
		boolean choice = true;
		File infile = new File(file);
		
		for (int i = 0; i < 100; i++) {
			if (data.getUsername().equals(dataArr[i][1])) {
				choice = false;
				break;
			}
		}
		User user = new User();
		String idNum = Integer.toString(user.getIdNum());
		String username = data.getUsername();
		String password = data.getPassword();
		
		
		if (choice == true) {
			try (BufferedWriter fw = new BufferedWriter(new FileWriter(infile, true))) {
				fw.newLine();
				fw.write(idNum + " ");
				fw.write(username + " ");
				fw.write(password);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return choice;
	}
	
	// constructor of the database file
	// this will import all of the values of the txt file so it can be used locally
	public DatabaseFile() throws IOException {
		File infile = new File(file);
		String [][] dataArr = new String[100][3];
		
		try (BufferedReader br = new BufferedReader(new FileReader(infile))) {
			// Places all idNums, Usernames, and passwords into a 2d array
			String line;
			int counter = 0;
			String [] tempArr = new String [3]; 
			while((line = br.readLine()) != null) {
				tempArr = line.split(" ");
				for (int i = 0; i < 3; i++) {
					dataArr [counter][i] = tempArr[i];
				}
				counter++;
			}
		}
		this.dataArr = dataArr;
	}
}
