package jhchv.searchplace.user;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

/**
 * Tests for {@link UserService}.
 *
 * @author Jihun Cha
 */
@RunWith(SpringRunner.class)
public class UserServiceTests {

    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setup() {
        userService = new UserService(userRepository);
    }

    @Test
    public void 사용자아이디가존재하면_해당사용자를정상반환한다() {
        String username = "jhchv";
        User user = new User();
        user.setUsername(username);
        user.setPassword("temp");
        given(userRepository.findById(username)).willReturn(Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername(username);
        Assert.assertEquals(username, userDetails.getUsername());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void 사용자아이디가존재하지않으면_예외를발생시킨다() {
        String username = "jhchv";
        given(userRepository.findById(username)).willReturn(Optional.empty());

        userService.loadUserByUsername(username);
    }

}
