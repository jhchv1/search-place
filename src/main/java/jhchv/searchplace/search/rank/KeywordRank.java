package jhchv.searchplace.search.rank;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Jihun Cha
 */
@Data
@Entity
@Table(indexes = @Index(columnList = "rankedDateTime"),
       uniqueConstraints = {
           @UniqueConstraint(columnNames = { "keyword", "rankedDateTime" }),
           @UniqueConstraint(columnNames = { "rank", "rankedDateTime" })
       })
public class KeywordRank {

    @Id
    @GeneratedValue
    private Long id;

    private String keyword;

    private int rank;

    private long totalSearchCount;

    private LocalDateTime rankedDateTime;

}
