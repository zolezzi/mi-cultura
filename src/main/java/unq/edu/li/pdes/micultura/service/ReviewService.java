package unq.edu.li.pdes.micultura.service;

import unq.edu.li.pdes.micultura.dto.ReviewDTO;
import unq.edu.li.pdes.micultura.vo.ReviewVO;

public interface ReviewService {

	ReviewDTO findById(Long reviewId);
	
	ReviewDTO update(ReviewVO review, Long reviewId);
	
	ReviewDTO getReviewByPlaceAndAccount(Long placeId, Long accountId);
}
