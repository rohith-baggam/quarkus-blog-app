package com.blog.model;

import com.common.model.BaseEntity;
import com.user.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "blog_posts")
public class BlogPost extends BaseEntity {

    @Column(name = "title", length = 256, nullable = false, unique = true)
    public String title;

    @Column(name = "description", columnDefinition = "TEXT")
    public String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    public User author;

}
