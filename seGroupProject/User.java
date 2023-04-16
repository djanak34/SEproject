package seGroupProject;

import java.io.Serializable;
import java.util.Random;

public class User implements Serializable{

	private int idNum;
	
	// get for id number
	public int getIdNum() {
		return idNum;
	}
	
	// setter for id number
	public void setIdNum(int idNum) {
		this.idNum = idNum;
	}
	
	// constructor for User
	// this will generate the random id number
	public User() {
		Random randNum = new Random();
		int upper = 1000;
		idNum = randNum.nextInt(upper);
		setIdNum(idNum);
	}
}