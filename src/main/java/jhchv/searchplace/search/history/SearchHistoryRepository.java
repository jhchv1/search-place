package jhchv.searchplace.search.history;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Jihun Cha
 */
public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {

    List<SearchHistory> findByRecordedBy(String recordedBy, Sort sort);

}
