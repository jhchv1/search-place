package jhchv.searchplace.kakao.response;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

import java.util.List;

/**
 * @author Jihun Cha
 */
@Data
class SameName {

    private List<String> region;

    private String keyword;

    @JsonSetter("selected_region")
    private String selectedRegion;

}
