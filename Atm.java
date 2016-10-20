import java.util.*;
public class Atm{
	
	private int maxTransAmmount;
	private HashMap<String,Integer> accWithdrawn;
	
	public Atm(){
		this.accWithdrawn = new HashMap<String,Integer>();
		this.maxTransAmmount = 100000;
	}
	
	public Atm(int maxTransAmmount){
		this.maxTransAmmount = maxTransAmmount;
	}
	
	public String deposit(String[] args){
		if ( Integer.parseInt(args[1]) <= this.maxTransAmmount && Integer.parseInt(args[1])>0){
			System.out.println("Depositing "+ args[1] + " cents into account " + args[0]);
			return "DE " + args[0] + " 00000000 " + args[1] + " ***";
		}
		System.out.println("Error: amount deposited must be between 0 and " + this.maxTransAmmount);
		return null;
	}
	
	public String withdraw(String[] args){
		if(Integer.parseInt(args[1]) <= this.maxTransAmmount && Integer.parseInt(args[1])>0){
			int withdrawn;
			try{
				withdrawn =this.accWithdrawn.get(args[0]).intValue();
			}catch(NullPointerException e){
				withdrawn = 0;
			}
			if(withdrawn + Integer.parseInt(args[1]) <= this.maxTransAmmount){
				System.out.println("Withdrawing "+ args[1] + " cents from account " + args[0]);
				this.accWithdrawn.put(args[0],new Integer(withdrawn + Integer.parseInt(args[1])));
				return "WD 00000000 " + args[0] + " " + args[1] + " ***";
			}else{
				System.out.println("Withdraw limit has been reached on account");
			}
		}
		System.out.println("Error: amount withdrawed must be between 0 and " + this.maxTransAmmount);
		return null;
	}
	
	public String transfer(String[] args){
		if(Integer.parseInt(args[2]) <= this.maxTransAmmount && Integer.parseInt(args[1])>0){
			System.out.println("Transfering " + args[2] + " cents from account " + args[0] + " to account "+ args[1]);
			return "TR " + args[0] + " " + args[1] + " " + args[2] + " ***"; 
		}
		System.out.println("Error: amount transfered must be between 0 and " + this.maxTransAmmount);
		return null;
	}
	
}