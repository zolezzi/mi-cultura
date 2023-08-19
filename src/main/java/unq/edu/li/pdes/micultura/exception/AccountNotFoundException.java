package unq.edu.li.pdes.micultura.exception;

public class AccountNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public AccountNotFoundException(String msg){
		super(msg);
	}

}
