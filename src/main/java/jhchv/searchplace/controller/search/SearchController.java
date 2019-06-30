package jhchv.searchplace.controller.search;

import jhchv.searchplace.search.SearchResponse;
import jhchv.searchplace.search.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Jihun Cha
 */
@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public SearchResponse doGet(@Valid SearchRequest request) {
        return searchService.search(request.getKeyword(), request.getPage());
    }

}
