-- Users table
CREATE TABLE IF NOT EXISTS users (
    id          TEXT PRIMARY KEY,
    username    VARCHAR(128) NOT NULL,
    email       VARCHAR(128) NOT NULL UNIQUE,
    password    VARCHAR(256) NOT NULL,
    is_active   BOOLEAN NOT NULL DEFAULT 1,
    created_at  DATETIME NOT NULL,
    updated_at  DATETIME NOT NULL
);

-- Blog posts table
CREATE TABLE IF NOT EXISTS blog_posts (
    id          TEXT PRIMARY KEY,
    title       VARCHAR(256) NOT NULL UNIQUE,
    description TEXT,
    author_id   TEXT NOT NULL,
    created_at  DATETIME NOT NULL,
    updated_at  DATETIME NOT NULL,
    FOREIGN KEY (author_id) REFERENCES users(id)
);

-- Blog post images table
CREATE TABLE IF NOT EXISTS blog_post_images (
    id          TEXT PRIMARY KEY,
    post_id     TEXT NOT NULL,
    image_url   VARCHAR(1024) NOT NULL,
    created_at  DATETIME NOT NULL,
    updated_at  DATETIME NOT NULL,
    FOREIGN KEY (post_id) REFERENCES blog_posts(id)
);