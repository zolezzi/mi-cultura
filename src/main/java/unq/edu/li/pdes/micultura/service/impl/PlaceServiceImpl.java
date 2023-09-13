package unq.edu.li.pdes.micultura.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import unq.edu.li.pdes.micultura.dto.PlaceDTO;
import unq.edu.li.pdes.micultura.exception.MiCulturaException;
import unq.edu.li.pdes.micultura.exception.PlaceNotFoundException;
import unq.edu.li.pdes.micultura.mapper.Mapper;
import unq.edu.li.pdes.micultura.model.Place;
import unq.edu.li.pdes.micultura.repository.PlaceRepository;
import unq.edu.li.pdes.micultura.repository.UserRepository;
import unq.edu.li.pdes.micultura.service.PlaceService;
import unq.edu.li.pdes.micultura.vo.PlaceVO;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService{

	private final PlaceRepository repository;
	private final UserRepository userRepository;
	private final Mapper mapper;
	
	@Override
	public PlaceDTO findById(Long placeId) {
		var placeDB = getPlaceById(placeId);
		return mapper.map(placeDB, PlaceDTO.class);
	}

	@Override
	public void deleteById(Long userId, Long placeId) throws Exception {
		var userAdmin = userRepository.findById(userId).orElseThrow(() -> new MiCulturaException(String.format("No found user:%s", userId)));
		if(!userAdmin.isAdmin()) {
			throw new MiCulturaException(String.format("Error Unauthorized permission user: %s ", userId));
		}
		var placeDB = getPlaceById(placeId);
		repository.delete(placeDB);
	}

	@Override
	public PlaceDTO save(PlaceVO placeVO) {
		var aNewPlace = mapper.map(placeVO, Place.class);
		aNewPlace = repository.save(aNewPlace);
		return mapper.map(aNewPlace, PlaceDTO.class);
	}

	@Override
	public PlaceDTO update(PlaceDTO place, Long userId, Long placeId) {
		var userAdmin = userRepository.findById(userId).orElseThrow(() -> new MiCulturaException(String.format("No found user:%s", userId)));
		if(!userAdmin.isAdmin()) {
			throw new MiCulturaException(String.format("Error Unauthorized permission user: %s ", userId));
		}
		var placeDB = getPlaceById(placeId);
		placeDB = mapper.map(place, Place.class);
		return mapper.map(repository.save(placeDB), PlaceDTO.class);
	}

	@Override
	public List<PlaceDTO> findAll() {
		return mapper.mapList(repository.findAll(), PlaceDTO.class);
	}

	private Place getPlaceById(Long placeId) {
		var placeIdOpt = repository.findById(placeId);
		if(placeIdOpt.isEmpty()) {
			throw new PlaceNotFoundException(String.format("Place not found with id:%s ", placeId));
		}
		return placeIdOpt.get();
	}
}
