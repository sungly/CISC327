public class Agent extends Atm{
	private final static int MAX_TRANS_AMMOUNT = 99 999 999;

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
