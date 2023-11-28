package unq.edu.li.pdes.micultura.mapper;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import unq.edu.li.pdes.micultura.dto.AccountDTO;
import unq.edu.li.pdes.micultura.dto.AccountReviewDetailsDTO;
import unq.edu.li.pdes.micultura.dto.EventDTO;
import unq.edu.li.pdes.micultura.dto.PlaceDTO;
import unq.edu.li.pdes.micultura.dto.ReviewDTO;
import unq.edu.li.pdes.micultura.dto.UserDTO;
import unq.edu.li.pdes.micultura.model.Account;
import unq.edu.li.pdes.micultura.model.AccountReviewEvent;
import unq.edu.li.pdes.micultura.model.AccountReviewPlace;
import unq.edu.li.pdes.micultura.model.AccountRole;
import unq.edu.li.pdes.micultura.model.Event;
import unq.edu.li.pdes.micultura.model.EventType;
import unq.edu.li.pdes.micultura.model.Place;
import unq.edu.li.pdes.micultura.model.PlaceType;
import unq.edu.li.pdes.micultura.model.Review;
import unq.edu.li.pdes.micultura.model.User;
import unq.edu.li.pdes.micultura.vo.AccountVO;
import unq.edu.li.pdes.micultura.vo.EventVO;
import unq.edu.li.pdes.micultura.vo.PlaceVO;
import unq.edu.li.pdes.micultura.vo.ReviewVO;
import unq.edu.li.pdes.micultura.vo.UserLoginVO;
import unq.edu.li.pdes.micultura.vo.UserVO;

@Component
public class RegisterMapper {

	@Autowired
	private MapperFactory mapperFactory;
	
	@Autowired
	private Mapper mapper;
	
