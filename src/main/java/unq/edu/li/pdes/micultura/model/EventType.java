package unq.edu.li.pdes.micultura.model;

public enum EventType {

	ANNOUNCEMENT("CONVOCATORIA"),
	PROCEDURE("TRAMITE");
	
	private String description;
	
	// private enum constructor
	private EventType(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}
