import java.util.*;
public class Atm extends Session{

	private int maxTransAmmount; // Declaring integer for the maximum withdraw/deposit/transfer.
	private HashMap<String,Integer> accWithdrawn; // Declaring a HashMap to keep track of how much an account has withdrawn this session.

	public Atm(){
		this.accWithdrawn = new HashMap<String,Integer>();
		this.maxTransAmmount = 100000; //initializing the maximum withdraw/deposit/transfer amount for an ATM.
	} //end constructor.

	public Atm(int maxTransAmmount){ // Constructor for an ATM.  We use this constructor mainly to help create an agent object.
		this.maxTransAmmount = maxTransAmmount; //We change the maximum withdraw/deposit/transfer amount to a new one(The parameter maxTransAmmount)
	} //end constructor.

	/*Method to deposit money into an account.
	@Params: args[0] -> account number
					 args[1] -> amount
	*/
	public String deposit(String[] args){
		if ( Integer.parseInt(args[1]) <= this.maxTransAmmount && Integer.parseInt(args[1])>0){ //Making sure the provided amount to deposit is valid.
			System.out.println("Depositing "+ args[1] + " cents into account " + args[0]);
			return "DE " + args[0] + " 00000000 " + args[1] + " ***";
		}
		System.out.println("Error: amount deposited must be between 0 and " + this.maxTransAmmount);
		return null;
	} //end deposit.

	/*Method to withdraw money from an account.
	@Params: args[0] -> account number
					 args[1] -> amount
	*/
	public String withdraw(String[] args){
		if(Integer.parseInt(args[1]) <= this.maxTransAmmount && Integer.parseInt(args[1])>0){ //Making sure the provided amount to withdraw is valid. (Less then the maximum withdrawal amount and greater then 0)
			int withdrawn; // Declaring an integer to hold the amount that an account has already withdrawn.
			try{
				withdrawn =this.accWithdrawn.get(args[0]).intValue();
			}catch(NullPointerException e){
				withdrawn = 0; //Since we cannot find an amount that an account has withdrawn that means it has not withdrawn in that session, therefore withdrawn amount is 0.
			}
			if(withdrawn + Integer.parseInt(args[1]) <= this.maxTransAmmount){ //Checking if the account has not exceeded their maximum withdrawal amount in this session.
				System.out.println("Withdrawing "+ args[1] + " cents from account " + args[0]);
				this.accWithdrawn.put(args[0],new Integer(withdrawn + Integer.parseInt(args[1]))); //Saving the total amount that this account has withdrawn this session.
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

	/**
	 * Left empty because atm cannot perform a create transaction
	 *
	 * @return 		Returns null
	 */
	public String create(String[] args) {
		// TODO Auto-generated method stub
		return null;
	}//end create

	/**
	 * Left empty because agent cannot perform a delete transaction
	 *
	 * @return 		Returns null
	 */
	public String delete(String[] args) {
		// TODO Auto-generated method stub
		return null;
	}//end delete


}//end Atm
