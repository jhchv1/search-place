package jhchv.searchplace.kakao.response;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

@Data
public class Document {

    private String id;

    @JsonSetter("place_name")
    private String placeName;

    @JsonSetter("category_name")
    private String categoryName;

    @JsonSetter("category_group_code")
    private String categoryGroupCode;

    @JsonSetter("category_group_name")
    private String categoryGroupName;

    private String phone;

    @JsonSetter("address_name")
    private String addressName;

    @JsonSetter("road_address_name")
    private String roadAddressName;

    private String x;

    private String y;

    @JsonSetter("place_url")
    private String placeUrl;

    private String distance;

}
