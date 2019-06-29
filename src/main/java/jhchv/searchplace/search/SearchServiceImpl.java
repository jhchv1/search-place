package jhchv.searchplace.search;

import jhchv.searchplace.search.history.SearchHistory;
import jhchv.searchplace.search.history.SearchHistoryRepository;
import jhchv.searchplace.search.history.TotalSearchCountByKeyword;
import jhchv.searchplace.search.history.TotalSearchCountByKeywordRepository;
import jhchv.searchplace.search.response.SearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final SearchHistoryRepository searchHistoryRepository;

    private final TotalSearchCountByKeywordRepository totalSearchCountByKeywordRepository;

    private final RestTemplate restTemplate;

    @Override
    @Transactional
    public SearchResponse search(String keyword, int page) {

        this.writeSearchHistory(keyword);
        this.plusTotalSearchCount(keyword);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK 58eef2e2c12deb84bb4f0911a29f5b61");

        return restTemplate.exchange(
                String.format("https://dapi.kakao.com/v2/local/search/keyword.json?query=%s&page=%d", keyword, page),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                SearchResponse.class).getBody();
    }

    private void writeSearchHistory(String keyword) {
        SearchHistory searchHistory = new SearchHistory();
        searchHistory.setKeyword(keyword);
        searchHistoryRepository.save(searchHistory);
    }

    private void plusTotalSearchCount(String keyword) {
        TotalSearchCountByKeyword totalSearchCountByKeyword =
                totalSearchCountByKeywordRepository.findById(keyword).orElse(new TotalSearchCountByKeyword());
        totalSearchCountByKeyword.setKeyword(keyword);
        totalSearchCountByKeyword.setTotalSearchCount(totalSearchCountByKeyword.getTotalSearchCount() + 1);
        totalSearchCountByKeywordRepository.save(totalSearchCountByKeyword);
    }

}
