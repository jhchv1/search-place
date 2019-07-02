package jhchv.searchplace.controller.token;

import lombok.Data;

/**
 * @author Jihun Cha
 */
@Data
class Token {

    private String type = "Bearer";

    private String accessToken;

    Token(String accessToken) {
        this.accessToken = accessToken;
    }

}
