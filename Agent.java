public class Agent extends Atm{
	public Agent(){
		super();
	}//end constructor

	public String create(int acc, String name){
		return "CR " + acc + " 00000000 " + "000 " +name;

	}//end end create

	public String delete(int acc, String name){
		return "DL " + acc + " 00000000 " + "000 " +name;
	}//end delete
}//end Agent class
