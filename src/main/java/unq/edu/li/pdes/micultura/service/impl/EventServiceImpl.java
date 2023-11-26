package unq.edu.li.pdes.micultura.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import unq.edu.li.pdes.micultura.dto.EventDTO;
import unq.edu.li.pdes.micultura.exception.MiCulturaException;
import unq.edu.li.pdes.micultura.exception.PlaceNotFoundException;
import unq.edu.li.pdes.micultura.mapper.Mapper;
import unq.edu.li.pdes.micultura.model.AccountInterestEvent;
import unq.edu.li.pdes.micultura.model.AccountReviewEvent;
import unq.edu.li.pdes.micultura.model.Event;
import unq.edu.li.pdes.micultura.model.Review;
import unq.edu.li.pdes.micultura.repository.AccountInterestEventRepository;
import unq.edu.li.pdes.micultura.repository.AccountRepository;
import unq.edu.li.pdes.micultura.repository.AccountReviewEventRepository;
import unq.edu.li.pdes.micultura.repository.EventRepository;
import unq.edu.li.pdes.micultura.repository.ReviewRepository;
import unq.edu.li.pdes.micultura.repository.UserRepository;
import unq.edu.li.pdes.micultura.service.EventService;
import unq.edu.li.pdes.micultura.vo.EventVO;
import unq.edu.li.pdes.micultura.vo.ReviewVO;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService{

	private final EventRepository repository;
	private final UserRepository userRepository;
	private final AccountRepository accountRepository;
	private final AccountInterestEventRepository accountInterestEventRepository;
	private final AccountReviewEventRepository accountReviewEventRepository;
	private final ReviewRepository reviewRepository;
	private final Mapper mapper;
	
	@Override
	public EventDTO findById(Long eventId) {
		var eventDB = getEventById(eventId);
		return mapper.map(eventDB, EventDTO.class);
	}

	@Override
	public void removeFavorite(Long accountId, Long eventId) throws Exception {
		var accountDB = accountRepository.findById(accountId).orElseThrow(() -> new MiCulturaException(String.format("No found account:%s", accountId)));
		var eventDB = getEventById(eventId);
		var accountInterestEvent = accountInterestEventRepository.findByEventIdAndAccountId(eventDB.getId(), accountDB.getId());
		accountInterestEventRepository.delete(accountInterestEvent);
	}

	@Transactional
	@Override
	public EventDTO save(EventVO eventVO, Long userId, Long eventId) {
		var userDB = userRepository.findById(userId).orElseThrow(() -> new MiCulturaException(String.format("No found user:%s", userId)));
		var eventOpt = repository.findByEventId(eventId);
		Event event = null;
		if(eventOpt.isEmpty()) {
			var aNewEvent = mapper.map(eventVO, Event.class);
			event = repository.save(aNewEvent);
		}else {
			event = eventOpt.get();
		}
		var accountInterestEventOpt = accountInterestEventRepository.findOneEventIdAndAccountId(event.getId(), userDB.getAccount().getId());
		if(accountInterestEventOpt.isEmpty()){
			var accountInterestEvent = new AccountInterestEvent();
			accountInterestEvent.setAccount(userDB.getAccount());
			accountInterestEvent.setEvent(event);
			accountInterestEventRepository.save(accountInterestEvent);
		}
		return mapper.map(event, EventDTO.class);
	}

	@Override
	public EventDTO update(ReviewVO review, Long userId, Long eventId) {
		var userDB = userRepository.findById(userId).orElseThrow(() -> new MiCulturaException(String.format("No found user:%s", userId)));
		var eventDB = getEventById(eventId);
		var accountReviewEventOpt = accountReviewEventRepository.findOneByEventIdAndAccountId(eventDB.getId(), userDB.getAccount().getId());
		AccountReviewEvent accountReviewEvent = null;
		if(accountReviewEventOpt.isEmpty()) {
			accountReviewEvent = new AccountReviewEvent();
			accountReviewEvent.setAccount(userDB.getAccount());
			accountReviewEvent.setEvent(eventDB);
			var reviewMapper = reviewRepository.save(mapper.map(review, Review.class));
			accountReviewEvent.setReview(reviewMapper);
		}else {
			accountReviewEvent = accountReviewEventOpt.get();
			accountReviewEvent.getReview().setScore(review.getScore());
			accountReviewEvent.getReview().setCommets(review.getComments());
		}
		accountReviewEventRepository.save(accountReviewEvent);
		return mapper.map(eventDB, EventDTO.class);
	}

	@Override
	public EventDTO favorite(Long accountId, Long eventId) {
		var accountDB = accountRepository.findById(accountId).orElseThrow(() -> new MiCulturaException(String.format("No found account:%s", accountId)));
		var eventDB = getEventById(eventId);
		var accountInterestEvent = accountInterestEventRepository.findByEventIdAndAccountId(eventDB.getId(), accountDB.getId());
		accountInterestEvent.setIsFavorite(Boolean.TRUE);
		accountInterestEventRepository.save(accountInterestEvent);
		var result = mapper.map(accountInterestEvent.getEvent(), EventDTO.class);
		result.setIsFavorite(Boolean.TRUE);
		return result;
	}

	@Override
	public List<EventDTO> findAllByUserId(Long userId) {
		var userDB = userRepository.findById(userId).orElseThrow(() -> new MiCulturaException(String.format("No found user:%s", userId)));
		return mapper.mapList(accountInterestEventRepository.findAllEventsByAccountId(userDB.getAccount().getId()), EventDTO.class);
	}

	@Override
	public List<EventDTO> findAll() {
		return mapper.mapList(repository.findAll(), EventDTO.class);
	}
	
	@Override
	public BigDecimal getTotalReviewScore(Long eventId) {
		var totalScore = accountReviewEventRepository.getTotalReviewScore(eventId);
		return totalScore == null ? BigDecimal.ZERO : totalScore;
	}
	
	private Event getEventById(Long eventId) {
		var eventIdOpt = repository.findByEventId(eventId);
		if(eventIdOpt.isEmpty()) {
			throw new PlaceNotFoundException(String.format("Event not found with id:%s ", eventId));
		}
		return eventIdOpt.get();
	}

}
