package jhchv.searchplace.search.history;

import jhchv.searchplace.user.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {

    List<SearchHistory> findByRecordedBy(User user, Sort sort);

}
