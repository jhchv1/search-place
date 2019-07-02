package jhchv.searchplace.user;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Jihun Cha
 */
@Data
@Entity
class User {

    @Id
    private String username;

    private String password;

}
