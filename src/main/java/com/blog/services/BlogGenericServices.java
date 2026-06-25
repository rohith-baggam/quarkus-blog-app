package com.blog.services;

import com.blog.dto.BlogCreateResponse;
import com.blog.dto.BlogPaginatedResponse;
import com.blog.dto.BlogPostImageResponse;
import com.blog.dto.request.BlogCreateRequest;
import com.blog.dto.request.BlogListParams;
import com.blog.model.BlogPost;
import com.blog.model.BlogPostImage;
import com.blog.repository.BlogPostImageRepository;
import com.blog.repository.BlogPostRepository;
import com.blog.utils.BlogPostListUtils;
import com.common.exception.ConflictException;
import com.common.security.CurrentUser;
import com.user.dto.UserResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

  @Inject BlogPostListUtils blogPostListUtils;

  public List<BlogPaginatedResponse> getBlogPaginatedList(BlogListParams blogListParams) {

    List<BlogPost> blogList =
        blogPostRepository
            .findAll()
            .range(blogListParams.offset, blogListParams.limit + blogListParams.offset - 1)
            .list();

    Map<UUID, List<BlogPostImage>> imagesByPost = blogPostListUtils.getBlogPostImageMap(blogList);

    List<BlogPaginatedResponse> blogPaginatedResponses = new ArrayList<>();
    for (BlogPost blogPost : blogList) {
      UserResponse userResponse = new UserResponse(blogPost.author);

      List<BlogPostImage> images = imagesByPost.get(blogPost.id);
      if (!images.isEmpty()) {
        List<BlogPostImageResponse> imageResponses = getBlogPostImageListResponse(images);
        blogPaginatedResponses.add(
            new BlogPaginatedResponse(blogPost, userResponse, imageResponses));
      }
    }

    return blogPaginatedResponses;
  }

  public List<BlogPostImageResponse> getBlogPostImageListResponse(List<BlogPostImage> images) {
    List<BlogPostImageResponse> blogPostImageResponses = new ArrayList<>();
    for (BlogPostImage blogPostImage : images) {
      blogPostImageResponses.add(new BlogPostImageResponse(blogPostImage));
    }
    return blogPostImageResponses;
  }
}
