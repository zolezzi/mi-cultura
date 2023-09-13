package unq.edu.li.pdes.micultura.service;

import java.util.List;

import unq.edu.li.pdes.micultura.dto.PlaceDTO;
import unq.edu.li.pdes.micultura.vo.PlaceVO;

public interface PlaceService {

	PlaceDTO findById(Long placeId);
	
	void deleteById(Long userId, Long placeId) throws Exception;
	
	PlaceDTO save(PlaceVO placeVO);
	
	PlaceDTO update(PlaceDTO place, Long userId, Long placeId);
	
	List<PlaceDTO> findAll();
}
