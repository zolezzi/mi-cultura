package unq.edu.li.pdes.micultura.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import unq.edu.li.pdes.micultura.model.Event;

public interface EventRepository extends JpaRepository<Event, Long>{
	
	@Query("SELECT e FROM Event e WHERE  e.eventId = (:eventId) ")
	Optional<Event> findByEventId(@Param("eventId") Long eventId);
}
