package jhchv.searchplace.kakao.response;

import jhchv.searchplace.search.SearchResponse;
import lombok.Data;

import java.util.List;

/**
 * @author Jihun Cha
 */
@Data
public class KakaoSearchResponse implements SearchResponse {

    private Meta meta;

    private List<Document> documents;

}
