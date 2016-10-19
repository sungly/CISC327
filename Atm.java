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
		if ( Integer.parseInt(args[1]) <= this.maxTransAmmount){
			return "DE " + args[0] + " 00000000 " + args[1] + " ***";
		}
		return null;
	}
	
	public String withdraw(String[] args){
		if(Integer.parseInt(args[1]) <= this.maxTransAmmount){
			int withdrawn;
			try{
				withdrawn =this.accWithdrawn.get(args[0]).intValue();
			}catch(NullPointerException e){
				withdrawn = 0;
			}
			if(withdrawn + Integer.parseInt(args[1]) <= this.maxTransAmmount){
				this.accWithdrawn.put(args[0],new Integer(withdrawn + Integer.parseInt(args[1])));
				return "WD 00000000 " + args[0] + " " + args[1] + " ***";
			}else{
				System.out.println("Withdraw limit has been reached on account");
			}
		}
		return null;
	}
	
	public String transfer(String[] args){
		if(Integer.parseInt(args[2]) <= this.maxTransAmmount){
			return "TR " + args[0] + " " + args[1] + " " + args[2] + " ***"; 
		}
		return null;
	}
	
}