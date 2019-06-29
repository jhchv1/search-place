package jhchv.searchplace.search.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Document {

    private String id;

    private String placeName;

    private String categoryName;

    private String categoryGroupCode;

    private String categoryGroupName;

    private String phone;

    private String addressName;

    private String roadAddressName;

    private String x;

    private String y;

    private String placeUrl;

    private String distance;

}
