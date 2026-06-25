package com.blog.services;

import com.blog.dto.BlogCreateResponse;
import com.blog.dto.request.BlogCreateRequest;
import com.blog.model.BlogPost;
import com.blog.model.BlogPostImage;
import com.blog.repository.BlogPostImageRepository;
import com.blog.repository.BlogPostRepository;
import com.common.exception.ConflictException;
import com.common.security.CurrentUser;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class BlogGenericServices {

  @Inject BlogPostRepository blogPostRepository;

  @Inject BlogPostImageRepository blogPostImageRepository;

  @Inject CurrentUser currentUser;

  @Transactional
  public BlogCreateResponse createPost(BlogCreateRequest request) {

    blogPostRepository
        .findByTitle(request.title)
        .ifPresent(
            existing -> {
              throw new ConflictException("Post title already exist");
            });
    BlogPost blogPost = new BlogPost();
    blogPost.title = request.title;
    if (request.description != null) {
      blogPost.description = request.description;
    }
    blogPost.author = currentUser.getUser();

    blogPostRepository.persist(blogPost);

    if (request.postImageUrls != null) {
      this.createPostImages(blogPost, request.postImageUrls);
    }
    BlogCreateResponse blogCreateResponse = new BlogCreateResponse(blogPost.id, blogPost.title);
    return blogCreateResponse;
  }

  @Transactional
  public void createPostImages(BlogPost blogPost, List<String> imageUrls) {

    for (int i = 0; i < imageUrls.size(); i++) {
      BlogPostImage blogPostImage = new BlogPostImage();
      blogPostImage.post = blogPost;

      blogPostImage.imageUrl = imageUrls.get(i);
      blogPostImageRepository.persist(blogPostImage);
    }
  }
}
