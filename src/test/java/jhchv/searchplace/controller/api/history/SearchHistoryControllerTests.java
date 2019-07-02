package jhchv.searchplace.controller.api.history;

import jhchv.searchplace.config.security.JWTConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for {@link SearchHistoryController}.
 *
 * @author Jihun Cha
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchHistoryControllerTests {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private JWTConfiguration.JWTCreator jwtCreator;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    public void 유효한JWT토큰으로_호출이가능하다() throws Exception {
        MockHttpServletResponse response = mvc
                .perform(
                        get("/api/histories")
                                .header("Authorization", "Bearer " + jwtCreator.create("jhchv")))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        assertEquals(MediaType.APPLICATION_JSON_UTF8_VALUE, response.getContentType());
    }

    @Test
    public void 토큰이없으면_호출이불가능하다() throws Exception {
        mvc.perform(get("/api/histories"))
                .andExpect(status().isUnauthorized());
    }

}
