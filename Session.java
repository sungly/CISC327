/**
 * Abstract Class used to wrap Atm and Agent classes. Done in order to allow to 
 * store an atm instance or agent instance in a singular object type Session without having to cast
 * the object when a method call is required. Defines all transactions that can be made
 * in all sessions. If another type of session is to be defined with a different set of
 * transactions. The transaction header would be defined here and session would extend this
 * class
 * 
 * @author 13ttm
 *
 */
public abstract class Session {
	public abstract String deposit(String[]args);
	public abstract String withdraw(String[]args);
	public abstract String transfer(String[]args);
	public abstract String create(String[]args);
	public abstract String delete(String[]args);
}
