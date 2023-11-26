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
@Table(name = "event")
public class Event {

	@Id
	@Column(name = "id", unique = true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "event_id")
	private Long eventId;
	
	@Column(name = "url")
	private String url;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "sub_title")
	private String subTitle;
	
	@Column(name = "body")
	private String body;
	
	@Column(name = "depends_on")
	private String dependsOn;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "link")
	private String link;
	
	@Column(name = "image")
	private String image;
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "from_date")
	private String fromDate;
	
	@Column(name = "to_date")
	private String toDate;
	
	@Enumerated(EnumType.STRING)
	private EventType eventType;
}
