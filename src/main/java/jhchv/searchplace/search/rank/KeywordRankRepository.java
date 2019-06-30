package jhchv.searchplace.search.rank;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KeywordRankRepository extends JpaRepository<KeywordRank, Long> {

    @Query("select a from KeywordRank a " +
           "where a.rankedDateTime = (select max(b.rankedDateTime) from KeywordRank b) " +
           "order by a.rank")
    List<KeywordRank> findRealTimeTopTen();

}
