package lt.kono.blog.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity
@Table(name = "posts")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post extends AbstractAuditableEntity<User, Long> implements Serializable {

    @Column
    @NotEmpty
    private String title;

    @Lob
    @Column
    private String content;

}
