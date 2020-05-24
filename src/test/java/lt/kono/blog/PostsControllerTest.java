package lt.kono.blog;

import lt.kono.blog.domain.Post;
import lt.kono.blog.web.PostsController;
import lt.kono.blog.web.PostForm;
import lt.kono.blog.repository.PostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PostsController.class, secure = false)
@RunWith(SpringRunner.class)
public class PostsControllerTest {

    @MockBean
    PostRepository posts;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Before
    public void setUp() {
        given(posts.findById(1L))
                .willReturn(Optional.of(Post.builder().title("test").build()));

        given(posts.findById(2L))
                .willReturn(Optional.empty());

        given(posts.save(any(Post.class)))
                .willReturn(Post.builder().title("test").build());

        doNothing().when(posts).delete(any(Post.class));
    }

    @Test
    public void testGetById() throws Exception {

        mockMvc
                .perform(
                        get("/v1/posts/{id}", 1L)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("test"));

        verify(posts, times(1)).findById(any(Long.class));
        verifyNoMoreInteractions(posts);
    }

    @Test
    public void testGetByIdNotFound() throws Exception {

        mockMvc
                .perform(
                        get("/v1/posts/{id}", 2L)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());

        verify(posts, times(1)).findById(any(Long.class));
        verifyNoMoreInteractions(posts);
    }

    @Test
    public void testSave() throws Exception {

        mockMvc
                .perform(
                        post("/v1/posts")
                                .content(objectMapper.writeValueAsBytes(PostForm.builder().title("test").content("test1").build()))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated());

        verify(posts, times(1)).save(any(Post.class));
        verifyNoMoreInteractions(posts);
    }

    @Test
    public void testUpdate() throws Exception {

        mockMvc
                .perform(
                        put("/v1/posts/1")
                                .content(objectMapper.writeValueAsBytes(PostForm.builder().title("test").content("test1").build()))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());

        verify(posts, times(1)).findById(any(Long.class));
        verify(posts, times(1)).save(any(Post.class));
        verifyNoMoreInteractions(posts);
    }

    @Test
    public void testDelete() throws Exception {

        mockMvc
                .perform(
                        delete("/v1/posts/1")
                )
                .andExpect(status().isNoContent());

        verify(posts, times(1)).findById(any(Long.class));
        verify(posts, times(1)).delete(any(Post.class));
        verifyNoMoreInteractions(posts);
    }

}
