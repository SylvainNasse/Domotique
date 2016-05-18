/**
 * 
 */
package org.nsf.domotic.control.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;
import org.nsf.domotic.control.exception.ConfigurationException;
import org.nsf.domotic.control.exception.LaunchConfigurationException;

import io.moquette.BrokerConstants;

/**
 * Configuration class for server
 * @author Nassouille
 */
public class Config {
	
	protected static final int DEFAULT_REST_PORT = 8085;
	protected static final int DEFAULT_MQTT_PORT = BrokerConstants.PORT;
	
	private static final Config INSTANCE = new Config();
	
	// TODO : mettre une intégration maven propre
	private static final String VERSION_STRING = "1.0.0";

	
	private RestConfig restConfig;
	private MqttConfig mqttConfig;
	private UIConfig uiConfig;
	
	
	/** 
	 * constructeur privé pour le singleton
	 */
	private Config() {
		reset();
	}
	
	
	protected void reset() {
		restConfig = new RestConfig();
		mqttConfig = new MqttConfig();
		uiConfig = new UIConfig();
	}
	

	/** 
	 * parse la config fournie par les arguments en ligne de commande
	 * 
	 * @param args les arguments en ligne de commande
	 */
	public static void parse(final String[] args) {
		
		
		final Map<String, Object> mapParams = new HashMap<String, Object>();
		
		ArgumentConsumer nextArgumentConsumer = null;
		ArgumentConsumer lastArgumentConsumer = null;
		String lastArgumentRead = "";
		boolean valueIsExpected;
		boolean consumerIsExpected;
		boolean consumerHasChanged;
		boolean firstRead = true;
		for (String tempArg : args) {
			
			valueIsExpected = (lastArgumentConsumer != null) && (!lastArgumentConsumer.isSatified());
			consumerIsExpected = (lastArgumentConsumer == null) || (!lastArgumentConsumer.canAcceptMore());
					
			nextArgumentConsumer = null;
			switch(tempArg) {
				case "-rp" :
				case "--restport" :
					nextArgumentConsumer = new PortNumberArgumentConsumer("restConfig.port");
					break;
				case "-mp" :
				case "--mqttport" :
					nextArgumentConsumer = new PortNumberArgumentConsumer("mqttConfig.port");
					break;
				case "-ui" :
				case "--servergui" :
					nextArgumentConsumer = new FlagArgumentConsumer("uiConfig.mqttBrokerUiStartFlag");
			}
			if (nextArgumentConsumer != null) {
				nextArgumentConsumer.setLabelInCommandLine(tempArg);
			}
			consumerHasChanged = (!firstRead) && (nextArgumentConsumer != null);
			firstRead = false;
			
			// cas d'erreur
			if (consumerIsExpected && nextArgumentConsumer == null) {
				throw new LaunchConfigurationException("Misplaced parameter or unknown flag : " + tempArg + ". Expected specific flag.");
			}
			if (valueIsExpected && (nextArgumentConsumer != null)) {
				throw new LaunchConfigurationException("Misplaced flag : " + tempArg + ". A value for previous flag " + lastArgumentRead + " was expected.");
			}
		
			// traitement de la valeur lue
			if (nextArgumentConsumer == null) {
				// on a lu une valeur : on la stocke dans le consumer "courant"
				lastArgumentConsumer.read(tempArg);
			}
			
			if (consumerHasChanged) {
				if (! lastArgumentConsumer.isSatified()) {
					throw new LaunchConfigurationException("parameter not satisfied : " + lastArgumentConsumer.getLabelInCommandLine());
				} else {
					lastArgumentConsumer.store(mapParams);
				}
			}
			
			// pr�paration de la prochaine boucle
			lastArgumentConsumer = nextArgumentConsumer == null ? lastArgumentConsumer : nextArgumentConsumer; 
			lastArgumentRead = tempArg;
		}
		
		// sortie de boucle
		if (lastArgumentConsumer != null) {
			if (! lastArgumentConsumer.isSatified()) {
				throw new LaunchConfigurationException("parameter not satisfied : " + lastArgumentConsumer.getLabelInCommandLine());
			}
			lastArgumentConsumer.store(mapParams);
		}
		
		
		storeValues(mapParams);
	}
	
	/**
	 * stores the values in the config objects
	 * @param mapParams the map containing the params
	 */
	private static void storeValues(Map<String, Object> mapParams) {
		String tempProperty = "";
		Object tempValue = null;
		try {
			for (Entry<String, Object> tempEntry : mapParams.entrySet()) {
				tempProperty = tempEntry.getKey();
				tempValue = tempEntry.getValue();
				BeanUtils.copyProperty(getInstance(), tempProperty, tempValue);
			}
		} catch (Exception e) {
			final String message = "Impossible to set property '" + tempProperty + "' with value " + tempValue;
			System.err.println(message);
			throw new ConfigurationException(message, e);
		}
	}

	public static class UIConfig {
		private Boolean mqttBrokerUiStartFlag = Boolean.FALSE;
				public Boolean getMqttBrokerUiStartFlag() {
			return mqttBrokerUiStartFlag;
		}
		public void setMqttBrokerUiStartFlag(Boolean mqttBrokerUiStartFlag) {
			this.mqttBrokerUiStartFlag = mqttBrokerUiStartFlag;
		}
	}

	public static class MqttConfig {
       private int port = DEFAULT_MQTT_PORT;
        
        
        @Override
        public String toString() {
            return getClass().getSimpleName();
        }


		public int getPort() {
			return port;
		}


		public void setPort(int port) {
			this.port = port;
		}
    }
	
	public static class RestConfig {
        private int port = DEFAULT_REST_PORT;
        
        
        @Override
        public String toString() {
            return getClass().getSimpleName();
        }


		public int getPort() {
			return port;
		}


		public void setPort(int port) {
			this.port = port;
		}
    }

	
	public static Config getInstance() {
		return INSTANCE;
	}
	
	public RestConfig getRestConfig() {
		return INSTANCE.restConfig;
	}

	public MqttConfig getMqttConfig() {
		return INSTANCE.mqttConfig;
	}

	public UIConfig getUiConfig() {
		return INSTANCE.uiConfig;
	}

	
	
	
}
