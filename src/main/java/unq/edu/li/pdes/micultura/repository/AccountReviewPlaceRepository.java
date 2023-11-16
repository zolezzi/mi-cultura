package unq.edu.li.pdes.micultura.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import unq.edu.li.pdes.micultura.model.AccountReviewPlace;

public interface AccountReviewPlaceRepository  extends JpaRepository<AccountReviewPlace, Long>{

	@Query("SELECT SUM(review.score) / COUNT(arp.id) FROM AccountReviewPlace arp inner join arp.review as review WHERE arp.place.placeId = (:placeId) ")
	BigDecimal getTotalReviewScore(@Param("placeId") Long placeId);

	@Query("SELECT arp FROM AccountReviewPlace arp WHERE  arp.place.placeId = (:placeId) AND  arp.account.id = (:accountId)")
	Optional<AccountReviewPlace> findOneByPlaceIdAndAccountId(@Param("placeId") Long placeId, @Param("accountId") Long accountId);

}
