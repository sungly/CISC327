public class Agent extends Atm{ //subclass of the Atm class.
	public Agent(){ //constructor class of an agent.
		super(99999999); // Maximum amount that an account may withdraw/transfer/deposit from an account.
	}//end constructor

	public String create(String[] args){ //Public method to create a new account.
		if(args[1].length() >= 3 && args[1].length()<=30){ //if statement to check if the account name is within 3 and 30 alphanumeric characters.
			System.out.println("Creating account " + args[0]); //Print out saying creating account + the account number.
			return "CR " + args[0] + " 00000000 000 " +args[1]; //Returns a transaction message to put into the Transaction Summary File.
		}
		System.out.println("Error: account name must be between 3 and 30 alphanumberic characters"); //Print out saying why the account was not created.
		return null; //If account number is not valid,  then account is not created.  Returns null.

	}//end end create

	public String delete(String[] args){ //Public method to delete an account.
		if(args[1].length() >= 3 && args[1].length()<=30){ //if statement to check if the account name is within 3 and 30 alphanumeric characters.
			System.out.println("Deleting account " + args[0]); //Print out saying Deleting account + The account number.
			return "DL " + args[0] + " 00000000 000 " +args[1]; //Returns a transaction message to put into the Transaction Summary File.
		}
		System.out.println("Error: account name must be between 3 and 30 alphanumberic characters"); //Print out saying why the account was not deleted.
		
		return null; //If account number is not valid, then account is not deleted.  Returns null.
	}//end delete
}//end Agent class
