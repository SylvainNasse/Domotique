/**
 * 
 */
package org.nsf.domotic.control;

import io.moquette.server.Server;

/**
 * Facade class for MQTT broker
 * @author Nassouille
 */
public class MqttBrokerFacade {

	private static Server server;

	public static void init(final Server mqttBroker) {
		server = mqttBroker;
	}

}
