package jhchv.searchplace.kakao.response;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

/**
 * @author Jihun Cha
 */
@Data
class Meta {

    @JsonSetter("total_count")
    private Integer totalCount;

    @JsonSetter("pageable_count")
    private Integer pageableCount;

    @JsonSetter("is_end")
    private Boolean isEnd;

    @JsonSetter("same_name")
    private SameName sameName;

}
