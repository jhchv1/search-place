package jhchv.searchplace.controller;

import jhchv.searchplace.search.history.SearchHistory;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SearchHistoryResponse {

    private String keyword;

    private LocalDateTime recordedDateTime;

    public SearchHistoryResponse(SearchHistory searchHistory) {
        this.keyword = searchHistory.getKeyword();
        this.recordedDateTime = searchHistory.getRecordedDateTime();
    }

}
