import java.util.*;
public class Atm extends Session{

	private int maxTransAmmount; // Declaring integer for the maximum withdraw/deposit/transfer.
	private HashMap<String,Integer> accWithdrawn; // Declaring a HashMap to keep track of how much an account has withdrawn this session.

	public Atm(){ //Constructor for an ATM
		this.accWithdrawn = new HashMap<String,Integer>(); //initializing the HashMap.
		this.maxTransAmmount = 100000; //initializing the maximum withdraw/deposit/transfer amount for an ATM.
	} //end constructor.

	public Atm(int maxTransAmmount){ // Constructor for an ATM.  We use this constructor mainly to help create an agent object.
		this.maxTransAmmount = maxTransAmmount; //We change the maximum withdraw/deposit/transfer amount to a new one(The parameter maxTransAmmount)
	} //end constructor.

	public String deposit(String[] args){ //Public method to deposit money into an account.
		if ( Integer.parseInt(args[1]) <= this.maxTransAmmount && Integer.parseInt(args[1])>0){ //Making sure the provided amount to deposit is valid.
			System.out.println("Depositing "+ args[1] + " cents into account " + args[0]); //Printout saying Depositing "amount to deposit" cents into account "account number".
			return "DE " + args[0] + " 00000000 " + args[1] + " ***"; //Returns a transaction message to put on the transaction summary file.
		}
		System.out.println("Error: amount deposited must be between 0 and " + this.maxTransAmmount); //Print out saying why no money was deposited.
		return null; //deposit failed, no transaction is completed.  Therefore, there is no transaction message, returns null.
	} //end deposit.

	public String withdraw(String[] args){ //Public method to withdraw money from an account.
		if(Integer.parseInt(args[1]) <= this.maxTransAmmount && Integer.parseInt(args[1])>0){ //Making sure the provided amount to withdraw is valid. (Less then the maximum withdrawal amount and greater then 0)
			int withdrawn; // Declaring an integer to hold the amount that an account has already withdrawn.
			try{
				withdrawn =this.accWithdrawn.get(args[0]).intValue(); //Trying to initialize withdraw with the amount an account has withdrawn.
			}catch(NullPointerException e){ //throws an exception when we cannot find an amount that an account has withdrawn in this session.
				withdrawn = 0; //Since we cannot find an amount that an account has withdrawn that means it has not withdrawn in that session, therefore withdrawn amount is 0.
			}
			if(withdrawn + Integer.parseInt(args[1]) <= this.maxTransAmmount){ //Checking if the account has not exceeded their maximum withdrawal amount in this session.
				System.out.println("Withdrawing "+ args[1] + " cents from account " + args[0]); //Printout saying withdrawing "amount to withdraw" cents from account "account number".
				this.accWithdrawn.put(args[0],new Integer(withdrawn + Integer.parseInt(args[1]))); //Saving the total amount that this account has withdrawn this session.
				//return "WD 00000000 " + args[0] + " " + args[1] + " ***"; //Returns a transaction message to put on the transaction summary.
				return "WD " + args[0] + " 00000000 " + args[1] + " ***";
			}else{
				System.out.println("Withdraw limit has been reached on account"); //Printing a message to tell the user that they have exceeded their withdraw limit on the account.
			}
		}
		System.out.println("Error: amount withdrawed must be between 0 and " + this.maxTransAmmount); //Printing a message explaining why the withdrawal failed. In this case is because the amount withdrawed was not between 0 and the maximum withdrawal amount.
		return null; //withdrawal failed, no transaction is completed.  Therefore, there is no transaction message, returns null.
	} //end withdraw.

	public String transfer(String[] args){ //Public method to transfer money from one account to another.
		if(Integer.parseInt(args[2]) <= this.maxTransAmmount && Integer.parseInt(args[2])>0){ //Checking that the provided amount to transfer is valid.  (Less then the maximum transfer amount and greater then 0)
			System.out.println("Transfering " + args[2] + " cents from account " + args[0] + " to account "+ args[1]); //Printout saying Transfering "Amount to transfer" cents from account "account a" to account "account b".
			return "TR " + args[0] + " " + args[1] + " " + args[2] + " ***"; //Returns a transaction message to put on the transaction summary file.
		}
		System.out.println("Error: amount transfered must be between 0 and " + this.maxTransAmmount); //Printing a message to tell the user they sent an invalid transfer amount.
		return null; //transfer failed, no transaction is completed.  Therefore, there is no transaction message, returns null.
	} //end transfer.

	/**
	 * Left empty because atm cannot perform a create transaction
	 *
	 * @return 		Returns null
	 */
	public String create(String[] args) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Left empty because agent cannot perform a delete transaction
	 *
	 * @return 		Returns null
	 */
	public String delete(String[] args) {
		// TODO Auto-generated method stub
		return null;
	}


}
