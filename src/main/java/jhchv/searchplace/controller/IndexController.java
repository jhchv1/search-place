package jhchv.searchplace.controller;

import jhchv.searchplace.search.rank.SearchKeywordRank;
import jhchv.searchplace.search.rank.SearchKeywordRankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class IndexController {

    private final SearchKeywordRankRepository searchKeywordRankRepository;

    @GetMapping
    public String doGet(Model model) {
        List<SearchKeywordRank> searchKeywordRanks = searchKeywordRankRepository.findRealTimeTopTen();
        if (searchKeywordRanks.isEmpty()) {
            model.addAttribute("standardDateTime", LocalDateTime.now().withSecond(0));
            return "index";
        }

        List<RealTimeTopKeyword> realTimeTopKeywords = searchKeywordRanks.stream()
                .map(RealTimeTopKeyword::new).collect(Collectors.toList());

        model.addAttribute("standardDateTime", searchKeywordRanks.get(0).getRankedDateTime());
        model.addAttribute("realTimeTopKeywords", realTimeTopKeywords);
        return "index";
    }

}
