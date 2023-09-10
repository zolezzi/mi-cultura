package unq.edu.li.pdes.micultura.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "place")
public class Place {

	@Id
	@Column(name = "id", unique = true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "url")
	private String url;
	
	private String link;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "address")
	private String address;
	
	private String phoneNumber;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "province")
	private String province;
	
	@Column(name = "depends_on")
	private String dependsOn;
	
	@Enumerated(EnumType.STRING)
	private PlaceType placeType;
	
	@OneToOne(optional = true)
	private Authority authority;
	
}
