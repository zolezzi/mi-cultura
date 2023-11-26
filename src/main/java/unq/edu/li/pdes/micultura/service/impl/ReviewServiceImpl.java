package unq.edu.li.pdes.micultura.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import unq.edu.li.pdes.micultura.dto.AccountReviewDetailsDTO;
import unq.edu.li.pdes.micultura.dto.ReviewDTO;
import unq.edu.li.pdes.micultura.exception.MiCulturaException;
import unq.edu.li.pdes.micultura.exception.ReviewNotFoundException;
import unq.edu.li.pdes.micultura.mapper.Mapper;
import unq.edu.li.pdes.micultura.model.Review;
import unq.edu.li.pdes.micultura.repository.AccountReviewEventRepository;
import unq.edu.li.pdes.micultura.repository.AccountReviewPlaceRepository;
import unq.edu.li.pdes.micultura.repository.ReviewRepository;
import unq.edu.li.pdes.micultura.service.ReviewService;
import unq.edu.li.pdes.micultura.vo.ReviewVO;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

	private final ReviewRepository repository;
	private final AccountReviewPlaceRepository accountReviewPlaceRepository;
	private final AccountReviewEventRepository accountReviewEventRepository;
	private final Mapper mapper;
	
	@Override
	public ReviewDTO findById(Long reviewId) {
		var reviewDB = getReviewById(reviewId);
		return mapper.map(reviewDB, ReviewDTO.class);
	}
	
	@Override
	public ReviewDTO update(ReviewVO review, Long reviewId) {
		var reviewDB = getReviewById(reviewId);
		reviewDB.setCommets(review.getComments());
		reviewDB.setScore(review.getScore());
		return mapper.map(repository.save(reviewDB), ReviewDTO.class);
	}
	
	@Override
	public ReviewDTO getReviewByPlaceAndAccount(Long placeId, Long accountId) {
		var accountReviewPlaceOpt = accountReviewPlaceRepository.findOneByPlaceIdAndAccountId(placeId, accountId)
				.orElseThrow(() -> new MiCulturaException(String.format("No found review para la cuenta:%s", accountId)));;
		return mapper.map(accountReviewPlaceOpt.getReview(), ReviewDTO.class);
	}
	
	@Override
	public List<AccountReviewDetailsDTO> findAll(){
		var listPlaces = mapper.mapList(accountReviewPlaceRepository.findAll(), AccountReviewDetailsDTO.class);
		var listEvents = mapper.mapList(accountReviewEventRepository.findAll(), AccountReviewDetailsDTO.class);
		listPlaces.addAll(listEvents);
		return listPlaces;
	}
	
	private Review getReviewById(Long reviewId) {
		var reviewOpt = repository.findById(reviewId);
		if(reviewOpt.isEmpty()) {
			throw new ReviewNotFoundException(String.format("Review not found with id:%s ", reviewId));
		}
		return reviewOpt.get();
	}
}
