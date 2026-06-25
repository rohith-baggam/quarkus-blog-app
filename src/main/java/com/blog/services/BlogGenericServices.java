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
import com.common.exception.ConflictException;
import com.common.security.CurrentUser;
import com.user.dto.UserResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
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

  public List<BlogPaginatedResponse> getBlogPaginatedList(BlogListParams blogListParams) {

    List<BlogPost> blogList =
        blogPostRepository
            .findAll()
            .range(blogListParams.offset, blogListParams.limit + blogListParams.offset - 1)
            .list();

    List<BlogPaginatedResponse> response = new ArrayList<>();

    for (int i = 0; i < blogList.size(); i++) {
      BlogPost blogPost = blogList.get(i);
      UserResponse userResponse = new UserResponse(blogPost.author);

      BlogPaginatedResponse blogPaginatedResponse =
          new BlogPaginatedResponse(
              blogPost, userResponse, this.getBlogPostImageListResponse(blogPost));
      response.add(blogPaginatedResponse);
    }

    return response;
  }

  public List<BlogPostImageResponse> getBlogPostImageListResponse(BlogPost blogPost) {
    List<BlogPostImage> blogImageList = blogPostImageRepository.find("post", blogPost).list();

    List<BlogPostImageResponse> blogPostImageResponseList = new ArrayList<>();
    for (int i = 0; i < blogImageList.size(); i++) {

      BlogPostImage blogPostImage = blogImageList.get(i);

      BlogPostImageResponse blogPostImageResponse = new BlogPostImageResponse(blogPostImage);

      blogPostImageResponseList.add(blogPostImageResponse);
    }

    return blogPostImageResponseList;
  }
}
