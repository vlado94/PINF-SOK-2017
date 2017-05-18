package app.populatedPlace;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PopulatedPlaceServiceImpl implements PopulatedPlaceService {

	private final PopulatedPlaceRepository populatedPlaceRepository;
	
	@Autowired
	public PopulatedPlaceServiceImpl(final PopulatedPlaceRepository populatedPlaceRepository) {
		this.populatedPlaceRepository = populatedPlaceRepository;
	}

	@Override
	public List<PopulatedPlace> findAll() {
		return (List<PopulatedPlace>) populatedPlaceRepository.findAll();
	}

	@Override
	public PopulatedPlace save(PopulatedPlace populatedPlace) {
		return populatedPlaceRepository.save(populatedPlace);
		
	}

	@Override
	public void delete(Long id) {
		populatedPlaceRepository.delete(id);
		
	}

	@Override
	public PopulatedPlace findOne(Long id) {
		return populatedPlaceRepository.findOne(id);
	}

	@Override
	public List<PopulatedPlace> findByNameLikeOrPttCodeLikeOrCountry_NameLike(String name,String pttCode,String country) {
		return populatedPlaceRepository.findByNameLikeOrPttCodeLikeOrCountry_NameLike(name,pttCode,country);
	}

}
