package jhchv.searchplace.search.history;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Jihun Cha
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(indexes = @Index(columnList = "recordedBy"))
public class SearchHistory {

    @Id
    @GeneratedValue
    private Long id;

    private String keyword;

    @CreatedBy
    private String recordedBy;

    @CreatedDate
    private LocalDateTime recordedDateTime;

}
