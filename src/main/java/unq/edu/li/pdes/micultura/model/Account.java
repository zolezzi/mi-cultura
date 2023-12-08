package unq.edu.li.pdes.micultura.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "account")
public class Account {

	@Id
	@Column(name = "id", unique = true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column
    private String firstname;
    
	@Column
	private String lastname;

	@Column
    private String dni;
	
	@Column
    private String address;
	
	@Column(name="phone_number")
    private String phoneNumber;
	
	@Enumerated(EnumType.STRING)
	private AccountRole accountRole;
}
