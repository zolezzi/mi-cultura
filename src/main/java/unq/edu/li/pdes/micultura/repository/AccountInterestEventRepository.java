package unq.edu.li.pdes.micultura.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import unq.edu.li.pdes.micultura.model.AccountInterestEvent;

public interface AccountInterestEventRepository extends JpaRepository<AccountInterestEvent, Long>{

	@Query("SELECT aie FROM AccountInterestEvent aie WHERE aie.event.id = (:eventId) AND aie.account.id = (:accountId) ")
	AccountInterestEvent findByEventIdAndAccountId(@Param("eventId") Long eventId, @Param("accountId") Long accountId);
	
	@Query("SELECT aie FROM AccountInterestEvent aie WHERE aie.event.id = (:eventId) AND aie.account.id = (:accountId) ")
	Optional<AccountInterestEvent> findOneEventIdAndAccountId(@Param("eventId") Long eventId, @Param("accountId") Long accountId);
	
	@Query("SELECT aie FROM AccountInterestEvent aie WHERE  aie.account.id = (:accountId) ")
	List<AccountInterestEvent> findAllEventsByAccountId(@Param("accountId") Long accountId);
}
