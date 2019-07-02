package jhchv.searchplace.controller.api.history;

import jhchv.searchplace.search.history.SearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jihun Cha
 */
@RestController
@RequestMapping("/api/histories")
@RequiredArgsConstructor
public class HistoryController {

    private final SearchHistoryRepository searchHistoryRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<SearchHistoryResponse> doGet(Authentication auth) {
        return searchHistoryRepository
                .findByRecordedBy(auth.getName(), new Sort(Sort.Direction.DESC, "recordedDateTime"))
                .stream().map(SearchHistoryResponse::new).collect(Collectors.toList());
    }

}
