package unq.edu.li.pdes.micultura.model;

public enum AccountRole {

	ADMIN("ADMIN"),
	TOURIST("TURISTA"),
	VISITOR("VISITANTE");
	
	private String description;
	
	// private enum constructor
	private AccountRole(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}
