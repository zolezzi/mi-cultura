package unq.edu.li.pdes.micultura.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "account_review_event")
public class AccountReviewEvent {
	@Id
	@Column(name = "id", unique = true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
	private Account account;
	
	@OneToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id")
	private Event event;
	
	@OneToOne
    @JoinColumn(name = "review_id", referencedColumnName = "id")
	private Review review;
}
