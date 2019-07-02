package jhchv.searchplace.search.rank;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

/**
 * Tests for {@link KeywordRankRepository}.
 *
 * @author Jihun Cha
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class KeywordRankRepositoryTests {

    @Autowired
    private KeywordRankRepository repository;

    private final LocalDateTime now = LocalDateTime.now();

    @Test(expected = DataIntegrityViolationException.class)
    public void 같은일시에_같은순위가저장될수없다() {
        KeywordRank rank1 = new KeywordRank();
        rank1.setKeyword("신대방");
        rank1.setRank(1);
        rank1.setRankedDateTime(now);
        repository.save(rank1);

        KeywordRank rank2 = new KeywordRank();
        rank2.setKeyword("신림");
        rank2.setRank(1);
        rank2.setRankedDateTime(now);
        repository.save(rank2);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void 같은일시에_같은키워드가저장될수없다() {
        KeywordRank rank1 = new KeywordRank();
        rank1.setKeyword("신대방");
        rank1.setRank(1);
        rank1.setRankedDateTime(now);
        repository.save(rank1);

        KeywordRank rank2 = new KeywordRank();
        rank2.setKeyword("신대방");
        rank2.setRank(2);
        rank2.setRankedDateTime(now);
        repository.save(rank2);
    }

}
