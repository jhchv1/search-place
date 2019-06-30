package jhchv.searchplace.search;

public interface SearchService<T extends SearchResponse> {

    T search(String keyword, int page);

}
