package app.codeBookActivities;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CodeBookActivitiesRepository extends PagingAndSortingRepository<CodeBookActivities, Long> {

	CodeBookActivities findByName(String name);
	
	@Query("select c from CodeBookActivities c where c.code like ?1 and c.name like ?2")
	List<CodeBookActivities> findByCodeLikeAndNameLike(Integer code,String name);


}
	