	@PostConstruct
	public void initialize() {

		mapperFactory.classMap(UserVO.class, User.class).customize(new CustomMapper<UserVO, User>() {
			@Override
			public void mapBtoA(User b, UserVO a, MappingContext context) {
				a.setEmail(b.getEmail());
				a.setPassword(b.getPassword());
				//a.setAccount(mapper.map(a.getAccount(), AccountVO.class));
			}
			@Override
			public void mapAtoB(UserVO a, User b, MappingContext context) {
				b.setEmail(a.getEmail());
				b.setPassword(a.getPassword());
				//b.setAccount(mapper.map(a.getAccount(), Account.class));
			}
		}).byDefault().register();
		
		mapperFactory.classMap(UserDTO.class, User.class).customize(new CustomMapper<UserDTO, User>() {
			@Override
			public void mapBtoA(User b, UserDTO a, MappingContext context) {
				a.setId(b.getId());
				a.setEmail(b.getEmail());
				a.setPassword(b.getPassword());
				a.setAccount(mapper.map(a.getAccount(), AccountDTO.class));
			}
			@Override
			public void mapAtoB(UserDTO a, User b, MappingContext context) {
				b.setId(a.getId());
				b.setEmail(a.getEmail());
				b.setPassword(a.getPassword());
				b.setAccount(mapper.map(a.getAccount(), Account.class));
			}
		}).byDefault().register();
		
		mapperFactory.classMap(UserLoginVO.class, User.class).customize(new CustomMapper<UserLoginVO, User>() {
			@Override
			public void mapBtoA(User b, UserLoginVO a, MappingContext context) {
				a.setEmail(b.getEmail());
				a.setPassword(b.getPassword());
			}
			@Override
			public void mapAtoB(UserLoginVO a, User b, MappingContext context) {
				b.setEmail(a.getEmail());
				b.setPassword(a.getPassword());
			}
		}).byDefault().register();
		
		mapperFactory.classMap(AccountVO.class, Account.class).customize(new CustomMapper<AccountVO, Account>() {
			@Override
			public void mapBtoA(Account b, AccountVO a, MappingContext context) {
				a.setFirstname(b.getFirstname());
				a.setLastname(b.getLastname());
				a.setDni(b.getDni());
				a.setRole(b.getAccountRole().name());
			}
			@Override
			public void mapAtoB(AccountVO a, Account b, MappingContext context) {
				b.setFirstname(a.getFirstname());
				b.setLastname(a.getLastname());
				b.setDni(a.getDni());
				b.setAddress(a.getAddress());
				b.setPhoneNumber(a.getPhoneNumber());
				b.setAccountRole(AccountRole.valueOf(a.getRole()));
			}
		}).byDefault().register();
		
		mapperFactory.classMap(AccountDTO.class, Account.class).customize(new CustomMapper<AccountDTO, Account>() {
			@Override
			public void mapBtoA(Account b, AccountDTO a, MappingContext context) {
				a.setId(b.getId());
				a.setFirstname(b.getFirstname());
				a.setLastname(b.getLastname());
				a.setDni(b.getDni());
				a.setRole(b.getAccountRole().name());
				a.setRoleDescripton(b.getAccountRole().getDescription());
			}
			@Override
			public void mapAtoB(AccountDTO a, Account b, MappingContext context) {
				b.setId(a.getId());
				b.setFirstname(a.getFirstname());
				b.setLastname(a.getLastname());
				b.setDni(a.getDni());
				b.setAccountRole(AccountRole.valueOf(a.getRole()));
			}
		}).byDefault().register();
		
		mapperFactory.classMap(PlaceVO.class, Place.class).customize(new CustomMapper<PlaceVO, Place>() {
			@Override
			public void mapBtoA(Place b, PlaceVO a, MappingContext context) {
				a.setAddress(b.getAddress());
				a.setPlaceId(b.getPlaceId());
				a.setDependsOn(b.getDependsOn());
				a.setDescription(b.getDescription());
				a.setEmail(b.getEmail());
				a.setLink(b.getLink());
				a.setName(b.getName());
				a.setPhoneNumber(b.getPhoneNumber());
				a.setPlaceTypeDescription(b.getPlaceType().getDescription());
				a.setProvince(b.getProvince());
				a.setUrl(b.getUrl());
			}
			@Override
			public void mapAtoB(PlaceVO a, Place b, MappingContext context) {
				b.setAddress(a.getAddress());
				b.setPlaceId(a.getPlaceId());
				b.setDependsOn(a.getDependsOn());
				b.setDescription(a.getDescription());
				b.setEmail(a.getEmail());
				b.setLink(a.getLink());
				b.setPhoneNumber(b.getPhoneNumber());
				b.setPlaceType(PlaceType.valueOf(a.getPlaceType()));
				b.setProvince(a.getProvince());
				b.setUrl(a.getUrl());
			}
		}).byDefault().register();
		
		mapperFactory.classMap(PlaceDTO.class, Place.class).customize(new CustomMapper<PlaceDTO, Place>() {
			@Override
			public void mapBtoA(Place b, PlaceDTO a, MappingContext context) {
				a.setId(b.getId());
				a.setAddress(b.getAddress());
				a.setDependsOn(b.getDependsOn());
				a.setEmail(b.getEmail());
				a.setLink(b.getLink());
				a.setName(b.getName());
				a.setPhoneNumber(b.getPhoneNumber());
				a.setPlaceType(a.getPlaceType().toString());
				a.setPlaceTypeDescription(b.getPlaceType().getDescription());
				a.setProvince(b.getProvince());
				a.setUrl(b.getUrl());
			}
			@Override
			public void mapAtoB(PlaceDTO a, Place b, MappingContext context) {
				b.setId(a.getId());
				b.setAddress(a.getAddress());
				b.setDependsOn(a.getDependsOn());
				b.setDescription(a.getDescription());
				b.setEmail(a.getDescription());
				b.setLink(a.getLink());
				b.setName(a.getName());
				b.setPhoneNumber(a.getPhoneNumber());
				b.setPlaceType(PlaceType.valueOf(a.getPlaceType()));
				b.setProvince(a.getProvince());
				b.setUrl(a.getUrl());
			}
		}).byDefault().register();
		
		mapperFactory.classMap(ReviewVO.class, Review.class).customize(new CustomMapper<ReviewVO, Review>() {
			@Override
			public void mapBtoA(Review b, ReviewVO a, MappingContext context) {
				a.setScore(b.getScore());
				a.setComments(b.getCommets());
			}
			@Override
			public void mapAtoB(ReviewVO a, Review b, MappingContext context) {
				b.setScore(a.getScore());
				b.setCommets(a.getComments());
			}
		}).byDefault().register();
		
		mapperFactory.classMap(ReviewDTO.class, Review.class).customize(new CustomMapper<ReviewDTO, Review>() {
			@Override
			public void mapBtoA(Review b, ReviewDTO a, MappingContext context) {
				a.setScore(b.getScore());
				a.setComments(b.getCommets());
			}
			@Override
			public void mapAtoB(ReviewDTO a, Review b, MappingContext context) {
				b.setScore(a.getScore());
				b.setCommets(a.getComments());
			}
		}).byDefault().register();
		
		mapperFactory.classMap(EventVO.class, Event.class).customize(new CustomMapper<EventVO, Event>() {
			@Override
			public void mapBtoA(Event b, EventVO a, MappingContext context) {
				a.setBody(b.getBody());
				a.setEventId(b.getEventId());
				a.setDependsOn(b.getDependsOn());
				a.setFromDate(b.getFromDate());
				a.setEmail(b.getEmail());
				a.setLink(b.getLink());
				a.setState(b.getState());
				a.setSubTitle(b.getSubTitle());
				a.setTitle(b.getTitle());
				a.setToDate(b.getToDate());
				a.setUrl(b.getUrl());
				a.setEventType(b.getEventType().name());
			}
			@Override
			public void mapAtoB(EventVO a, Event b, MappingContext context) {
				b.setBody(a.getBody());
				b.setEventId(a.getEventId());
				b.setDependsOn(a.getDependsOn());
				b.setFromDate(a.getFromDate());
				b.setEmail(a.getEmail());
				b.setLink(a.getLink());
				b.setState(a.getState());
				b.setSubTitle(a.getSubTitle());
				b.setTitle(a.getTitle());
				b.setToDate(a.getToDate());
				b.setEventType(EventType.valueOf(a.getEventType()));
				b.setUrl(a.getUrl());
			}
		}).byDefault().register();
		
		mapperFactory.classMap(EventDTO.class, Event.class).customize(new CustomMapper<EventDTO, Event>() {
			@Override
			public void mapBtoA(Event b, EventDTO a, MappingContext context) {
				a.setId(b.getId());
				a.setBody(b.getBody());
				a.setDependsOn(b.getDependsOn());
				a.setEmail(b.getEmail());
				a.setLink(b.getLink());
				a.setEventId(b.getEventId());
				a.setEventType(b.getEventType().toString());
				a.setEventTypeDescription(b.getEventType().getDescription());
				a.setFromDate(b.getFromDate());
				a.setImage(b.getImage());
				a.setState(b.getState());
				a.setTitle(b.getTitle());
				a.setSubTitle(b.getSubTitle());
				a.setToDate(b.getToDate());
				a.setUrl(b.getUrl());
			}
			@Override
			public void mapAtoB(EventDTO a, Event b, MappingContext context) {
				b.setId(a.getId());
				b.setBody(a.getBody());
				b.setDependsOn(a.getDependsOn());
				b.setEmail(a.getEmail());
				b.setLink(a.getLink());
				b.setEventId(a.getEventId());
				b.setEventType(EventType.valueOf(a.getEventType()));
				b.setFromDate(a.getFromDate());
				b.setImage(a.getImage());
				b.setState(a.getState());
				b.setTitle(a.getTitle());
				b.setSubTitle(a.getSubTitle());
				b.setToDate(a.getToDate());
				b.setUrl(a.getUrl());
			}
		}).byDefault().register();
		
		mapperFactory.classMap(AccountReviewDetailsDTO.class, AccountReviewPlace.class).customize(new CustomMapper<AccountReviewDetailsDTO, AccountReviewPlace>() {
			@Override
			public void mapBtoA(AccountReviewPlace b, AccountReviewDetailsDTO a, MappingContext context) {
				a.setScore(b.getReview().getScore());
				a.setComments(b.getReview().getCommets());
				a.setAccountId(b.getAccount().getId());
				a.setDni(b.getAccount().getDni());
				a.setFirstname(b.getAccount().getFirstname());
				a.setLastname(b.getAccount().getLastname());
				a.setPlaceId(b.getPlace().getPlaceId());
				a.setPlaceName(b.getPlace().getName());
				a.setPlaceType(b.getPlace().getPlaceType().toString());
				a.setPlaceTypeDescription(b.getPlace().getPlaceType().getDescription());
				a.setReviewId(b.getReview().getId());
			}
		}).byDefault().register();
		
		mapperFactory.classMap(AccountReviewDetailsDTO.class, AccountReviewEvent.class).customize(new CustomMapper<AccountReviewDetailsDTO, AccountReviewEvent>() {
			@Override
			public void mapBtoA(AccountReviewEvent b, AccountReviewDetailsDTO a, MappingContext context) {
				a.setScore(b.getReview().getScore());
				a.setComments(b.getReview().getCommets());
				a.setAccountId(b.getAccount().getId());
				a.setDni(b.getAccount().getDni());
				a.setFirstname(b.getAccount().getFirstname());
				a.setLastname(b.getAccount().getLastname());
				a.setReviewId(b.getReview().getId());
				a.setEventId(b.getEvent().getEventId());
				a.setTitle(b.getEvent().getTitle());
				a.setEventType(b.getEvent().getEventType().toString());
				a.setEventTypeDescription(b.getEvent().getEventType().getDescription());
			}

		}).byDefault().register();
	}
}
