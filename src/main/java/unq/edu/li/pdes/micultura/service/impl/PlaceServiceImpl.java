package unq.edu.li.pdes.micultura.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import unq.edu.li.pdes.micultura.dto.PlaceDTO;
import unq.edu.li.pdes.micultura.exception.MiCulturaException;
import unq.edu.li.pdes.micultura.exception.PlaceNotFoundException;
import unq.edu.li.pdes.micultura.mapper.Mapper;
import unq.edu.li.pdes.micultura.model.AccountInterestPlace;
import unq.edu.li.pdes.micultura.model.AccountReviewPlace;
import unq.edu.li.pdes.micultura.model.Place;
import unq.edu.li.pdes.micultura.model.Review;
import unq.edu.li.pdes.micultura.repository.AccountInterestPlaceRepository;
import unq.edu.li.pdes.micultura.repository.AccountRepository;
import unq.edu.li.pdes.micultura.repository.AccountReviewPlaceRepository;
import unq.edu.li.pdes.micultura.repository.PlaceRepository;
import unq.edu.li.pdes.micultura.repository.ReviewRepository;
import unq.edu.li.pdes.micultura.repository.UserRepository;
import unq.edu.li.pdes.micultura.service.PlaceService;
import unq.edu.li.pdes.micultura.vo.PlaceVO;
import unq.edu.li.pdes.micultura.vo.ReviewVO;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService{

	private final PlaceRepository repository;
	private final UserRepository userRepository;
	private final AccountRepository accountRepository;
	private final AccountInterestPlaceRepository accountInterestPlaceRepository;
	private final AccountReviewPlaceRepository accountReviewPlaceRepository;
	private final ReviewRepository reviewRepository;
	private final Mapper mapper;
	
	@Override
	public PlaceDTO findById(Long placeId) {
		var placeDB = getPlaceById(placeId);
		return mapper.map(placeDB, PlaceDTO.class);
	}

	@Override
	public void removeFavorite(Long accountId, Long placeId) throws Exception {
		var accountDB = accountRepository.findById(accountId).orElseThrow(() -> new MiCulturaException(String.format("No found account:%s", accountId)));
		var placeDB = getPlaceById(placeId);
		var accountInterestPlace = accountInterestPlaceRepository.findByPlaceIdAndAccountId(placeDB.getId(), accountDB.getId());
		accountInterestPlaceRepository.delete(accountInterestPlace);
	}

	@Override
	public PlaceDTO favorite(PlaceVO placeVO, Long accountId, Long placeId) {
		var accountDB = accountRepository.findById(accountId).orElseThrow(() -> new MiCulturaException(String.format("No found account:%s", accountId)));
		var placeOpt = repository.findByPlaceId(placeId);
		Place place = null;
		if(placeOpt.isEmpty()) {
			var aNewEvent = mapper.map(placeVO, Place.class);
			place = repository.save(aNewEvent);
		}else {
			place = placeOpt.get();
		}
		AccountInterestPlace accountInterestPlace = null;
		var accountInterestPlaceOpt = accountInterestPlaceRepository.findOnePlaceIdAndAccountId(place.getId(), accountDB.getId());
		if(accountInterestPlaceOpt.isEmpty()){
			accountInterestPlace = new AccountInterestPlace();
			accountInterestPlace.setAccount(accountDB);
			accountInterestPlace.setPlace(place);
		}else {
			accountInterestPlace = accountInterestPlaceOpt.get();
		}
		accountInterestPlace.setIsFavorite(Boolean.TRUE);
		accountInterestPlaceRepository.save(accountInterestPlace);
		var result = mapper.map(accountInterestPlace.getPlace(), PlaceDTO.class);
		result.setIsFavorite(Boolean.TRUE);
		return result;
	}
	
	@Transactional
	@Override
	public PlaceDTO save(PlaceVO placeVO, Long userId, Long placeId) {
		var userDB = userRepository.findById(userId).orElseThrow(() -> new MiCulturaException(String.format("No found user:%s", userId)));
		var placeIdOpt = repository.findByPlaceId(placeId);
		Place place = null;
		if(placeIdOpt.isEmpty()) {
			var aNewPlace = mapper.map(placeVO, Place.class);
			place = repository.save(aNewPlace);
		}else {
			place = placeIdOpt.get();
		}
		var accountInterestPlaceOpt = accountInterestPlaceRepository.findOnePlaceIdAndAccountId(place.getId(), userDB.getAccount().getId());
		if(accountInterestPlaceOpt.isEmpty()){
			var accountInterestPlace = new AccountInterestPlace();
			accountInterestPlace.setAccount(userDB.getAccount());
			accountInterestPlace.setPlace(place);
			accountInterestPlaceRepository.save(accountInterestPlace);
		}
		return mapper.map(place, PlaceDTO.class);
	}

	@Override
	public PlaceDTO update(ReviewVO review, Long userId, Long placeId) {
		var userDB = userRepository.findById(userId).orElseThrow(() -> new MiCulturaException(String.format("No found user:%s", userId)));
		var placeDB = getPlaceById(placeId);
		var accountReviewPlaceOpt = accountReviewPlaceRepository.findOneByPlaceIdAndAccountId(placeDB.getId(), userDB.getAccount().getId());
		AccountReviewPlace accountReviewPlace = null;
		if(accountReviewPlaceOpt.isEmpty()) {
			accountReviewPlace = new AccountReviewPlace();
			accountReviewPlace.setAccount(userDB.getAccount());
			accountReviewPlace.setPlace(placeDB);
			var reviewMapper = reviewRepository.save(mapper.map(review, Review.class));
			accountReviewPlace.setReview(reviewMapper);
		}else {
			accountReviewPlace = accountReviewPlaceOpt.get();
			accountReviewPlace.getReview().setScore(review.getScore());
			accountReviewPlace.getReview().setCommets(review.getComments());
		}
		accountReviewPlaceRepository.save(accountReviewPlace);
		return mapper.map(placeDB, PlaceDTO.class);
	}
	
	@Override
	public List<PlaceDTO> findAllByUserId(Long userId){
		var userDB = userRepository.findById(userId).orElseThrow(() -> new MiCulturaException(String.format("No found user:%s", userId)));
		return mapper.mapList(accountInterestPlaceRepository.findAllPlacesByAccountId(userDB.getAccount().getId()), PlaceDTO.class);
	}

	@Override
	public List<PlaceDTO> findAll() {
		return mapper.mapList(repository.findAll(), PlaceDTO.class);
	}

	@Override
	public BigDecimal getTotalReviewScore(Long placeId) {
		var totalScore = accountReviewPlaceRepository.getTotalReviewScore(placeId);
		return totalScore == null ? BigDecimal.ZERO : totalScore;
	}
	
	private Place getPlaceById(Long placeId) {
		var placeIdOpt = repository.findByPlaceId(placeId);
		if(placeIdOpt.isEmpty()) {
			throw new PlaceNotFoundException(String.format("Place not found with id:%s ", placeId));
		}
		return placeIdOpt.get();
	}

}
