package jhchv.searchplace.controller.api.search;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Jihun Cha
 */
@Data
class SearchRequest {

    @NotNull
    private String keyword;

    @Min(1)
    @Max(45)
    private int page = 1;

}
