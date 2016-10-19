public class Agent extends Atm{
	public Agent(){
		super(99999999);
	}//end constructor

	public String create(String[] args){
		if(args[1].length() >= 3 && args[1].length()<=30)
			return "CR " + args[0] + " 00000000 " + "000 " +args[1];
		return null;

	}//end end create

	public String delete(String[] args){
		if(args[1].length() >= 3 && args[1].length()<=30)
			return "DL " + args[0] + " 00000000 " + "000 " +args[1];
		return null;
	}//end delete
}//end Agent class
