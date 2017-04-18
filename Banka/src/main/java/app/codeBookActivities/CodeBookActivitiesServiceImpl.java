package app.codeBookActivities;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CodeBookActivitiesServiceImpl implements CodeBookActivitiesService  {

	private final CodeBookActivitiesRepository codeBookActivitiesRepository;
	
	@Autowired
	public CodeBookActivitiesServiceImpl(final CodeBookActivitiesRepository codeBookActivitiesRepository) {
		this.codeBookActivitiesRepository = codeBookActivitiesRepository;
	}

	@Override
	public List<CodeBookActivities> findAll() {
		return (List<CodeBookActivities>) codeBookActivitiesRepository.findAll();
	}

	@Override
	public void save(CodeBookActivities codeBookActivity) {
		codeBookActivitiesRepository.save(codeBookActivity);
	}

	@Override
	public CodeBookActivities findOne(Long id) {
		return codeBookActivitiesRepository.findOne(id);
	}

	@Override
	public void delete(Long id) {
		codeBookActivitiesRepository.delete(id);
		
	}
}
