package jhchv.searchplace.search.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Meta {

    private Integer totalCount;

    private Integer pageableCount;

    private Boolean isEnd;

    private SameName sameName;

}
