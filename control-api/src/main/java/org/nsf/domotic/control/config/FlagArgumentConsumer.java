/**
 * 
 */
package org.nsf.domotic.control.config;

import org.nsf.domotic.control.exception.LaunchConfigurationException;

/**
 * @author sylvain_nasse
 *
 */
public class FlagArgumentConsumer extends ArgumentConsumer {

	
	protected FlagArgumentConsumer(final String pPropName) {
		super(pPropName);
	}

	@Override
	protected Object getValue() {
		return Boolean.TRUE;
	}

	@Override
	protected boolean isSatified() {
		return true;
	}

	@Override
	protected boolean canAcceptMore() {
		return false;
	}

	@Override
	protected void read(final String tempArg) {
		throw new LaunchConfigurationException("Flag-typed arguments should not be provided with a value");
	}

	
}
