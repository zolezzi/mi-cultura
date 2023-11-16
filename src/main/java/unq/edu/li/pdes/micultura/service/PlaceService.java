package unq.edu.li.pdes.micultura.service;

import java.util.List;

import unq.edu.li.pdes.micultura.dto.PlaceDTO;
import unq.edu.li.pdes.micultura.vo.PlaceVO;
import unq.edu.li.pdes.micultura.vo.ReviewVO;

public interface PlaceService {

	PlaceDTO findById(Long placeId);
	
	void removeFavorite(Long accountId, Long placeId) throws Exception;
	
	PlaceDTO save(PlaceVO placeVO, Long userId, Long placeId);
	
	PlaceDTO update(ReviewVO review, Long userId, Long placeId);
	
	List<PlaceDTO> findAllByUserId(Long userId);
	
	List<PlaceDTO> findAll();
}
