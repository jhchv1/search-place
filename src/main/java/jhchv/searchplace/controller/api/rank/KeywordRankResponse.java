package jhchv.searchplace.controller.api.rank;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Jihun Cha
 */
@Data
class KeywordRankResponse {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime standardDateTime;

    private List<KeywordRankDto> keywordRanks;

}
