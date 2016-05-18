package org.nsf.domotic.control.api;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.nsf.domotic.control.model.EnumSensorStatus;
import org.nsf.domotic.control.model.Sensor;
 
@Path(ApiConstants.SENSORS)
public class SensorService {
	
	
	@GET
	@Path(ApiConstants.LIST)
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * Liste les capteurs
	 * @return la liste des capteurs
	 */
	public List<Sensor> listSensors() {
 
		// mock
		final List<Sensor> response = new ArrayList<Sensor>();
		final Sensor sensor1 = new Sensor();
		sensor1.setId("SALON_FENETRE_1");
		sensor1.setStatus(EnumSensorStatus.OK.name());
		response.add(sensor1);
		final Sensor sensor2 = new Sensor();
		sensor2.setId("SALON_LUMIERE");
		sensor2.setStatus(EnumSensorStatus.LIMITED.name());
		response.add(sensor2);
		return response;
	}

	
}
