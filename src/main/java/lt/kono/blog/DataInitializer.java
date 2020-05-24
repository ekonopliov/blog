package lt.kono.blog;

import lt.kono.blog.domain.User;
import lt.kono.blog.domain.Post;
import lt.kono.blog.repository.UserRepository;
import lt.kono.blog.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    @Autowired
    PostRepository posts;

    @Autowired
    UserRepository users;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        log.debug("initializing posts data...");
        Arrays.asList("The best article", "The worst article").forEach(title -> posts.saveAndFlush(Post.builder()
                .title(title)
                .content(title + " content...")
                .build()));

        log.debug("Printing all posts...");
        posts.findAll().forEach(v -> log.debug(" Post :" + v.toString()));

        users.save(User.builder()
            .username("user")
            .password(passwordEncoder.encode("password"))
            .roles(Arrays.asList( "ROLE_USER"))
            .build()
        );

        users.save(User.builder()
            .username("admin")
            .password(passwordEncoder.encode("password"))
            .roles(Arrays.asList("ROLE_USER", "ROLE_ADMIN"))
            .build()
        );

        log.debug("Printing all users...");
        users.findAll().forEach(v -> log.debug(" User :" + v.toString()));
    }
}
