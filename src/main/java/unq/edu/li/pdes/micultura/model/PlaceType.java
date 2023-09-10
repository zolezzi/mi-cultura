package unq.edu.li.pdes.micultura.model;

public enum PlaceType {

	MUSEUM("MUSEO"),
	INSTITUTE("INSTITUTO"),
	AGENCIES("ORGANISMO");
	
	private String description;
	
	// private enum constructor
	private PlaceType(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}
