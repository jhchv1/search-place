package jhchv.searchplace.api.history;

import jhchv.searchplace.search.history.SearchHistoryRepository;
import jhchv.searchplace.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/histories")
@RequiredArgsConstructor
public class HistoryController {

    private final SearchHistoryRepository searchHistoryRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize("isAuthenticated()")
    public List<SearchHistoryResponse> doGet(@AuthenticationPrincipal User user) {
        return searchHistoryRepository
                .findByRecordedBy(user,new Sort(Sort.Direction.DESC, "recordedDateTime"))
                .stream().map(SearchHistoryResponse::new).collect(Collectors.toList());
    }

}
