package app.codeBookActivities;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface CodeBookActivitiesRepository extends PagingAndSortingRepository<CodeBookActivities, Long> {

	CodeBookActivities findByName(String name);
	
	List<CodeBookActivities> findByCodeLikeOrNameLike(Integer code,String name);


}
	