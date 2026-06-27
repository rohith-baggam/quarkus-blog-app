package com.blog.repository;

import com.blog.model.BlogPost;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class BlogPostRepository implements PanacheRepository<BlogPost> {
  public Optional<BlogPost> findByTitle(String title) {
    return find("title", title).firstResultOptional();
  }

  public Optional<BlogPost> findByPostId(UUID id) {
    return find("id", id).firstResultOptional();
  }
}
