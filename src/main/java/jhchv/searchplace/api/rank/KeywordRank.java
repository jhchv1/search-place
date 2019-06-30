package jhchv.searchplace.api.rank;

import jhchv.searchplace.search.rank.SearchKeywordRank;
import lombok.Data;

@Data
public class KeywordRank {

    private int rank;

    private String keyword;

    private long totalSearchCount;

    public KeywordRank(SearchKeywordRank rank) {
        this.rank = rank.getRank();
        this.keyword = rank.getKeyword();
        this.totalSearchCount = rank.getTotalSearchCount();
    }

}
