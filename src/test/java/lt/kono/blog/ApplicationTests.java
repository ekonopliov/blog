package lt.kono.blog;

import lt.kono.blog.web.PostForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext applicationContext;

    @Autowired
    ObjectMapper objectMapper;

    @Before
    public void setup() {
        mockMvc = webAppContextSetup(applicationContext)
            .apply(springSecurity())
            .build();
    }

    @Test
    public void getAllPosts() throws Exception {
        mockMvc
            .perform(
                get("/v1/posts")
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }

    @Test
    public void testSave() throws Exception {

        mockMvc
            .perform(
                post("/v1/posts")
                    .content(objectMapper.writeValueAsBytes(PostForm.builder().title("test").build()))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().is4xxClientError());

    }

    @Test
    @WithUserDetails()
    public void testSaveWithMock() throws Exception {

        mockMvc
            .perform(
                post("/v1/posts")
                    .content(objectMapper.writeValueAsBytes(PostForm.builder().title("test").build()))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isCreated());
    }

}
