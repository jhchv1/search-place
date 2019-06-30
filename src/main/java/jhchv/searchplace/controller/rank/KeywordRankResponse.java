package jhchv.searchplace.controller.rank;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class KeywordRankResponse {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime standardDateTime;

    private List<KeywordRankDto> keywordRanks;

}
