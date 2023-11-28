package unq.edu.li.pdes.micultura.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import unq.edu.li.pdes.micultura.model.AccountReviewEvent;

public interface AccountReviewEventRepository extends JpaRepository<AccountReviewEvent, Long>{

	@Query("SELECT SUM(review.score) / COUNT(are.id) FROM AccountReviewEvent are inner join are.review as review WHERE are.event.eventId = (:eventId) ")
	BigDecimal getTotalReviewScore(@Param("eventId") Long eventId);

	@Query("SELECT are FROM AccountReviewEvent arp WHERE are.event.eventId = (:eventId) AND  are.account.id = (:accountId)")
	Optional<AccountReviewEvent> findOneByEventIdAndAccountId(@Param("eventId") Long eventId, @Param("accountId") Long accountId);
}
