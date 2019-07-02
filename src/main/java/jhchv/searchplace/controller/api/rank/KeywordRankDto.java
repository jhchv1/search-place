package jhchv.searchplace.controller.api.rank;

import jhchv.searchplace.search.rank.KeywordRank;
import lombok.Data;

/**
 * @author Jihun Cha
 */
@Data
class KeywordRankDto {

    private int rank;

    private String keyword;

    private long totalSearchCount;

    KeywordRankDto(KeywordRank rank) {
        this.rank = rank.getRank();
        this.keyword = rank.getKeyword();
        this.totalSearchCount = rank.getTotalSearchCount();
    }

}
