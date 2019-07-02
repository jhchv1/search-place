package jhchv.searchplace.search.history;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * @author Jihun Cha
 */
@Data
@Entity
@Table(indexes = @Index(columnList = "totalSearchCount"))
public class TotalSearchCount {

    @Id
    private String keyword;

    private long totalSearchCount;

}
