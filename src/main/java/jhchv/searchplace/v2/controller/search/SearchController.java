package jhchv.searchplace.v2.controller.search;

import jhchv.searchplace.search.SearchService;
import jhchv.searchplace.search.response.SearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v2/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public SearchResponse doGet(@Valid SearchRequest request) {
        return searchService.search(request.getKeyword(), request.getPage());
    }

}
