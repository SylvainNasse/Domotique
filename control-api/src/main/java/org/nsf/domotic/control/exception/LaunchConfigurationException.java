/**
 * 
 */
package org.nsf.domotic.control.exception;

/**
 * @author Nassouille
 *
 */
public class LaunchConfigurationException extends RuntimeException {

	/** serial. */
	private static final long serialVersionUID = 5303789359730745602L;

	public LaunchConfigurationException(final String message, final Exception cause) {
		super(message, cause);
	}

	public LaunchConfigurationException(final String message) {
		super(message);
	}


}
