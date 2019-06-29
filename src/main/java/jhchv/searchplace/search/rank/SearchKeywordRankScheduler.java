package jhchv.searchplace.search.rank;

import jhchv.searchplace.search.history.TotalSearchCountByKeyword;
import jhchv.searchplace.search.history.TotalSearchCountByKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SearchKeywordRankScheduler {

    private final TotalSearchCountByKeywordRepository totalSearchCountByKeywordRepository;

    private final SearchKeywordRankRepository searchKeywordRankRepository;

    @Scheduled(cron = "0 * * * * *")
    public void schedule() {
        LocalDateTime now = LocalDateTime.now();

        Page<TotalSearchCountByKeyword> page = totalSearchCountByKeywordRepository.findAll(
                PageRequest.of(0, 10, new Sort(Sort.Direction.DESC, "totalSearchCount")));

        int rank = 0;
        List<SearchKeywordRank> searchKeywordRanks = new ArrayList<>();
        for (TotalSearchCountByKeyword totalSearchCountByKeyword : page.getContent()) {
            SearchKeywordRank searchKeywordRank = new SearchKeywordRank();
            searchKeywordRank.setKeyword(totalSearchCountByKeyword.getKeyword());
            searchKeywordRank.setRank(++rank);
            searchKeywordRank.setTotalSearchCount(totalSearchCountByKeyword.getTotalSearchCount());
            searchKeywordRank.setRankedDateTime(now);
            searchKeywordRanks.add(searchKeywordRank);
        }
        searchKeywordRankRepository.saveAll(searchKeywordRanks);
    }

}
