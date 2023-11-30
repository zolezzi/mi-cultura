package unq.edu.li.pdes.micultura.service;

import java.math.BigDecimal;
import java.util.List;

import unq.edu.li.pdes.micultura.dto.EventDTO;
import unq.edu.li.pdes.micultura.vo.EventVO;
import unq.edu.li.pdes.micultura.vo.ReviewVO;

public interface EventService {

	EventDTO findById(Long eventId);
	
	void removeFavorite(Long accountId, Long eventId) throws Exception;
	
	EventDTO save(EventVO placeVO, Long userId, Long eventId);
	
	EventDTO update(ReviewVO review, Long userId, Long eventId);
	
	EventDTO favorite(EventVO eventVO, Long accountId, Long eventId);
	
	List<EventDTO> findAllByUserId(Long userId);
	
	List<EventDTO> findAll();
	
	BigDecimal getTotalReviewScore(Long placeId);
}
