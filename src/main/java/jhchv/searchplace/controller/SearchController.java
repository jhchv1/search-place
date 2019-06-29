package jhchv.searchplace.controller;

import jhchv.searchplace.search.SearchService;
import jhchv.searchplace.search.response.SearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/search")
    public String doGet(@Valid SearchRequest request, Model model) {
        SearchResponse searchResponse = searchService.search(request.getKeyword(), request.getPage());

        model.addAttribute("request", request);
        model.addAttribute("documents", searchResponse.getDocuments());
        model.addAttribute("lastPage", searchResponse.getMeta().getIsEnd());

        return "search-result";
    }

    @GetMapping("/search/ajax")
    @ResponseBody
    public SearchResponse doGetAjax(@Valid SearchRequest request) {
        return searchService.search(request.getKeyword(), request.getPage());
    }

}
