package app.codeBookActivities;

import java.util.List;

public interface CodeBookActivitiesService {

	List<CodeBookActivities> findAll();

	CodeBookActivities findOne(Long id);

	CodeBookActivities save(CodeBookActivities codeBookActivity);

	void delete(Long id);

	CodeBookActivities findByName(String name);
	
	List<CodeBookActivities> findByCodeLikeAndNameLike(Integer code,String name);
	
}
