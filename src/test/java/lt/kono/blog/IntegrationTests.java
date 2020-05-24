package lt.kono.blog;

import lt.kono.blog.web.AuthenticationRequest;
import lt.kono.blog.web.PostForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Slf4j
public class IntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    ObjectMapper objectMapper;

    private String token;

    @Before
    public void setup() {
        RestAssured.port = port;
        token = given()
            .contentType(ContentType.JSON)
            .body(AuthenticationRequest.builder().username("user").password("password").build())
            .when().post("/authenticate/token")
            .andReturn().jsonPath().getString("token");
        log.debug("Got token:" + token);
    }

    @Test
    public void getAllPosts() throws Exception {
        //@formatter:off
         given()

            .accept(ContentType.JSON)

        .when()
            .get("/v1/posts")

        .then()
            .assertThat()
            .statusCode(HttpStatus.SC_OK);
        //@formatter:on
    }

    @Test
    public void testSave() throws Exception {
        given()

            .contentType(ContentType.JSON)
            .body(PostForm.builder().title("test").build())

        .when()
            .post("/v1/posts")

        .then()
            .statusCode(403);

    }

    @Test
    public void testSaveWithAuth() throws Exception {

        given()
                .header("Authorization", "Bearer "+token)
                .contentType(ContentType.JSON)
                .body(PostForm.builder().title("test").build())

                .when()
                .post("/v1/posts")

                .then()
                .statusCode(201);

    }

    @Test
    @Ignore
    public void testSaveWithInvalidAuth() throws Exception {

        given()
                .header("Authorization", "Bearer "+"invalidtokenhere")
                .contentType(ContentType.JSON)
                .body(PostForm.builder().title("test").build())

                .when()
                .post("/v1/posts")

                .then()
                .statusCode(403);
    }

}
