import java.util.HashMap;

public class Agent extends Session{ //subclass of the Atm class.
	private int maxTransAmmount; // Declaring integer for the maximum withdraw/deposit/transfer.
	private HashMap<String,Integer> accWithdrawn; // Declaring a HashMap to keep track of how much an account has withdrawn this session.

	public Agent(){
		this.accWithdrawn = new HashMap<String,Integer>();
		this.maxTransAmmount = 99999999; //initializing the maximum withdraw/deposit/transfer amount for an ATM.
	} //end constructor.

 /*Method to create a new account
	@Params: args[0] -> account number
					 args[1] -> account name
 */
	public String create(String[] args){
		if((args[1].length() >= 3 && args[1].length()<=30) && !(args[1].startsWith(" ") || args[1].charAt(args[1].length() -1) == ' ')){
			System.out.println("Creating account " + args[0]);
			return "CR " + args[0] + " 00000000 000 " +args[1];
		}
		System.out.println("Error: account name must be between 3 and 30 alphanumberic characters");
		return null;
	}//end create

	/*Method to delete an accoutn
	 @Params: args[0] -> account number
						args[1] -> account name
	*/
	public String delete(String[] args){
		if(args[1].length() >= 3 && args[1].length()<=30){ //if statement to check if the account name is within 3 and 30 alphanumeric characters.
			System.out.println("Deleting account " + args[0]);
			return "DL " + args[0] + " 00000000 000 " +args[1];
		}
		System.out.println("Error: account name must be between 3 and 30 alphanumberic characters");

		return null;
	}//end delete

/*Method to deposit money into an account.
@Params: args[0] -> account number
				 args[1] -> amount
*/
	public String deposit(String[] args){
		if ( Integer.parseInt(args[1]) <= this.maxTransAmmount && Integer.parseInt(args[1])>0){ =
			System.out.println("Depositing "+ args[1] + " cents into account " + args[0]);
			return "DE " + args[0] + " 00000000 " + args[1] + " ***";
		}
		System.out.println("Error: amount deposited must be between 0 and " + this.maxTransAmmount);
		return null;
	} //end deposit.

	/*Method to widthraw money from an account.
	@Params: args[0] -> account number
					 args[1] -> amount
	*/
	public String withdraw(String[] args){
		if(Integer.parseInt(args[1]) <= this.maxTransAmmount && Integer.parseInt(args[1])>0){ //Making sure the provided amount to withdraw is valid. (Less then the maximum withdrawal amount and greater then 0)
			int withdrawn; // Declaring an integer to hold the amount that an account has already withdrawn.
			try{
				withdrawn =this.accWithdrawn.get(args[0]).intValue();
			}catch(NullPointerException e){ //throws an exception when we cannot find an amount that an account has withdrawn in this session.
				withdrawn = 0; //Since we cannot find an amount that an account has withdrawn that means it has not withdrawn in that session, therefore withdrawn amount is 0.
			}
			if(withdrawn + Integer.parseInt(args[1]) <= this.maxTransAmmount){
				System.out.println("Withdrawing "+ args[1] + " cents from account " + args[0]);
				this.accWithdrawn.put(args[0],new Integer(withdrawn + Integer.parseInt(args[1])));
				return "WD 00000000 " + args[0] + " " + args[1] + " ***";
			}else{
				System.out.println("Withdraw limit has been reached on account");
				return null;
			}
		}
		System.out.println("Error: amount withdrawed must be between 0 and " + this.maxTransAmmount);
		return null;
	} //end withdraw.

	/*Method to transfer money from an account to another
	@Params: args[0] -> account number from
					 args[1] -> account number to
					 args[2] -> amount to transfer
	*/
	public String transfer(String[] args){
		if(Integer.parseInt(args[2]) <= this.maxTransAmmount && Integer.parseInt(args[2])>0){ //Checking that the provided amount to transfer is valid.  (Less then the maximum transfer amount and greater then 0)
			System.out.println("Transfering " + args[2] + " cents from account " + args[0] + " to account "+ args[1]);
			return "TR " + args[0] + " " + args[1] + " " + args[2] + " ***";
		}
		System.out.println("Error: amount transfered must be between 0 and " + this.maxTransAmmount);
		return null;
	} //end transfer.
}//end Agent class
