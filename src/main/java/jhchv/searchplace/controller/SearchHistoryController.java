package jhchv.searchplace.controller;

import jhchv.searchplace.search.history.SearchHistoryRepository;
import jhchv.searchplace.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/search-history")
@RequiredArgsConstructor
public class SearchHistoryController {

    private final SearchHistoryRepository searchHistoryRepository;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public String doGet(@AuthenticationPrincipal User user, Model model) {
        List<SearchHistoryResponse> histories = searchHistoryRepository
                .findByRecordedBy(user, new Sort(Sort.Direction.DESC, "recordedDateTime"))
                .stream().map(SearchHistoryResponse::new).collect(Collectors.toList());

        model.addAttribute("histories", histories);
        return "search-history";
    }

}
