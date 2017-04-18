package app.codeBookActivities;

import java.util.List;

public interface CodeBookActivitiesService {

	List<CodeBookActivities> findAll();

	CodeBookActivities findOne(Long id);

	void save(CodeBookActivities codeBookActivity);

	void delete(Long id);
	
	
}
