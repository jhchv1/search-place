package jhchv.searchplace.v2.controller.search;

import jhchv.searchplace.search.SearchService;
import jhchv.searchplace.search.response.SearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public SearchResponse doGet(@RequestBody @Valid SearchRequest request) {
        return searchService.search(request.getKeyword(), request.getPage());
    }

}
