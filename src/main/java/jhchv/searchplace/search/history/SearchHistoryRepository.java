package jhchv.searchplace.search.history;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, String> {
}
