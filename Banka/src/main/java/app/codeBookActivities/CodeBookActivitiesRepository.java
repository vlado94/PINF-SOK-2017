package app.codeBookActivities;

import org.springframework.data.repository.PagingAndSortingRepository;

import app.user.banker.Banker;

public interface CodeBookActivitiesRepository extends PagingAndSortingRepository<CodeBookActivities, Long> {

}
