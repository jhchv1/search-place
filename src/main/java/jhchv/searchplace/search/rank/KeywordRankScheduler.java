package jhchv.searchplace.search.rank;

import jhchv.searchplace.search.history.TotalSearchCount;
import jhchv.searchplace.search.history.TotalSearchCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jihun Cha
 */
@Component
@RequiredArgsConstructor
public class KeywordRankScheduler {

    private final TotalSearchCountRepository totalSearchCountRepository;

    private final KeywordRankRepository keywordRankRepository;

    @Scheduled(cron = "0 * * * * *")
    public void schedule() {
        LocalDateTime now = LocalDateTime.now();

        Page<TotalSearchCount> page = totalSearchCountRepository.findAll(
                PageRequest.of(0, 10, new Sort(Sort.Direction.DESC, "totalSearchCount")));

        int rank = 0;
        List<KeywordRank> keywordRanks = new ArrayList<>();
        for (TotalSearchCount totalSearchCount : page.getContent()) {
            KeywordRank keywordRank = new KeywordRank();
            keywordRank.setKeyword(totalSearchCount.getKeyword());
            keywordRank.setRank(++rank);
            keywordRank.setTotalSearchCount(totalSearchCount.getTotalSearchCount());
            keywordRank.setRankedDateTime(now);
            keywordRanks.add(keywordRank);
        }
        keywordRankRepository.saveAll(keywordRanks);
    }

}
