package org.nsf.domotic.control.config;

import java.util.Map;

public abstract class ArgumentConsumer {

	private String propName;
	private String labelInCommandLine;
	
	public String getLabelInCommandLine() {
		return labelInCommandLine;
	}
	public void setLabelInCommandLine(String labelInCommandLine) {
		this.labelInCommandLine = labelInCommandLine;
	}
	protected ArgumentConsumer(final String pPropName){
		this.propName = pPropName;
	}
	public void store(Map<String, Object> mapParams) {
		mapParams.put(propName, getValue());
	}
	protected abstract Object getValue();
	protected abstract boolean isSatified();
	protected abstract boolean canAcceptMore();
	protected abstract void read(String tempArg);
	
}