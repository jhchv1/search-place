package jhchv.searchplace.controller.rank;

import jhchv.searchplace.search.rank.KeywordRank;
import lombok.Data;

/**
 * @author Jihun Cha
 */
@Data
public class KeywordRankDto {

    private int rank;

    private String keyword;

    private long totalSearchCount;

    public KeywordRankDto(KeywordRank rank) {
        this.rank = rank.getRank();
        this.keyword = rank.getKeyword();
        this.totalSearchCount = rank.getTotalSearchCount();
    }

}
