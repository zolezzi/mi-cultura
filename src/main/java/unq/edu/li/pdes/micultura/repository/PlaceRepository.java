package unq.edu.li.pdes.micultura.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import unq.edu.li.pdes.micultura.model.Place;

public interface PlaceRepository extends JpaRepository<Place, Long>{

	@Query("SELECT p FROM Place p WHERE  p.placeId = (:placeId) ")
	Optional<Place> findByPlaceId(@Param("placeId") Long placeId);

}
