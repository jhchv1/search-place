package jhchv.searchplace.search.history;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class TotalSearchCount {

    @Id
    private String keyword;

    private long totalSearchCount;

}
