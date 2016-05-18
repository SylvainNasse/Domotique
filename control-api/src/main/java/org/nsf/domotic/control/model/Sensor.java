/**
 * 
 */
package org.nsf.domotic.control.model;

/**
 * représente un capteur
 * @author Nassouille
 *
 */
public class Sensor {

	/** ID du capteur. */
	private String id;
	
	/** Indicateur de l'etat du capteur. voir #EnumSensorStatus*/
	private String status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "[Sensor:id=" + id + "]";
	}
	
	
	
	
}
