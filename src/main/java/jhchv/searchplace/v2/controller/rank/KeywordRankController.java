package jhchv.searchplace.v2.controller.rank;

import jhchv.searchplace.search.rank.SearchKeywordRank;
import jhchv.searchplace.search.rank.SearchKeywordRankRepository;
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
@RequestMapping("/popular-keywords")
@RequiredArgsConstructor
public class KeywordRankController {

    private final SearchKeywordRankRepository searchKeywordRankRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public KeywordRankResponse doGet() {
        KeywordRankResponse response = new KeywordRankResponse();

        List<SearchKeywordRank> ranks = searchKeywordRankRepository.findRealTimeTopTen();
        if (ranks.isEmpty()) {
            response.setStandardDateTime(LocalDateTime.now().withSecond(0));
            response.setKeywordRanks(new ArrayList<>());
            return response;
        }

        response.setStandardDateTime(ranks.get(0).getRankedDateTime());
        response.setKeywordRanks(ranks.stream().map(KeywordRank::new).collect(Collectors.toList()));
        return response;
    }

}
