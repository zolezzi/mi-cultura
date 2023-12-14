package unq.edu.li.pdes.micultura.service;

import java.math.BigDecimal;
import java.util.List;

import unq.edu.li.pdes.micultura.dto.PlaceDTO;
import unq.edu.li.pdes.micultura.vo.PlaceVO;
import unq.edu.li.pdes.micultura.vo.ReviewVO;

public interface PlaceService {

	PlaceDTO findById(Long placeId);
	
	void removeFavorite(Long accountId, Long placeId) throws Exception;
	
	PlaceDTO save(PlaceVO placeVO, Long userId, Long placeId);
	
	PlaceDTO update(ReviewVO review, Long userId, Long placeId);
	
	PlaceDTO favorite(PlaceVO plaveVO, Long accountId, Long placeId);
	
	List<PlaceDTO> findAllByUserId(Long userId);
	
	List<PlaceDTO> findAll();
	
	BigDecimal getTotalReviewScore(Long placeId);
}
