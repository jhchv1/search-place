package jhchv.searchplace.api.rank;

import jhchv.searchplace.search.rank.KeywordRank;
import jhchv.searchplace.search.rank.KeywordRankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/keyword-ranks")
@RequiredArgsConstructor
public class KeywordRankController {

    private final KeywordRankRepository keywordRankRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public KeywordRankResponse doGet() {
        KeywordRankResponse response = new KeywordRankResponse();

        List<KeywordRank> ranks = keywordRankRepository.findRealTimeTopTen();
        if (ranks.isEmpty()) {
            response.setStandardDateTime(LocalDateTime.now().withSecond(0));
            response.setKeywordRanks(new ArrayList<>());
            return response;
        }

        response.setStandardDateTime(ranks.get(0).getRankedDateTime());
        response.setKeywordRanks(ranks.stream().map(KeywordRankDto::new).collect(Collectors.toList()));
        return response;
    }

}
