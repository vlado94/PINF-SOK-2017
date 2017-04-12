package app.codeBookActivities;

import java.util.List;

import app.user.banker.Banker;

public interface CodeBookActivitiesService {

	public List<CodeBookActivities> findAll();

	public void save(CodeBookActivities codeBookActivity);
}
