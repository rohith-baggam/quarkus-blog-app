package com.blog.utils;

import com.blog.model.BlogPost;
import com.blog.model.BlogPostImage;
import com.blog.repository.BlogPostImageRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class BlogPostListUtils {

  @Inject BlogPostImageRepository blogPostImageRepository;

  public Map<UUID, List<BlogPostImage>> getBlogPostImageMap(List<BlogPost> blogPostList) {

    List<UUID> postIds = new ArrayList<>();
    for (BlogPost post : blogPostList) {
      postIds.add(post.id);
    }

    List<BlogPostImage> allImages = blogPostImageRepository.find("post.id in ?1", postIds).list();
    Map<UUID, List<BlogPostImage>> imagesByPost = new HashMap<>();

    for (BlogPostImage img : allImages) {
      UUID postId = img.post.id;
      if (!imagesByPost.containsKey(postId)) {
        imagesByPost.put(postId, new ArrayList<>());
      }
      imagesByPost.get(postId).add(img);
    }
    return imagesByPost;
  }
}
