/**
 * 
 */
package org.nsf.domotic.control.config;

import org.nsf.domotic.control.exception.LaunchConfigurationException;

/**
 * @author sylvain_nasse
 *
 */
public class PortNumberArgumentConsumer extends ArgumentConsumer {

	private Integer value = null;
	
	protected PortNumberArgumentConsumer(final String pPropName) {
		super(pPropName);
	}

	
	@Override
	protected Integer getValue() {
		return this.value;
	}

	@Override
	protected boolean isSatified() {
		return value != null;
	}

	@Override
	protected boolean canAcceptMore() {
		return value == null;
	}

	@Override
	protected void read(String tempArg) {
		try {
			this.value = Integer.valueOf(tempArg);
		} catch (NumberFormatException e) {
			throw new LaunchConfigurationException("port number is not a number : '" + tempArg + "'");
		}
		
		if (this.value < 0 || this.value > 65535) {
			throw new LaunchConfigurationException("Port number is out of range : " + this.value);
		}
	}



}
