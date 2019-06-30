package jhchv.searchplace.v2.controller.rank;

import jhchv.searchplace.search.rank.SearchKeywordRank;
import lombok.Data;

@Data
public class KeywordRank {

    private int rank;

    private String keyword;

    public KeywordRank(SearchKeywordRank rank) {
        this.rank = rank.getRank();
        this.keyword = rank.getKeyword();
    }

}
