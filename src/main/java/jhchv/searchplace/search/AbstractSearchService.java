package jhchv.searchplace.search;

import jhchv.searchplace.search.history.SearchHistory;
import jhchv.searchplace.search.history.SearchHistoryRepository;
import jhchv.searchplace.search.history.TotalSearchCount;
import jhchv.searchplace.search.history.TotalSearchCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Jihun Cha
 */
@RequiredArgsConstructor
public abstract class AbstractSearchService<T extends SearchResponse> implements SearchService<T> {

    private final SearchHistoryRepository searchHistoryRepository;

    private final TotalSearchCountRepository totalSearchCountRepository;

    @Override
    @Transactional
    public T search(String keyword, int page) {
        this.writeSearchHistory(keyword);
        this.plusTotalSearchCount(keyword);
        return doSearch(keyword, page);
    }

    protected abstract T doSearch(String keyword, int page);

    private void writeSearchHistory(String keyword) {
        SearchHistory searchHistory = new SearchHistory();
        searchHistory.setKeyword(keyword);
        searchHistoryRepository.save(searchHistory);
    }

    private void plusTotalSearchCount(String keyword) {
        TotalSearchCount totalSearchCount =
                totalSearchCountRepository.findById(keyword).orElse(new TotalSearchCount());
        totalSearchCount.setKeyword(keyword);
        totalSearchCount.setTotalSearchCount(totalSearchCount.getTotalSearchCount() + 1);
        totalSearchCountRepository.save(totalSearchCount);
    }

}
