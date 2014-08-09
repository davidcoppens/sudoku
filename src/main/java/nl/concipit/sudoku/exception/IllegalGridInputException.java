package nl.concipit.sudoku.exception;


public class IllegalGridInputException extends Exception {
	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = 268406604786536753L;


	/**
	 * Default constructor
	 */
	public IllegalGridInputException() {
		super();
	}
	
	/**
	 * Constructor with cause
	 * 
	 * @param cause Cause
	 */
	public IllegalGridInputException(Throwable cause) {
		super(cause);
	}

}
