package jhchv.searchplace.kakao.response;

import jhchv.searchplace.search.SearchResponse;
import lombok.Data;

import java.util.List;

@Data
public class KakaoSearchResponse implements SearchResponse {

    private Meta meta;

    private List<Document> documents;

}
