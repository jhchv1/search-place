package jhchv.searchplace.controller;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class SearchRequest {

    @NotNull
    private String keyword;

    @Min(1)
    @Max(45)
    private Integer page = 1;

    @Min(1)
    @Max(15)
    private Integer size = 15;

}
