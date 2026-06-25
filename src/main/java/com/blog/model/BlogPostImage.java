package com.blog.model;

import com.common.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "blog_post_images")
public class BlogPostImage extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id", nullable = false)
  public BlogPost post;

  @Column(name = "image_url", length = 1024, nullable = false)
  public String imageUrl;
}
