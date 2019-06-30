package jhchv.searchplace.search.rank;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "keyword", "rank", "rankedDateTime" }))
public class KeywordRank {

    @Id
    @GeneratedValue
    private Long id;

    private String keyword;

    private int rank;

    private long totalSearchCount;

    private LocalDateTime rankedDateTime;

}
