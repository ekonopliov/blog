package lt.kono.blog.repository;

import lt.kono.blog.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "posts", collectionResourceRel = "posts", itemResourceRel = "post")
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByTitle(String title);

}
