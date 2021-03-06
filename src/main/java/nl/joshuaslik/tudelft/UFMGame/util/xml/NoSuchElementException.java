package nl.joshuaslik.tudelft.UFMGame.util.xml;

/**
 * @author <a href="http://www.joshuaslik.nl/" target="_blank">Joshua Slik</a>
 *
 */
public class NoSuchElementException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3704509663698448420L;

	/**
	 * 
	 */
	public NoSuchElementException() {
		super();
	}

	/**
	 * 
	 * @param message
	 *            is the exception message to pass through.
	 */
	public NoSuchElementException(String message) {
		super(message);
	}

}
