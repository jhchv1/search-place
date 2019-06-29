package jhchv.searchplace.controller;

import jhchv.searchplace.search.rank.SearchKeywordRank;
import lombok.Data;

@Data
public class RealTimeTopKeyword {

    private int rank;

    private String keyword;

    private long totalSearchCount;

    public RealTimeTopKeyword(SearchKeywordRank rank) {
        this.rank = rank.getRank();
        this.keyword = rank.getKeyword();
        this.totalSearchCount = rank.getTotalSearchCount();
    }

}
