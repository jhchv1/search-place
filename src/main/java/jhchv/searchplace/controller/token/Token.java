package jhchv.searchplace.controller.token;

import lombok.Data;

@Data
public class Token {

    private String type = "Bearer";

    private String accessToken;

    public Token(String accessToken) {
        this.accessToken = accessToken;
    }

}
