package com.blog.repository;

import com.blog.model.BlogPostImage;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BlogPostImageRepository implements PanacheRepository<BlogPostImage> {}
