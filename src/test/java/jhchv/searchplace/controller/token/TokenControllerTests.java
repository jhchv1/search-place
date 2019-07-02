package jhchv.searchplace.controller.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import jhchv.searchplace.config.security.JWTConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for {@link TokenController}.
 *
 * @author Jihun Cha
 */
@RunWith(SpringRunner.class)
@WebMvcTest(TokenController.class)
@Import(JWTConfiguration.class)
public class TokenControllerTests {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(username = "jhchv")
    public void 토큰발급API가_정상적으로작동한다() throws Exception {
        MockHttpServletResponse response = mvc.perform(post("/token"))
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        assertEquals(MediaType.APPLICATION_JSON_UTF8_VALUE, response.getContentType());

        TokenResponse token = new ObjectMapper().readValue(response.getContentAsString(), TokenResponse.class);
        assertEquals("Bearer", token.getType());

        DecodedJWT decodedJWT = JWT.decode(token.getAccessToken());
        assertEquals("jhchv", decodedJWT.getClaim("username").asString());
    }

}
