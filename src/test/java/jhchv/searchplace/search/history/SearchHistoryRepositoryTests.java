package jhchv.searchplace.search.history;

import jhchv.searchplace.config.JpaAuditingConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests for {@link SearchHistoryRepository}.
 *
 * @author Jihun Cha
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@Import(JpaAuditingConfiguration.class)
public class SearchHistoryRepositoryTests {

    @Autowired
    private SearchHistoryRepository searchHistoryRepository;

    @Test
    @WithMockUser(username = "jhchv")
    public void EnableJpaAuditing설정에의해_키워드만입력해도_사용자와일시정보가저장된다() {
        List<SearchHistory> searchHistories = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            SearchHistory searchHistory = new SearchHistory();
            searchHistory.setKeyword("키워드" + i);
            searchHistories.add(searchHistory);
        }
        searchHistoryRepository.saveAll(searchHistories);

        Assert.assertFalse(searchHistoryRepository.findAll().stream().anyMatch(history ->
                history.getId() == null || history.getRecordedBy() == null || history.getRecordedDateTime() == null));
    }

}
