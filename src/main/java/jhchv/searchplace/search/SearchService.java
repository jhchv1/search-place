package jhchv.searchplace.search;

import jhchv.searchplace.search.response.SearchResponse;

public interface SearchService {

    SearchResponse search(String keyword, int page);

}
