package unq.edu.li.pdes.micultura.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import unq.edu.li.pdes.micultura.model.AccountInterestPlace;

public interface AccountInterestPlaceRepository extends JpaRepository<AccountInterestPlace, Long>{

	@Query("SELECT aip FROM AccountInterestPlace aip WHERE aip.place.id = (:placeId) AND aip.account.id = (:accountId) ")
	AccountInterestPlace findByPlaceIdAndAccountId(@Param("placeId") Long placeId, @Param("accountId") Long accountId);
	
	@Query("SELECT aip FROM AccountInterestPlace aip WHERE aip.place.id = (:placeId) AND aip.account.id = (:accountId) ")
	Optional<AccountInterestPlace> findOnePlaceIdAndAccountId(@Param("placeId") Long placeId, @Param("accountId") Long accountId);
	
	@Query("SELECT aip FROM AccountInterestPlace aip WHERE  aip.account.id = (:accountId) ")
	List<AccountInterestPlace> findAllPlacesByAccountId(@Param("accountId") Long accountId);

}
