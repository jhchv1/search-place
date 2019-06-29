package jhchv.searchplace.search.response;

import lombok.Data;

import java.util.List;

@Data
public class SearchResponse {

    private Meta meta;

    private List<Document> documents;

}
