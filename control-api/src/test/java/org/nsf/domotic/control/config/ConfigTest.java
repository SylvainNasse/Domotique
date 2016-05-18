package org.nsf.domotic.control.config;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ConfigTest {

	@Before
	public void reinit() {
		Config.getInstance().reset();
	}
	
	@Test
	public void test1() {
		String[] args = new String[] {};
		Config.parse(args);

		assertEquals("pas le bon port REST", Config.DEFAULT_REST_PORT, Config.getInstance().getRestConfig().getPort());
		assertEquals("pas le bon port MQTT", Config.DEFAULT_MQTT_PORT, Config.getInstance().getMqttConfig().getPort());

		assertEquals("pas le bon flag UI serveur", Boolean.FALSE, Config.getInstance().getUiConfig().getMqttBrokerUiStartFlag());

	}

	@Test
	public void test5() {
		String[] args = new String[] {"-rp", "12"};
		Config.parse(args);

		assertEquals("pas le bon port REST", 12, Config.getInstance().getRestConfig().getPort());
		assertEquals("pas le bon port MQTT", Config.DEFAULT_MQTT_PORT, Config.getInstance().getMqttConfig().getPort());

		assertEquals("pas le bon flag UI serveur", Boolean.FALSE, Config.getInstance().getUiConfig().getMqttBrokerUiStartFlag());
	}
	
	@Test
	public void test6() {
		String[] args = new String[] {"--restport", "12"};
		Config.parse(args);

		assertEquals("pas le bon port REST", 12, Config.getInstance().getRestConfig().getPort());
		assertEquals("pas le bon port MQTT", Config.DEFAULT_MQTT_PORT, Config.getInstance().getMqttConfig().getPort());

		assertEquals("pas le bon flag UI serveur", Boolean.FALSE, Config.getInstance().getUiConfig().getMqttBrokerUiStartFlag());
	}
	
	
	@Test
	public void test7() {
		String[] args = new String[] {"-mp", "125"};
		Config.parse(args);

		assertEquals("pas le bon port REST", Config.DEFAULT_REST_PORT, Config.getInstance().getRestConfig().getPort());
		assertEquals("pas le bon port MQTT", 125, Config.getInstance().getMqttConfig().getPort());

		assertEquals("pas le bon flag UI serveur", Boolean.FALSE, Config.getInstance().getUiConfig().getMqttBrokerUiStartFlag());
	}
	
	@Test
	public void test8() {
		String[] args = new String[] {"--mqttport", "125"};
		Config.parse(args);

		assertEquals("pas le bon port REST", Config.DEFAULT_REST_PORT, Config.getInstance().getRestConfig().getPort());
		assertEquals("pas le bon port MQTT", 125, Config.getInstance().getMqttConfig().getPort());

		assertEquals("pas le bon flag UI serveur", Boolean.FALSE, Config.getInstance().getUiConfig().getMqttBrokerUiStartFlag());
	}
	
	
	@Test
	public void test9() {
		String[] args = new String[] {"--mqttport", "125", "-ui"};
		Config.parse(args);

		assertEquals("pas le bon port REST", Config.DEFAULT_REST_PORT, Config.getInstance().getRestConfig().getPort());
		assertEquals("pas le bon port MQTT", 125, Config.getInstance().getMqttConfig().getPort());

		assertEquals("pas le bon flag UI serveur", Boolean.TRUE, Config.getInstance().getUiConfig().getMqttBrokerUiStartFlag());
	}
	
	
}
