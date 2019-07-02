package jhchv.searchplace.controller.token;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jihun Cha
 */
@Data
@NoArgsConstructor
class TokenResponse {

    private String type = "Bearer";

    private String accessToken;

    TokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }

}
