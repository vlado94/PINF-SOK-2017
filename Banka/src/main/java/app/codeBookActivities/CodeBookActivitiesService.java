package app.codeBookActivities;

import java.util.List;

public interface CodeBookActivitiesService {

	public List<CodeBookActivities> findAll();

	public CodeBookActivities findOne(Long id);

	public void save(CodeBookActivities codeBookActivity);
}
