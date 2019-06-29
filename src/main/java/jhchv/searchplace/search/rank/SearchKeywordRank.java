package jhchv.searchplace.search.rank;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class SearchKeywordRank {

    @Id
    @GeneratedValue
    private Long id;

    private String keyword;

    private int rank;

    private long totalSearchCount;

    private LocalDateTime rankedDateTime;

}
