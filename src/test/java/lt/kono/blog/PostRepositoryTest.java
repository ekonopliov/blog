package lt.kono.blog;

import lt.kono.blog.domain.Post;
import lt.kono.blog.repository.PostRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    private PostRepository posts;

    @Test
    public void mapping() {
        Post saved = posts.save(Post.builder().title("test").build());
        Post post = posts.getOne(saved.getId());
        assertThat(post.getTitle()).isEqualTo("test");
        assertThat(post.getId()).isNotNull();
        assertThat(post.getId()).isGreaterThan(0);
    }
}
