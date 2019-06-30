package jhchv.searchplace.controller.history;

import com.fasterxml.jackson.annotation.JsonFormat;
import jhchv.searchplace.search.history.SearchHistory;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Jihun Cha
 */
@Data
public class SearchHistoryResponse {

    private String keyword;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime recordedDateTime;

    public SearchHistoryResponse(SearchHistory searchHistory) {
        this.keyword = searchHistory.getKeyword();
        this.recordedDateTime = searchHistory.getRecordedDateTime();
    }

}
