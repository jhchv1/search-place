package jhchv.searchplace.kakao;

import jhchv.searchplace.kakao.response.KakaoSearchResponse;
import jhchv.searchplace.search.SearchService;
import jhchv.searchplace.search.history.SearchHistory;
import jhchv.searchplace.search.history.TotalSearchCount;
import jhchv.searchplace.search.history.TotalSearchCountRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

/**
 * Tests for {@link KakaoSearchService}.
 *
 * @author Jihun Cha
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class KakaoSearchServiceTests {

    @Autowired
    private SearchService<KakaoSearchResponse> searchService;

    @Autowired
    private TotalSearchCountRepository totalSearchCountRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @WithMockUser(username = "jhchv")
    public void 장소검색시_검색이력을남기고_해당키워드의검색횟수를저장한다() {
        String keyword = "신대방";
        searchService.search(keyword, 1);

        SearchHistory searchHistory = entityManager
                .createQuery("select a from SearchHistory a where a.keyword = :keyword", SearchHistory.class)
                .setParameter("keyword", keyword)
                .getSingleResult();
        Assert.assertEquals(keyword, searchHistory.getKeyword());
        Assert.assertEquals("jhchv", searchHistory.getRecordedBy());

        TotalSearchCount totalSearchCount = totalSearchCountRepository.findById(keyword).orElseThrow(RuntimeException::new);
        Assert.assertEquals(keyword, totalSearchCount.getKeyword());
        Assert.assertEquals(1L, totalSearchCount.getTotalSearchCount());
    }

}
