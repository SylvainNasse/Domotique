/**
 * 
 */
package org.nsf.domotic.control;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.UriBuilder;

import org.nsf.domotic.control.config.Config;
import org.nsf.domotic.control.mqttlisteners.PublisherListener;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.net.httpserver.HttpServer;

import io.moquette.interception.InterceptHandler;
import io.moquette.server.Server;
import io.moquette.server.config.ClasspathConfig;
import io.moquette.server.config.IConfig;

/**
 * @author Nassouille
 *
 */
public class Main {

	
	/**
	 * @param args
	 */
	public static void main(final String[] args) throws IOException {
		// parse arguments
		Config.parse(args);
		
		// start MQTT broker
		System.out.println("Starting Moquette MQTT broker ...");

        final Server mqttBroker = new Server();
        List<? extends InterceptHandler> userHandlers = Arrays.asList(new PublisherListener());
        
        final IConfig classPathConfig = new ClasspathConfig();
        tweakConfig(Config.getInstance(), classPathConfig);
        
		mqttBroker.startServer(classPathConfig, userHandlers);
        //Bind  a shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("Stopping broker");
                mqttBroker.stopServer();
                System.out.println("Broker stopped");
            }
        });
        
		// set facade
		MqttBrokerFacade.init(mqttBroker);
		
		// start Jersey
		System.out.println("Starting Crunchify's Embedded Jersey HTTPServer...");
		HttpServer crunchifyHTTPServer = createHttpServer();
		crunchifyHTTPServer.start();
		System.out.println(String.format("Jersey Application Server started with WADL available at " + "%sapplication.wadl\n", getCrunchifyURI()));
		System.out.println("Started Crunchify's Embedded Jersey HTTPServer Successfully.\n");
		// add shutdown hook for jersey server
		System.out.println("Binding shutdown hook for Jersey server ...");
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				mqttBroker.stopServer();
			}
		});
		System.out.println("Shutdown hook for Jersey server bound.\n");
		
	}


	

	private static void tweakConfig(Config instance, IConfig classPathConfig) {
		classPathConfig.setProperty("port", String.valueOf(instance.getMqttConfig().getPort()));
	}




	private static HttpServer createHttpServer() throws IOException {
		ResourceConfig crunchifyResourceConfig = new PackagesResourceConfig("org.nsf.domotic.control.api");
		final Map<String, Object> config = new HashMap<String, Object>();
		config.put("com.sun.jersey.api.json.POJOMappingFeature", true);
		crunchifyResourceConfig.setPropertiesAndFeatures(config);

		// This tutorial required and then enable below line: http://crunfy.me/1DZIui5
		//crunchifyResourceConfig.getContainerResponseFilters().add(CrunchifyCORSFilter.class);
		return HttpServerFactory.create(getCrunchifyURI(), crunchifyResourceConfig);
	}

	private static URI getCrunchifyURI() {
		return UriBuilder.fromUri(
					"http://" + crunchifyGetHostName() + "/")
				.port(Config.getInstance().getRestConfig().getPort())
				.build();
	}

	private static String crunchifyGetHostName() {
		String hostName = "localhost";
		try {
			hostName = InetAddress.getLocalHost().getCanonicalHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return hostName;
	}
}
