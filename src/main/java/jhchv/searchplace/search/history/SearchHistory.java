package jhchv.searchplace.search.history;

import jhchv.searchplace.user.User;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class SearchHistory {

    @Id
    @GeneratedValue
    private Long id;

    private String keyword;

    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "recordedBy")
    private User recordedBy;

    @CreatedDate
    private LocalDateTime recordedDateTime;

}
