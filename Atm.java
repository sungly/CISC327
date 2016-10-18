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
	
	public String deposit(int acc, int amt){
		if ( amt <= this.maxTransAmmount){
			return "DE " + acc + " 00000000 " + amt + " ***";
		}
		return null;
	}
	
	public String withdraw(int acc, int amt){
		if(amt <= this.maxTransAmmount){
			if(this.withdrawn + amt <= this.maxTransAmmount)
				return "WD 00000000" + acc + " " + amt + " ***";
			this.withdrawn = this.withdrawn + amt;
		}
		return null;
	}
	
	public String transfer(int accFrom, int accTo, int amt){
		if(amt <= this.maxTransAmmount){
			return "TR " + accFrom + " " + accTo + " " + amt + " ***"; 
		}
		return null;
	}
	
}