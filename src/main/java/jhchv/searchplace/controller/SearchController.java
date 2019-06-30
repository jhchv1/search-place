package jhchv.searchplace.controller;

import jhchv.searchplace.api.search.SearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class SearchController {

    @GetMapping("/search")
    public String doGet(@Valid SearchRequest request, Model model) {
        model.addAttribute("keyword", request.getKeyword());
        model.addAttribute("page", request.getPage());
        return "search-result";
    }

}
