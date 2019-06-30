package jhchv.searchplace.v2.controller.rank;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class KeywordRankResponse {

    private LocalDateTime standardDateTime;

    private List<KeywordRank> keywordRanks;

}
