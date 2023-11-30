package unq.edu.li.pdes.micultura.service;

import java.util.List;

import unq.edu.li.pdes.micultura.dto.AccountReviewDetailsDTO;
import unq.edu.li.pdes.micultura.dto.ReviewDTO;
import unq.edu.li.pdes.micultura.vo.ReviewVO;

public interface ReviewService {

	ReviewDTO findById(Long reviewId);
	
	ReviewDTO update(ReviewVO review, Long reviewId);
	
	ReviewDTO getReviewByPlaceAndAccount(Long placeId, Long accountId);
	
	ReviewDTO getReviewByEventAndAccount(Long eventId, Long accountId);
	
	List<AccountReviewDetailsDTO> findAll();
}
