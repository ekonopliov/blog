package lt.kono.blog;

import lt.kono.blog.domain.Post;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PostJpaTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void mapping() {
        Post post = testEntityManager.persistFlushFind(Post.builder().title("test").build());
        assertThat(post.getTitle()).isEqualTo("test");
        assertThat(post.getId()).isNotNull();
        assertThat(post.getId()).isGreaterThan(0);
    }
}
