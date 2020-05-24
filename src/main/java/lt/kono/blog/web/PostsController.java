package lt.kono.blog.web;

import lt.kono.blog.domain.Post;
import lt.kono.blog.repository.PostRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/v1/posts")
public class PostsController {

    private PostRepository posts;

    public PostsController(PostRepository posts) {
        this.posts = posts;
    }

    @GetMapping("")
    public ResponseEntity<List<Post>> all() {
        return ok(posts.findAll());
    }

    @PostMapping("")
    public ResponseEntity save(@RequestBody PostForm form, HttpServletRequest request) {
        Post saved = posts.save(Post.builder().title(form.getTitle()).content(form.getContent()).build());
        return created(
                ServletUriComponentsBuilder
                        .fromContextPath(request)
                        .path("/v1/posts/{id}")
                        .buildAndExpand(saved.getId())
                        .toUri())
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> get(@PathVariable("id") Long id) {
        return ok(posts.findById(id).orElseThrow(() -> new PostNotFoundException()));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody PostForm form) {
        Post existed = posts.findById(id).orElseThrow(() -> new PostNotFoundException());
        if(form.getTitle() != null){
            existed.setTitle(form.getTitle());
        }
        if(form.getContent() != null){
            existed.setContent(form.getContent());
        }
        posts.save(existed);
        return noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        Post existed = posts.findById(id).orElseThrow(() -> new PostNotFoundException());
        posts.delete(existed);
        return noContent().build();
    }
}
