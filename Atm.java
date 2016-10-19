public class Atm{
	
	private int maxTransAmmount;
	private int withdrawn;
	
	public Atm(){
		this.withdrawn = 0;
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
			if(this.withdrawn + Integer.parseInt(args[1]) <= this.maxTransAmmount)
				return "WD 00000000" + args[0] + " " + args[1] + " ***";
			this.withdrawn = this.withdrawn + Integer.parseInt(args[1]);
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