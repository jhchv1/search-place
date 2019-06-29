package jhchv.searchplace.search.rank;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SearchKeywordRankRepository extends JpaRepository<SearchKeywordRank, Long> {

    @Query("select a from SearchKeywordRank a " +
           "where a.rankedDateTime = (select max(b.rankedDateTime) from SearchKeywordRank b) " +
           "order by a.rank")
    List<SearchKeywordRank> findRealTimeTopTen();

}
