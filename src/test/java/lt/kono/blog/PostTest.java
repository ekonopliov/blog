package lt.kono.blog;

import lt.kono.blog.domain.Post;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PostTest {

    @Test
    public void testPost(){
        Post post = Post.builder().title("test").build();
        post.setId(1L);
        assertTrue("id is 1L", 1L == post.getId());
        assertTrue("title is test", "test".equals(post.getTitle()));

        Post post2 =  Post.builder().title("test2").build();
        post2.setId(2L);
        assertTrue("id is 2L", 2L == post2.getId());
        assertTrue("title is test2", "test2".equals(post2.getTitle()));
    }
}
