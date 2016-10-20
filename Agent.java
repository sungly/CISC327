public class Agent extends Atm{
	public Agent(){
		super(99999999);
	}//end constructor

	public String create(String[] args){
		if(args[1].length() >= 3 && args[1].length()<=30){
			System.out.println("Creating account " + args[0]);
			return "CR " + args[0] + " 00000000 000 " +args[1];
		}
		System.out.println("Error: account name must be between 3 and 30 alphanumberic characters");
		return null;

	}//end end create

	public String delete(String[] args){
		if(args[1].length() >= 3 && args[1].length()<=30){
			System.out.println("Deleting account " + args[0]);
			return "DL " + args[0] + " 00000000 000 " +args[1];
		}
		System.out.println("Error: account name must be between 3 and 30 alphanumberic characters");
		
		return null;
	}//end delete
}//end Agent class
