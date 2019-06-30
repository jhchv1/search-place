package jhchv.searchplace.search;

/**
 * @author Jihun Cha
 */
public interface SearchService<T extends SearchResponse> {

    T search(String keyword, int page);

}
