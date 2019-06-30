package jhchv.searchplace.search.rank;

import jhchv.searchplace.search.history.TotalSearchCount;
import jhchv.searchplace.search.history.TotalSearchCountRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class KeywordRankSchedulerTests {

    @Autowired
    private TotalSearchCountRepository totalSearchCountRepository;

    @Autowired
    private KeywordRankRepository keywordRankRepository;

    private KeywordRankScheduler keywordRankScheduler;

    @Before
    public void setup() {
        keywordRankScheduler = new KeywordRankScheduler(totalSearchCountRepository, keywordRankRepository);
    }

    @Test
    public void KeywordRankScheduler는_한번집계시_최상위10개의키워드를_집계한다() {
        List<TotalSearchCount> totalSearchCounts = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            TotalSearchCount totalSearchCount = new TotalSearchCount();
            totalSearchCount.setKeyword("키워드" + i);
            totalSearchCount.setTotalSearchCount((int) ((Math.random() * 100) + 1));
            totalSearchCounts.add(totalSearchCount);
        }
        totalSearchCountRepository.saveAll(totalSearchCounts);
        Assert.assertEquals(50, totalSearchCountRepository.findAll().size());

        keywordRankScheduler.schedule();

        List<KeywordRank> keywordRanks = keywordRankRepository.findRealTimeTopTen();
        Assert.assertEquals(10, keywordRanks.size());

        long totalSearchCount = 100;
        for (int i = 0; i < 10; i++) {
            KeywordRank keywordRank = keywordRanks.get(i);
            Assert.assertEquals(i + 1, keywordRank.getRank());
            Assert.assertTrue(totalSearchCount >= keywordRank.getTotalSearchCount());
            totalSearchCount = keywordRank.getTotalSearchCount();
        }
    }

}
