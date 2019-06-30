package jhchv.searchplace.kakao;

import jhchv.searchplace.kakao.response.KakaoSearchResponse;
import jhchv.searchplace.search.AbstractSearchService;
import jhchv.searchplace.search.history.SearchHistoryRepository;
import jhchv.searchplace.search.history.TotalSearchCountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Jihun Cha
 */
@Service
@Configuration
@EnableConfigurationProperties(KakaoConfigurationProperties.class)
public class KakaoSearchService extends AbstractSearchService<KakaoSearchResponse> {

    private final KakaoConfigurationProperties properties;

    private final RestTemplate restTemplate;

    @Autowired
    public KakaoSearchService(
            SearchHistoryRepository searchHistoryRepository, TotalSearchCountRepository totalSearchCountRepository,
            KakaoConfigurationProperties properties) {
        super(searchHistoryRepository, totalSearchCountRepository);
        this.properties = properties;
        this.restTemplate = new RestTemplate();
    }

    @Override
    protected KakaoSearchResponse doSearch(String keyword, int page) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + properties.getRestApiKey());

        return restTemplate.exchange(
                String.format("%s?query=%s&page=%d", properties.getSearchPlaceByKeywordUrl(), keyword, page),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                KakaoSearchResponse.class).getBody();
    }

}
